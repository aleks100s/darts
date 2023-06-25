import SwiftUI
import shared

struct DartsBoardScreen: View {
	let turn: Turn
	
	@Environment(\.displayScale) var displayScale
	@State private var renderedImage = Image(systemName: "photo")
		
	var body: some View {
		ScrollView(showsIndicators: false) {
			content
		}
		.toolbar {
			ToolbarItem(placement: .navigationBarTrailing) {
				ShareLink("share", item: renderedImage, preview: SharePreview("preview", image: renderedImage))
			}
		}
		.task {
			do {
				try await Task.sleep(nanoseconds: 1_000_000_000)
				render()
			} catch {}
		}
		.background(Color.surface)
	}
	
	@ViewBuilder
	private var content: some View {
		VStack(spacing: 32) {
			ForEach(turn.shots.filter { !$0.sector.isNone() }) { shot in
				dartsBoardItem(shot: shot)
			}
		}
	}
	
	@ViewBuilder
	private func dartsBoardItem(shot: Shot) -> some View {
		VStack(alignment: .center) {
			Text(shot.sector.inputString)
			DartsBoard(highlightedSector: shot.sector)
		}
	}
	
	@MainActor
	private func render() {
		let content = content
			.background(Color.background)
			.frame(width: 300)
		let renderer = ImageRenderer(content: content)
		// make sure and use the correct display scale for this device
		renderer.scale = displayScale
		if let uiImage = renderer.uiImage {
			renderedImage = Image(uiImage: uiImage)
		}
	}
}

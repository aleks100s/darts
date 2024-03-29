import SwiftUI

struct CreateGameWidgetEntryView: View {
	let entry: CreateGameEntry
	
	@Environment(\.widgetFamily) private var family

	var body: some View {
		content
	}
	
	@ViewBuilder
	private var content: some View {
		switch family {
		case .accessoryCircular:
			CreateGameLockScreenWidgetView()
			
		default:
			EmptyView()
		}
	}
}

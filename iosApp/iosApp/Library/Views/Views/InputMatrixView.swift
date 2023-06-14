import SwiftUI
import shared

struct InputMatrixView: View {
	let onInputClick: (Sector) -> ()

	var body: some View {
		ScrollView {
			VStack(spacing: 2) {
				ForEach(Sector.companion.sectors) { sectors in
					InputRow(sectors: sectors, onInputClick: onInputClick)
				}
				.listRowSeparator(.hidden)
			}
			.background(ScrollViewConfigurator {
				$0?.bounces = false
			})
		}
		.background(Color.background)
	}
	
	@ViewBuilder
	private func InputRow(
		sectors: [Sector],
		onInputClick: @escaping (Sector) -> ()
	) -> some View {
		HStack(spacing: 2) {
			ForEach(sectors) { sector in
				InputCell(sector: sector) {
					onInputClick(sector)
				}
			}
			.background(Color.background)
		}
		.frame(maxWidth: .infinity)
	}
	
	@ViewBuilder
	private func InputCell(
		sector: Sector,
		onInputClick: @escaping () -> ()
	) -> some View {
		Button {
			onInputClick()
		} label: {
			HStack(alignment: .center) {
				Text(sector.inputString)
			}
			.padding(.vertical, 12)
			.frame(maxWidth: .infinity)
			.background(sector.backgroundColor)
		}
		.tint(sector.textColor)
	}
}

private struct ScrollViewConfigurator: UIViewRepresentable {
	let configure: (UIScrollView?) -> ()
	func makeUIView(context: Context) -> UIView {
		let view = UIView()
		DispatchQueue.main.async {
			configure(view.enclosingScrollView())
		}
		return view
	}

	func updateUIView(_ uiView: UIView, context: Context) {}
}

private extension UIView {
	func enclosingScrollView() -> UIScrollView? {
		var next: UIView? = self
		repeat {
			next = next?.superview
			if let scrollview = next as? UIScrollView {
				return scrollview
			}
		} while next != nil
		return nil
	}
}

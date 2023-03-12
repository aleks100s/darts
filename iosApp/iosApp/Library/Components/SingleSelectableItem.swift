import SwiftUI

internal struct SignleSelectableItem: View {
	let title: String
	let onSelect: () -> Void
	
	var body: some View {
		Button {
			onSelect()
		} label: {
			HStack {
				Text(title)
				Spacer()
				Image(systemName: "chevron.right")
			}
			.padding(.vertical, 4)
		}
		.tint(Color.onBackground)
	}
}

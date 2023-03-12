import SwiftUI

internal struct DoubleSelectableItem: View {
	let leftText: String
	let rightText: String
	let onSelect: () -> Void
	
	var body: some View {
		Button {
			onSelect()
		} label: {
			HStack {
				Text(leftText)
				Spacer()
				Text(rightText)
				Image(systemName: "chevron.right")
			}
			.padding(.horizontal, 16)
			.padding(.vertical, 4)
		}
		.tint(Color.onBackground)
	}
}

import SwiftUI
import shared

struct StatisticsPlayerListView: View {
	let values: [(Player, String)]
	let onValueSelected: (Int) -> Void
	
	var body: some View {
		List {
			ForEach(values, id: \.0.id) { value in
				DoubleSelectableItem(
					leftText: value.0.name,
					rightText: value.1,
					onSelect: {
						onValueSelected(values.firstIndex { $0 == value } ?? 0)
					}
				)
			}
		}
	}
}

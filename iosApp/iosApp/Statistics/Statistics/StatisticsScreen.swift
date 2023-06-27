import SwiftUI
import shared

struct StatisticsScreen: View {
	let onSelect: (StatisticsNavigationEvent) -> Void
	
	var body: some View {
		List {
			ForEach(StatisticsNavigationEvent.allCases) { event in
				SignleSelectableItem(title: event.title) {
					onSelect(event)
				}
			}
		}
	}
}

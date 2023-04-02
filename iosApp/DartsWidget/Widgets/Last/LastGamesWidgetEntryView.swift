import SwiftUI

struct LastGamesWidgetEntryView: View {
	let entry: LastGamesEntry
	
	@Environment(\.widgetFamily) private var family

	var body: some View {
		content
	}
	
	@ViewBuilder
	private var content: some View {
		switch family {
		case .systemMedium:
			LastGamesMediumWidgetView(entry: entry)
			
		default:
			EmptyView()
		}
	}
}

import SwiftUI

struct CreateGameWidgetEntryView : View {
	var entry: Provider.Entry
	
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

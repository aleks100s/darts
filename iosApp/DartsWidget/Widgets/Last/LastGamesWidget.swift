import SwiftUI
import WidgetKit

struct LastGamesWidget: Widget {
	let kind: String = String(describing: Self.self)

	var body: some WidgetConfiguration {
		IntentConfiguration(
			kind: kind,
			intent: ConfigurationIntent.self,
			provider: LastGamesProvider()
		) { entry in
			LastGamesWidgetEntryView(entry: entry)
		}
		.configurationDisplayName("last_games_widget")
		.description("last_games_widget_description")
		.supportedFamilies([.systemMedium])
	}
}

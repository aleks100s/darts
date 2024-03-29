import SwiftUI
import WidgetKit

struct CreateGameWidget: Widget {
	let kind: String = String(describing: Self.self)

	var body: some WidgetConfiguration {
		IntentConfiguration(
			kind: kind,
			intent: ConfigurationIntent.self,
			provider: CreateGameProvider()
		) { entry in
			CreateGameWidgetEntryView(entry: entry)
		}
		.configurationDisplayName("create_game_widget")
		.description("create_game_widget_description")
		.supportedFamilies([.accessoryCircular])
	}
}

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
		.configurationDisplayName("lock_screen_widget")
		.description("lock_screen_widget_description")
		.supportedFamilies([.accessoryCircular])
	}
}

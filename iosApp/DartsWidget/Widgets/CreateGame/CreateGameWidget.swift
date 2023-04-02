import SwiftUI
import WidgetKit

struct CreateGameWidget: Widget {
	let kind: String = String(describing: Self.self)

	var body: some WidgetConfiguration {
		IntentConfiguration(
			kind: kind,
			intent: ConfigurationIntent.self,
			provider: Provider()
		) { entry in
			CreateGameWidgetEntryView(entry: entry)
		}
		.configurationDisplayName("Lock Screen Widget")
		.description("This widget can navigate you directly to game creation!")
		.supportedFamilies([.accessoryCircular])
	}
}

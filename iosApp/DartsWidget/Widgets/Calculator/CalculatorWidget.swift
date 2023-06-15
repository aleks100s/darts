import SwiftUI
import WidgetKit

struct CalculatorWidget: Widget {
	let kind: String = String(describing: Self.self)

	var body: some WidgetConfiguration {
		IntentConfiguration(
			kind: kind,
			intent: ConfigurationIntent.self,
			provider: CalculatorProvider()
		) { entry in
			CalculatorWidgetEntryView(entry: entry)
		}
		.configurationDisplayName("calculator_widget")
		.description("calculator_widget_description")
		.supportedFamilies([.accessoryCircular])
	}
}

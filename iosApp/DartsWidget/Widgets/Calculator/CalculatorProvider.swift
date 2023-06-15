import WidgetKit
import SwiftUI
import Intents

struct CalculatorProvider: IntentTimelineProvider {
	func placeholder(in context: Context) -> CalculatorEntry {
		CalculatorEntry(date: Date(), configuration: ConfigurationIntent())
	}

	func getSnapshot(for configuration: ConfigurationIntent, in context: Context, completion: @escaping (CalculatorEntry) -> ()) {
		let entry = CalculatorEntry(date: Date(), configuration: configuration)
		completion(entry)
	}

	func getTimeline(for configuration: ConfigurationIntent, in context: Context, completion: @escaping (Timeline<CalculatorEntry>) -> ()) {
		let entries = [CalculatorEntry(date: Date(), configuration: configuration)]
		let timeline = Timeline(entries: entries, policy: .atEnd)
		completion(timeline)
	}
}

import WidgetKit
import SwiftUI
import Intents

struct CreateGameProvider: IntentTimelineProvider {
	func placeholder(in context: Context) -> CreateGameEntry {
		CreateGameEntry(date: Date(), configuration: ConfigurationIntent())
	}

	func getSnapshot(for configuration: ConfigurationIntent, in context: Context, completion: @escaping (CreateGameEntry) -> ()) {
		let entry = CreateGameEntry(date: Date(), configuration: configuration)
		completion(entry)
	}

	func getTimeline(for configuration: ConfigurationIntent, in context: Context, completion: @escaping (Timeline<CreateGameEntry>) -> ()) {
		let entries = [CreateGameEntry(date: Date(), configuration: configuration)]
		let timeline = Timeline(entries: entries, policy: .atEnd)
		completion(timeline)
	}
}

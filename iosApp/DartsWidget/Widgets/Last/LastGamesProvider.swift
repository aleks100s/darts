import WidgetKit

struct LastGamesProvider: IntentTimelineProvider {
	func placeholder(in context: Context) -> LastGamesEntry {
		LastGamesEntry(date: Date(), configuration: ConfigurationIntent())
	}

	func getSnapshot(for configuration: ConfigurationIntent, in context: Context, completion: @escaping (LastGamesEntry) -> ()) {
		let entry = LastGamesEntry(date: Date(), configuration: configuration)
		completion(entry)
	}

	func getTimeline(for configuration: ConfigurationIntent, in context: Context, completion: @escaping (Timeline<LastGamesEntry>) -> ()) {
		let entries = [LastGamesEntry]()
		let timeline = Timeline(entries: entries, policy: .atEnd)
		completion(timeline)
	}
}


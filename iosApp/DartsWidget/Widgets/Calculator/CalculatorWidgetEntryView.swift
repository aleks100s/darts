import SwiftUI

struct CalculatorWidgetEntryView: View {
	let entry: CalculatorEntry
	
	@Environment(\.widgetFamily) private var family

	var body: some View {
		content
	}
	
	@ViewBuilder
	private var content: some View {
		switch family {
		case .accessoryCircular:
			CalculatorLockScreenWidgetView()
			
		default:
			EmptyView()
		}
	}
}

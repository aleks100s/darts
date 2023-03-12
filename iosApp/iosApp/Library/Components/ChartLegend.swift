import SwiftUI

internal struct ChartLegend: View {
	@Binding var legend: [(Color, String)]
	
	var body: some View {
		VStack(alignment: .leading) {
			ForEach(legend, id: \.1) { item in
				legendItem(item: item)
			}
		}
	}
	
	@ViewBuilder
	private func legendItem(item: (Color, String)) -> some View {
		HStack {
			Image(systemName: "square.fill")
				.foregroundColor(item.0)
			Text(item.1)
			Spacer()
		}
	}
}

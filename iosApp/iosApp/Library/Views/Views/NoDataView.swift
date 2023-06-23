import SwiftUI

struct NoDataView: View {
	var body: some View {
		VStack(alignment: .center) {
			Spacer()
			Text("no_data_yet")
			Spacer()
		}
		.frame(maxWidth: .infinity)
	}
}

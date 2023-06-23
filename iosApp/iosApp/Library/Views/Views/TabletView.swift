import SwiftUI

struct TabletView<V1: View, V2: View>: View {
	@ViewBuilder var view1: V1
	@ViewBuilder var view2: V2
	
	var body: some View {
		HStack(spacing: 2) {
			view1
			view2
		}
	}
}

import shared
import SwiftUI

enum TimeScene {
	static func create(module: AppModule) -> some View {
		let getGlobalTotalTimePlayedUseCase = GetGlobalTotalTimePlayedUseCase(dataSource: module.statisticsDataSource)
		let viewModel = TimeViewModel(getGlobalTotalTimePlayedUseCase: getGlobalTotalTimePlayedUseCase, coroutineScope: nil)
		let iOSViewModel = IOSTimeViewModel(viewModel: viewModel)
		return TimeView(viewModel: iOSViewModel)
	}
}

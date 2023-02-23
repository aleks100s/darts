import SwiftUI
import shared

internal enum CreatePlayerScene {
	@MainActor
	static func create(
		using module: AppModule,
		navigateBack: @escaping () -> ()
	) -> some View {
		let createPlayerUseCase = CreatePlayerUseCase(dataSource: module.gameDataSource)
		let viewModel = IOSCreatePlayerViewModel(
			createPlayerUseCase: createPlayerUseCase,
			navigateBack: navigateBack
		)
		return CreatePlayerView(viewModel: viewModel)
	}
}

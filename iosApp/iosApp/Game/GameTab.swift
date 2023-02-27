import SwiftUI
import shared

internal struct GameTab: View {
	let module: AppModule
	
	@State private var navigationStack: [GameNavigation] = []
	@State private var isCreatePlayerShown: Bool = false
	
	var body: some View {
		NavigationStack(path: $navigationStack) {
			GameListScene.create(using: module)
				.toolbar {
					Button {
						navigationStack.append(.createGame)
					} label: {
						Label("create_game", systemImage: "plus")
					}
				}
				.navigationDestination(for: GameNavigation.self) {
					navigate(to: $0)
				}
		}
		.sheet(isPresented: $isCreatePlayerShown) {
			CreatePlayerScene.create(using: module) {
				isCreatePlayerShown = false
			}
			.presentationDetents([.medium])
		}
	}
	
	@MainActor
	@ViewBuilder
	private func navigate(to scene: GameNavigation) -> some View {
		switch scene {
		case .createGame:
			CreateGameScene.create(
				using: module,
				createPlayer: {
					isCreatePlayerShown = true
				},
				startGame: { players, goal in
					navigationStack.append(.game(players: players, goal: goal))
				}
			)
			
		case let .game(players, goal):
			GameScene.create(using: module, players: players, goal: goal) {
				navigationStack = []
			}
				.navigationTitle("game")
				.navigationBarBackButtonHidden()
		}
	}
}

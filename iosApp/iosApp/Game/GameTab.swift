import SwiftUI
import shared

internal struct GameTab: View {
	let module: AppModule
	
	@State private var navigationStack: [GameNavigation] = []
	
	var body: some View {
		NavigationStack(path: $navigationStack) {
			GameListScene.create(using: module)
				.toolbar {
					Button {
						navigationStack.append(.createGame)
					} label: {
						Label("Create game", systemImage: "plus")
					}
				}
				.navigationDestination(for: GameNavigation.self) {
					navigate(to: $0)
				}
		}
	}
	
	@MainActor
	@ViewBuilder
	private func navigate(to scene: GameNavigation) -> some View {
		switch scene {
		case .createGame:
			CreateGameScene.create(using: module) { event in
				switch event {
				case .CreatePlayer():
					navigationStack.append(.createPlayer)
					
				default:
					break
				}
			}
			
		case .createPlayer:
			CreatePlayerScene.create(using: module) {
				_ = navigationStack.popLast()
			}
		}
	}
}

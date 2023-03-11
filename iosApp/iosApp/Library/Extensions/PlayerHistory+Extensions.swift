import shared

extension PlayerHistory: Identifiable {
	public var id: String {
		player.name
	}
}

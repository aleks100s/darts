import shared

extension GamePlayerResult: Identifiable {
	public var id: Int64 {
		self.player.id
	}
}

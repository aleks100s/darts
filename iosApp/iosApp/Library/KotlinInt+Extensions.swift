import shared

extension KotlinInt: Identifiable {
	public var id: Int {
		self.intValue
	}
}

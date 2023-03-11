import shared

extension KotlinFloat: Identifiable {
	public var id: Float {
		self.floatValue
	}
}

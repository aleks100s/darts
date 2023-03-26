import shared
import SwiftUI

extension Sector: Identifiable {
	
}

extension [Sector]: Identifiable {
	public var id: Int32 {
		first?.id ?? 0
	}
}

internal extension Sector {
	var textColor: Color {
		if isWhite() {
			return .black
		} else if isBlack() {
			return .white
		} else if isGreen() {
			return .black
		} else if isRed() {
			return .white
		} else {
			return .white
		}
	}
	
	var backgroundColor: Color {
		if isWhite() {
			return .white
		} else if isBlack() {
			return .black
		} else if isGreen() {
			return .green
		} else if isRed() {
			return .red
		} else {
			return .gray
		}
	}
}

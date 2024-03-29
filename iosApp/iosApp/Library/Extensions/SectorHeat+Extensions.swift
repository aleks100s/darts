import shared
import SwiftUI

extension SectorHeat: Identifiable {}

extension SectorHeat {
	var color: Color {
		Color(red: Double(heat), green: Double(heat) / 3, blue: Double(heat) / 3)
	}
}

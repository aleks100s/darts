import SwiftUI
import shared

extension Color {
	init(hex: Int64, alpha: Double = 1) {
		self.init(
			.sRGB,
			red: Double((hex >> 16) & 0xff) / 255,
			green: Double((hex >> 08) & 0xff) / 255,
			blue: Double((hex >> 00) & 0xff) / 255,
			opacity: alpha
		)
	}
		
	static let lightBackground = Color(hex: colors.LightBackground)
	static let lightSurface = Color(hex: colors.LightSurface)
	static let darkBackground = Color(hex: colors.DarkBackground)
	static let darkSurface = Color(hex: colors.DarkSurface)
	static let salat = Color(hex: 0xFF00FF90)
	static let textBlack = Color(hex: colors.TextBlack)
	
	static let secondary = Color(light: salat, dark: salat)
	static let onSecondary = Color(light: .textBlack, dark: .textBlack)
	static let background = Color(light: .lightBackground, dark: .darkBackground)
	static let onBackground = Color(light: .textBlack, dark: .white)
	static let surface = Color(light: .lightSurface, dark: .darkSurface)
	static let onSurface = Color(light: .textBlack, dark: .white)
	
	private static let colors = Colors()
}

private extension Color {
	init(light: Self, dark: Self) {
		self.init(uiColor: UIColor(light: UIColor(light), dark: UIColor(dark)))
	}
}

private extension UIColor {
	convenience init(light: UIColor, dark: UIColor) {
		self.init { traits in
			switch traits.userInterfaceStyle {
			case .light, .unspecified:
				return light
			case .dark:
				return dark
			@unknown default:
				return light
			}
		}
	}
}

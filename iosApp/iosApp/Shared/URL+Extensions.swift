import Foundation

extension URL {
	static var createGame: URL? {
		var urlComponents = URLComponents()
		urlComponents.path = "darts-helper/createGame"
		return urlComponents.url
	}
	
	static var calculator: URL? {
		var urlComponents = URLComponents()
		urlComponents.path = "darts-helper/calculator"
		return urlComponents.url
	}
	
	private var queryParameters: [String: String]? {
		guard
			let components = URLComponents(url: self, resolvingAgainstBaseURL: true),
			let queryItems = components.queryItems else { return nil }
		return queryItems.reduce(into: [String: String]()) { (result, item) in
			result[item.name] = item.value
		}
	}
	
	func canBeNavigated(to path: String?) -> Bool {
		lastPathComponent == path
	}
	
	func contains(value: String, for key: String) -> Bool {
		guard let parameters = queryParameters else { return false }
		
		return parameters.contains { $0 == key && $1 == value }
	}
}

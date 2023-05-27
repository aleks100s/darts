//
//  iosAppUITests.swift
//  iosAppUITests
//
//  Created by Alexander on 24.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import XCTest

final class iosAppUITests: XCTestCase {
	private let app = XCUIApplication()

    override func setUpWithError() throws {
        continueAfterFailure = false
		app.launch()
    }
	
	func testCreateGameButtonIsAvailable() {
		app.navigationBars["Games"].buttons["createGame"].tap()
	}

    func testLaunchPerformance() throws {
        if #available(macOS 10.15, iOS 13.0, tvOS 13.0, watchOS 7.0, *) {
            // This measures how long it takes to launch your application.
            measure(metrics: [XCTApplicationLaunchMetric()]) {
                XCUIApplication().launch()
            }
        }
    }
}

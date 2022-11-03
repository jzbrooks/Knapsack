//
//  readlaterApp.swift
//  readlater
//
//  Created by Justin Brooks on 3/7/22.
//

import SwiftUI
import data

@main
struct ReadlaterApp: App {
    @StateObject var appSettingsManager: SettingsManager
    @StateObject var authenticationManager: Authenticator
    @StateObject var dataController: CachingEntryRepository
    
    init() {
        let driverFactory = DriverFactory()
        let appSettings = SettingsManager()
        let authenticator = Authenticator(appSettings: appSettings)
        let service = EntryService(appSettings: appSettings, authenticationManager: authenticator)
        let dataController = CachingEntryRepository(authenticationManager: authenticator, databaseDriver: driverFactory, service: service)
        _dataController = StateObject(wrappedValue: dataController)
        _authenticationManager = StateObject(wrappedValue: authenticator)
        _appSettingsManager = StateObject(wrappedValue: appSettings)
    }
    
    var body: some Scene {
        WindowGroup {
            NavigationView {
                ReadingListView(authManager: dataController.authenticationManager, entries: [])
            }
            .environmentObject(appSettingsManager)
            .environmentObject(authenticationManager)
            .environmentObject(dataController)
        }
    }
}

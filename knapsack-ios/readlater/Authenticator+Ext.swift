//
//  Authenticator+Ext.swift
//  readlater
//
//  Created by Justin Brooks on 10/19/22.
//

import data

extension SettingsManager : ObservableObject {
    
}

extension Authenticator : ObservableObject {
    
}

extension CachingEntryRepository : ObservableObject {
    
}

extension Entry : Identifiable {
    static let preview = Entry(id: 1, title: "You should read this", url: "https://americanreformer.org/wp-content/uploads/2022/10/mai-rodriguez-__4mdndKK1w-unsplash-1-scaled-e1664893436482-1536x476.jpeg", content: "<h1>dude</h1>", preview_picture: nil)
    
}

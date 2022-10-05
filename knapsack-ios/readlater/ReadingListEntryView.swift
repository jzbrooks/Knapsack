//
//  ReadingListEntryView.swift
//  readlater
//
//  Created by Justin Brooks on 4/28/22.
//

import SwiftUI
import data

struct ReadingListEntryView: View {
    @State var entry: Entry
    
    var body: some View {
        HypertextMarkupView(html: entry.content)
    }
}

struct ReadingListEntryView_Previews: PreviewProvider {
    static var previews: some View {
        ReadingListEntryView(entry: .preview)
    }
}

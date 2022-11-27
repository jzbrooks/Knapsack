//
//  ReadingListEntryView.swift
//  readlater
//
//  Created by Justin Brooks on 4/28/22.
//

import SwiftUI
import common

struct ReadingListEntryView: View {
    @State var entry: Entry

    private var formattedText: Text {
        let string = HtmlString(text: entry.content)
        let styled = string.toStyledString()
        return styled.renderToView()
    }

    var body: some View {
        ScrollView(.vertical) {
            formattedText.padding(.horizontal)
        }.navigationTitle(entry.title)
    }
}

struct ReadingListEntryView_Previews: PreviewProvider {
    static var previews: some View {
        ReadingListEntryView(entry: .preview)
    }
}

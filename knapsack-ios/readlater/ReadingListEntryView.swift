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
    
    var formattedText: AttributedString {
        let string = HtmlString(text: entry.content)
        let styled = string.toStyledString()
        return AttributedString(stringLiteral: styled.text)
    }

    var body: some View {
        ScrollView {
            Text(formattedText)
        }
    }
}

struct ReadingListEntryView_Previews: PreviewProvider {
    static var previews: some View {
        ReadingListEntryView(entry: .preview)
    }
}

//
//  StyledString+Ext.swift
//  readlater
//
//  Created by Justin Brooks on 11/25/22.
//

import SwiftUI
import data

extension StyledString {
    func renderToView() -> Text {
        var text = AttributedString(self.text)

        for bold in boldPositions {
            let range = text.index(text.startIndex, offsetByCharacters: Int(truncating: bold.start))...text.index(text.startIndex, offsetByCharacters: Int(truncating: bold.endInclusive))
            text[range].inlinePresentationIntent = .stronglyEmphasized
        }
        
        for italic in italicPositions {
            let range = text.index(text.startIndex, offsetByCharacters: Int(truncating: italic.start))...text.index(text.startIndex, offsetByCharacters: Int(truncating: italic.endInclusive))
            text[range].inlinePresentationIntent = .emphasized
        }
        
        for underline in underlinedPositions {
            let range = text.index(text.startIndex, offsetByCharacters: Int(truncating: underline.start))...text.index(text.startIndex, offsetByCharacters: Int(truncating: underline.endInclusive))
            text[range].underlineStyle = .single
        }

        return Text(text)
    }
}

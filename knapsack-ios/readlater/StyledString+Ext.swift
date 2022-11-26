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
        var pendingStyles: Set<Style> = []
        var text = Text("")

        let bolds = self.boldPositions.map {
            self.text.index(self.text.startIndex, offsetBy: Int(truncating: $0.start))...self.text.index(self.text.startIndex, offsetBy: Int(truncating: $0.endInclusive))
        }
        
        let italics = self.italicPositions.map {
            self.text.index(self.text.startIndex, offsetBy: Int(truncating: $0.start))...self.text.index(self.text.startIndex, offsetBy: Int(truncating: $0.endInclusive))
        }
        
        let underlines = self.underlinedPositions.map {
            self.text.index(self.text.startIndex, offsetBy: Int(truncating: $0.start))...self.text.index(self.text.startIndex, offsetBy: Int(truncating: $0.endInclusive))
        }
                
        var start = self.text.startIndex
        for i in self.text.indices {
            var newStyles: Set<Style> = []

            if bolds.contains(where: { $0.contains(i) }) {
                newStyles.insert(.bold)
            }
            
            if italics.contains(where: { $0.contains(i) }) {
                newStyles.insert(.italic)
            }
            
            if underlines.contains(where: { $0.contains(i) }) {
                newStyles.insert(.underline)
            }
            
            if newStyles != pendingStyles {
                let plain = self.text[start..<i]
                
                var newSegment = Text(plain)
                
                if pendingStyles.contains(.bold) {
                    newSegment = newSegment.bold()
                }
                
                if pendingStyles.contains(.italic) {
                    newSegment = newSegment.italic()
                }
                
                if pendingStyles.contains(.underline) {
                    newSegment = newSegment.underline()
                }
                
                start = i
                pendingStyles = newStyles
                text = text + newSegment
                
                continue
            }
        }

        let end = self.text.index(self.text.startIndex, offsetBy: self.text.count)

        return text + Text(self.text[start..<end])
    }

    enum Style {
        case bold, italic, underline
    }
}

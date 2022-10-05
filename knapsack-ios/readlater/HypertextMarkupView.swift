//
//  HypertextMarkupView.swift
//  readlater
//
//  Created by Justin Brooks on 4/30/22.
//

import SwiftUI

import WebKit
import SwiftUI

struct HypertextMarkupView: UIViewRepresentable {
    let html: String

    func makeUIView(context: Context) -> WKWebView {
        let view = WKWebView()
        view.loadHTMLString(html, baseURL: nil)
        return view
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
    }
}

struct HypertextMarkupView_Previews: PreviewProvider {
    static var previews: some View {
        HypertextMarkupView(html: "<h1>cool, dude</h1>")
    }
}

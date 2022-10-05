import SwiftUI
import data

struct ReadingListView: View {
    private var authenticator: AuthenticationManager
    
    @EnvironmentObject var repo: CachingEntryRepository
    @State var entries: Array<Entry>
    @State var showAuth: Bool
    
    init(authManager: AuthenticationManager, entries: [Entry]) {
        _showAuth = State(initialValue: !authManager.isAuthenticated)
        self.authenticator = authManager
        self.entries = entries // todo: should this be a binding?
    }
    
    var body: some View {
        List {
            ForEach(entries) { entry in
                ReadingListItem(entry: entry)
                    .frame(maxHeight: 100)
            }
        }
        .task { await load() }
        .navigationTitle("Reading List")
        .toolbar {
            Button(action: {
                authenticator.deleteCredentials()
                Task {
                    try await repo.deleteAllEntries()
                    showAuth = true
                }
            }, label: {
                Image(systemName: "rectangle.portrait.and.arrow.right")
            })
        }
        .fullScreenCover(isPresented: $showAuth, onDismiss: {
            Task { await load() }
        }) {
            AuthenticationView(isPresented: $showAuth)
        }
    }
    
    private func load() async {
        guard entries.count < 1, authenticator.isAuthenticated else { return }

        do {
            try await repo.updateEntries()
            self.entries = try await repo.entries()
        } catch {
            debugPrint("There was an error!")
        }
    }
}

struct ReadingListItem: View {
    let entry: Entry
    
    var body: some View {
        NavigationLink(destination: ReadingListEntryView(entry: entry)) {
            HStack {
                if let previewImage = entry.preview_picture {
                    // HACK: Should http image loading be allowed?
                    let url = URL(string: previewImage.replacingOccurrences(of: "http://", with: "https://"))
                    AsyncImage(url: url) { phase in
                        if let image = phase.image {
                            image
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                        } else if let error = phase.error {
                            let _ = debugPrint(error)
                            Color.red
                        } else {
                            Color(white: 0.75)
                        }
                    }
                    .frame(width: 50, height: 50)
                    .cornerRadius(4)
                }
                
                Text(entry.title)
                    .fontWeight(.medium)
            }
        }
    }
}

struct ReadingList_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ReadingListView(
                authManager: Authenticator(appSettings: AppSettingsManager()),
                entries: [.preview]
            )            
        }
    }
}

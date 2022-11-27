import SwiftUI
import common

struct AuthenticationView: View {
    @EnvironmentObject var appSettingsManager: SettingsManager
    @EnvironmentObject var authenticationManager: Authenticator

    @State var baseUrl: String = "https://app.wallabag.it"
    @State var clientId: String = ""
    @State var clientSecret: String = ""
    @State var username: String = ""
    @State var password: String = ""
    @Binding var isPresented: Bool

    var body: some View {
        VStack {
            TextField("BaseUrl", text: $baseUrl)
                .textInputAutocapitalization(.never)
                .disableAutocorrection(true)
                .textFieldStyle(.roundedBorder)

            TextField("Client ID", text: $clientId)
                .textInputAutocapitalization(.never)
                .disableAutocorrection(true)
                .textFieldStyle(.roundedBorder)

            TextField("Client Secret", text: $clientSecret)
                .textInputAutocapitalization(.never)
                .disableAutocorrection(true)
                .textFieldStyle(.roundedBorder)

            TextField("Username", text: $username)
                .textInputAutocapitalization(.never)
                .disableAutocorrection(true)
                .textFieldStyle(.roundedBorder)

            SecureField("Password", text: $password)
                .textInputAutocapitalization(.never)
                .disableAutocorrection(true)
                .textFieldStyle(.roundedBorder)

            withAnimation {
                Button(action: {
                    Task {
                        do {
                            try await getAccessToken()
                            isPresented.toggle()
                        } catch {
                            debugPrint(error.localizedDescription)
                        }
                    }
                }, label: {
                    Text("Sign In")
                })
            }
        }.padding()

    }

    private func getAccessToken() async throws {
        appSettingsManager.baseUrl = self.baseUrl

        let request = PasswordGrantRequestDto(clientId: clientId, clientSecret: clientSecret, username: username, password: password, grantType: "password")

        try await authenticationManager.authenticate(password: request)
    }
}

struct Authentication_Previews: PreviewProvider {
    static var previews: some View {
        AuthenticationView(
            clientId: "1200gn29g1n390gb",
            clientSecret: "a3bad9100gansg21ng1",
            username: "username",
            password: "password",
            isPresented: .constant(true)
        )
    }
}

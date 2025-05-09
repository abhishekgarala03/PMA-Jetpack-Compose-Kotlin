# Password Manager App (PMA)

A secure and user-friendly Android application for managing passwords with robust encryption and intuitive UI.

## Features

### Functional Requirements
- **Add Password**: Securely store new entries with account type, username/email, and password.
- **View/Edit Password**: View and modify existing entries, including all account details.
- **Password List**: Home screen displays all saved passwords with essential details.
- **Delete Password**: Remove unwanted or outdated entries.

### Technical Highlights
- **RSA Encryption**: Secures sensitive data using `EncryptionUtils` (RSA/ECB/PKCS1Padding).
- **Room Database**: Local storage with structured tables (`PasswordEntry`, `PasswordDao`).
- **Clean Architecture**: Separation of concerns with Repository, ViewModel, and Data layers.
- **Jetpack Components**: Utilizes DataStore for preferences, ViewModel for lifecycle-aware operations.
- **Input Validation**: Ensures mandatory fields are filled before submission.
- **Error Handling**: Graceful handling of edge cases and user feedback.

## Installation

1. **Clone the Repository**  
   `git clone https://github.com/abhishekgarala03/PMA-Jetpack-Compose-Kotlin.git`

2. **Open in Android Studio**  
   Import the project and sync Gradle dependencies.

3. **Build and Run**  
   Connect a device/emulator and click `Run` to install the app.

## Usage

### Adding a Password
1. Tap the "+" button on the home screen.
2. Fill in account type, username/email, and password.
3. Tap "Save" to encrypt and store the entry.

### Viewing/Editing
1. Tap an entry from the home screen list.
2. View details or modify fields in the bottom sheet.
3. Tap "Update" to save changes.

### Deleting
1. Tap an entry from the home screen list.
2. View details or modify fields in the bottom sheet.
3. Tap "Delete" to delete entry.

## Technical Details

### Encryption
- **RSA** encryption via `cypher.EncryptionUtils`.
- Keys are securely managed using `java.security.KeyPairGenerator`.

### Database
- **Room Database**: Stores encrypted data locally.
- Entity: `PasswordEntry` (ID, account type, username, encrypted password).
- DAO: `PasswordDao` provides CRUD operations with Flow support.

### Dependency Injection
- Hilt for DI with modules in `di` package:
  - `AppModule`: Application-level dependencies.
  - `DatabaseModule`: Room database setup.
  - `RepositoryModule`: Binds `PasswordRepositoryImpl`.

## Contributing
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/xyz`).
3. Commit changes and push to the branch.
4. Open a Pull Request.

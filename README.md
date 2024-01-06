# PetFinder App

Welcome to PetFinder, an Android app that helps you find information about various pet categories. This app includes features such as a splash screen, a home screen with category details, and more.

## Screenshots

![Splash Screen](screenshots/splash_screen.png)
*Caption: The engaging splash screen while the app loads.*

![Home Screen](screenshots/home_screen.png)
*Caption: The home screen displaying all categories of pets with detailed information.*

![Category Details Screen](screenshots/category_details_screen.png)
*Caption: The category details screen showing specific information for each pet category.*

## Features

- **Splash Screen:** Engage users with an appealing splash screen while the app loads.
- **Home Screen:** View all categories of pets with detailed information.
- **Category Details Screen:** Explore specific details for each pet category.

## Technologies Used

The PetFinder app is built using the following technologies and skills:

- **XML:** Used for designing the layout of screens.
- **Kotlin:** The primary programming language for Android development.
- **AES (Advanced Encryption Standard):** Ensures secure data transmission.
- **Android JeyStore:** A secure storage solution for sensitive data.
- **DataStore:** Android's modern data storage solution.
- **Room Database:** A SQLite object mapping library for local data storage.
- **Retrofit:** A powerful HTTP client for making network requests.
- **Dagger Hilt:** A dependency injection library for Android.
- **Unit Test:** Implementing unit tests to ensure code quality.
- **Pagination:** Efficiently load large datasets by fetching and displaying data in chunks.
- **Clean Code:** Adhering to clean coding practices for maintainability and readability.
- **Clean Architecture:** Organizing code in a modular and scalable architecture.
- **MVI (Model-View-Intent):** A design pattern for building user interfaces.
- **Navigation Components:** Simplifying the implementation of navigation in Android apps.

## Getting Started

Follow these steps to get the PetFinder app up and running on your local machine:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/gamalragab21/SoftxpertPetFinder
   cd petfinder

1. **Open in Android Studio:**
   Open the project in Android Studio by selecting "Open an existing Android Studio project" and navigating to the cloned `petfinder` directory.

2. **Set Up API Keys:**
   Obtain the necessary API keys for services like Retrofit or any other external APIs used in the app. Place the keys in the appropriate configuration file (e.g., `app/build.gradle` or a separate `api_keys.xml` file).

3. **Build and Run:**
   Build the project by clicking on the "Build" option in the top menu and selecting "Rebuild Project". After a successful build, run the app on an emulator or a physical device by clicking on the "Run" option.

4. **Explore the App:**
   Once the app is running, navigate through the splash screen, home screen, and category details screen to experience the features of PetFinder.

## Running Tests

To run unit tests, use the following command:
```bash
./gradlew test

## Contributing

We appreciate your interest in contributing to PetFinder! To make the process smoother, please follow these guidelines:

### Issues

If you encounter any issues with the app or have a feature request, please check if the issue has already been reported in the [Issues](https://github.com/your-username/petfinder/issues) section. If not, feel free to open a new issue, providing as much detail as possible.

### Pull Requests

We welcome contributions through pull requests. Before submitting a pull request, please:

1. Fork the repository and create a new branch for your feature or bug fix.
2. Make your changes, ensuring that your code follows the project's coding standards.
3. Write tests to cover your changes, if applicable.
4. Ensure that your code builds successfully and passes all existing tests.
5. Create a pull request, describing the changes and the problem or feature it addresses.

### Coding Standards

Please adhere to the coding standards and best practices used in the project. This includes proper code formatting, commenting, and following the established architecture.

### Feature Proposals

If you have a significant feature in mind, consider opening an issue to discuss it before implementing it. This helps ensure that your proposed feature aligns with the project's goals and doesn't overlap with existing or planned work.

### Code of Conduct

Be respectful and considerate when contributing to PetFinder. Follow our [Code of Conduct](CODE_OF_CONDUCT.md) to create a positive and inclusive environment for everyone.

Thank you for considering contributing to PetFinder! Your help is valuable in making the app better for everyone.


# CookLog CMP Project for Android & iOS

<img width="2752" height="1536" alt="cooklog_github_banner" src="https://github.com/user-attachments/assets/ddca9d46-9356-4f6e-ba04-9228d2d3833e" /> <br/>


CookLog is a modern, cross-platform recipe management application built with the Compose Multiplatform framework. It enables users to organize their culinary world by creating categories, managing recipes, and tracking cooking details, providing a seamless experience on both Android and iOS.

## Screenshots
Android (Jetpack Compose + CMP)<br/>
<br/><img width="1020" height="568" alt="Screenshot 2026-05-01 at 12 20 25" src="https://github.com/user-attachments/assets/b1618efa-5029-4de6-a430-9d8cb12199f3" />

iOS (Jetpack Compose + CMP)<br/>
<br/><img width="1029" height="569" alt="Screenshot 2026-05-01 at 12 20 34" src="https://github.com/user-attachments/assets/13d84a27-6ed3-41f0-9449-511ea393c68f" />

## Features
* Customizable Catalog Management: Organize your culinary collection. Create custom categories and personalize your catalog with gallery images or with defaults.
* Recipe Dashboard: A clean, responsive list view that allows you to jump straight into editing or viewing your saved recipes.
* Recipe Editor: A streamlined, modern editor that supports full recipe metadata, ingredient management, and rich text explanations.
* Detail View: A dedicated view for your recipes, keeping your focus on the cooking process.
* Search & Filtering: Never lose track of a recipe. Use search to filter through categories and recipe names in real-time.
* CRUD Operations: Full support for creating, reading, updating, and deleting recipes.
* Modern UI: Built entirely with Compose Multiplatform, ensuring a native, high-performance UI experience on both Android and iOS from a single codebase.

## Tech Stack & Architecture
This project is built using Clean Architecture principles and a multi-module approach, ensuring a modular codebase that is highly testable, scalable, and easy to maintain.
* UI: Compose Multiplatform (Sharing 100% of the UI code between Android and iOS)
* Dependency Injection: Koin (Lightweight and native-Kotlin friendly, perfect for multiplatform projects)
* Local Storage: Room with two tables for saving catalogs, recipes
* Build System: Gradle Convention Plugins (Centralizing build logic to ensure consistent configurations across all modules)
* Navigation: Compose Navigation.


# CookLog

<img width="2752" height="1536" alt="cooklog_github_banner" src="https://github.com/user-attachments/assets/ddca9d46-9356-4f6e-ba04-9228d2d3833e" />


CookLog is a modern, cross-platform recipe management application built with the Compose Multiplatform framework. It allows users to organize their culinary world by creating categories, managing recipes, and tracking cooking details with a seamless experience on both Android and iOS.

## Features
* Category Management: Organize recipes into custom categories, which are considered as a catalog screen with personalized images (Gallery or Defaults).
* Recipe: the user can add different recipes to the recipe list of each catalog.
* Smart Search: Quickly filter through your categories to find exactly what you're looking for.
* Full CRUD Operations: Create, Read, Update, and Delete recipes with a dedicated Detail and Edit flow.
* Modern UI: Built entirely with Jetpack Compose for a smooth, declarative interface across platforms.

## Tech Stack & Architecture
This project follows a clean, multi-module architecture to ensure separation of concerns and scalability.
* UI: Compose Multiplatform (Android/iOS)
* Dependency Injection: Koin
* Local Database: Room 
* Build System: Convention Plugins (Build Logic) for centralized Gradle management.
* Navigation: Compose Navigation.

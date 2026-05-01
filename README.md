# CookLog CMP Project for Android & iOS

<img width="2752" height="1536" alt="cooklog_github_banner" src="https://github.com/user-attachments/assets/ddca9d46-9356-4f6e-ba04-9228d2d3833e" /> <br/>


CookLog is a modern, cross-platform recipe management application built with the Compose Multiplatform framework. It allows users to organize their culinary world by creating categories, managing recipes, and tracking cooking details with a seamless experience on both Android and iOS.

## Screenshots
Android (Jetpack Compose + CMP)<br/>
<img width="1020" height="568" alt="Screenshot 2026-05-01 at 12 20 25" src="https://github.com/user-attachments/assets/b1618efa-5029-4de6-a430-9d8cb12199f3" />

iOS (Jetpack Compose + CMP)<br/>
<img width="1029" height="569" alt="Screenshot 2026-05-01 at 12 20 34" src="https://github.com/user-attachments/assets/13d84a27-6ed3-41f0-9449-511ea393c68f" />


## Features
* Category Management: Organize recipes into custom categories, which are considered as a catalog screen with personalized images (Gallery or Defaults).
* RecipeList: This is where the user can see the list of recipes and also can go to the Recipe Edit screen.
* Recipe Edit: In This Screen, the user can write recipe information or edit a saved recipe.
* Recipe Detail: The user can read the information of each recipe in this screen.
* Smart Search: Quickly filter through your categories to find exactly what you're looking for.
* Full CRUD Operations: Create, Read, Update, and Delete recipes with a dedicated Detail and Edit flow.
* Modern UI: Built entirely with Jetpack Compose for a smooth, declarative interface across platforms.

## Tech Stack & Architecture
This project follows a clean, multi-module architecture to ensure separation of concerns and scalability.
* UI: Compose Multiplatform (Android/iOS)
* Dependency Injection: Koin
* Local Database: Room with three tables for saving catalogs, recipes, and recipe images
* Build System: Convention Plugins (Build Logic) for centralized Gradle management.
* Navigation: Compose Navigation.

# FlashQuiz - Kotlin Multiplatform Learning App
## Introduction
FlashQuiz is a cross-platform mobile application designed to simplify and enhance the learning process through flashcard quizzes. Taking inspiration from apps like Anki and Kahoot, FlashQuiz aims to provide users with a straightforward and intuitive tool for creating, sharing, and revising quizzes.

## Targeted Users
FlashQuiz is tailored for students and learners in the age group of 14-60, encompassing a diverse range of individuals pursuing various fields of study or personal interests. 

Whether you're a university student, language learner, or someone seeking an efficient learning tool, FlashQuiz is designed to cater to your needs.

## Product Niche
In a market cluttered with feature-heavy learning apps, FlashQuiz stands out by prioritizing simplicity. 

The app focuses on essential functionalities, ensuring an intuitive user experience without unnecessary complexities. This streamlined approach not only enhances usability but also makes the app easy to maintain and develop further features.


## Features & Functionalities


### Quiz List View

Display existing quizzes.

Create new quizzes.

### Flashcard View

Present quiz questions one by one.

Track progress with a progress bar.

### Score View

Show the user's score at the end of the quiz.

### Create/Edit Quiz View

Add, edit, and delete quiz questions.




## This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…


### Resources:
1. PreCompose: https://tlaster.github.io/PreCompose/
2. PreCompose (Koin) implementation (iOS):
  https://medium.com/@nitheeshag/building-a-compose-multiplatform-app-with-an-architectural-pattern-e31a85e82927



### License:
We would like to declare that we are usign a software called PreCommpose from Tlaster and do not claim any right to it.
The reference to software can be found in References section p.1.

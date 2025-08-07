# Paykids - 학습용 금융 퀴즈 앱
<img width="3840" height="2160" alt="Image" src="https://github.com/user-attachments/assets/8a274114-770e-4900-b432-95caa029d022" />
<img width="3840" height="2160" alt="Image" src="https://github.com/user-attachments/assets/74416703-564a-4565-af6d-af695b0f4366" />
<img width="3840" height="2160" alt="Image" src="https://github.com/user-attachments/assets/6c5a3880-16d4-448f-9387-aefcad1ea5fd" />

<br>

## 📱 Project Overview

- **앱 이름**: PayKids(페이키즈)
- **작업 기간**: 2025.06 ~ 2025.08 (마켓 등록 준비 중)
- **인력 구성**:
    - 1차: 기획 2명 / 디자인 1명 / Android 2명 / BE 2명
    - 2차: Android 2명
- **핵심 기능**:
    - 다양한 퀴즈로 구성된 스테이지를 해금시키며 금융 지식을 재미있게 학습하도록 함
    - ChatGPT를 통해 대화형으로 학습 가능
    - 퀘스트를 달성하며 업적을 쌓아 올릴 수 있음
    - 소비와 수입을 기록하여 올바른 소비 습관을 기르도록 도와줌
- **플랫폼**: Android
- **개발 언어**: Kotlin
- **개발 환경**: Android Studio

<br>

## 🛠️ Tech Stack
| **분류** | **내용** |
| --- | --- |
| **Language** | Kotlin |
| **UI/UX** | Jetpack Compose Material3 |
| **Architecture** | Clean Architecture, MVVM |
| **Asynchronous** | Kotlin Coroutines, Flow |
| **Navigation** | Jetpack Navigation |
| **State Management** | ViewModel, StateFlow |
| **Local Storage** | Preference |
| **Networking** | Retrofit2, OkHttp3, Moshi |
| **Image Loading** | Coil3 |
| **Authentication** | Kakao Login API |
| **DI** | 수동 주입(Hilt 예정) |
| **Tools** | Git, Figma, Notion, Discord |

<br>

## 🗂️ Package Structure
```markdown
📁 PayKids
├── 📁 app
│   └── 📁 di
├── 📁 common
│	├── 📁 di
│   ├── 📁 enums
│   ├── 📁 exception
│   ├── 📁 mapper
│   ├── 📁 model
│   ├── 📁 repositories
│   ├── 📁 result
│   ├── 📁 usecase
│	└── 📁 util
├── 📁 data
│   ├── 📁 database
│   ├── 📁 mapper
│   ├── 📁 model
│   ├── 📁 network
│   │   └── 📁 service
│   ├── 📁 repositories
│   └── 📁 util
└── 📁 presentation
    ├── 📁 base
    ├── 📁 dummy
    ├── 📁 factory
    ├── 📁 mapper
    ├── 📁 model
    ├── 📁 navigation
    │ 	├── 📁 bottom
    │   └── 📁 route
    └── 📁 screens
        ├── 📁 allowance
        ├── 📁 home
        ├── 📁 login
        ├── 📁 mypage
        ├── 📁 quest
        ├── 📁 quiz
        ├── 📁 splash
        └── 📁 study
```

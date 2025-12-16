# 📰 News API Demo App

A simple Android application that demonstrates how to fetch data from a REST API and display it in a list. 

**Key Learning Concepts:**
* **Networking:** Using Retrofit to make HTTP requests.
* **Security:** Best practices for handling API keys (using `local.properties` and the Secrets Gradle Plugin).
* **Architecture:** MVVM pattern with Coroutines and LiveData/Flow.
* **UI:** Displaying images asynchronously using Coil.

---

## 🛑 Important: Setup Required

This project requires an API Key to function. **The app will not compile or fetch data until you add your own key.** We do not store API keys in version control (Git) to prevent them from being stolen or misused. Instead, we use a local configuration file.

### Step 1: Get a Free API Key
1.  Go to [NewsAPI.org](https://newsapi.org/).
2.  Click "Get API Key" and register for a free account.
3.  Copy the generated API Key to your clipboard.

### Step 2: Configure Android Studio
1.  Clone or download this project and open it in **Android Studio**.
2.  In the project files view (left sidebar), look for the file named **`local.properties`** in the root folder.
    * *Note: If you don't see it, switch the view from "Android" to "Project" at the top of the sidebar.*
3.  Open `local.properties` and add the following line at the bottom:

```properties
NEWS_API_KEY=paste_your_key_here_without_quotes

```

**Example:**

```properties
sdk.dir=...
NEWS_API_KEY=abc123456789xyz

```

###Step 3: Sync and Build1. Click the **"Sync Project with Gradle Files"** button (Elephant icon) in the top right.
2. **Build the project** (Build > Make Project) or simply **Run** the app on an emulator.
* *Note: You must build the project at least once for the code to recognize `BuildConfig.NEWS_API_KEY`.*



---

##🧐 How It Works (Under the Hood)###1. API Key SecurityInstead of hardcoding the key like this: `val apiKey = "12345"`, we use the **Secrets Gradle Plugin**.

* The plugin reads `NEWS_API_KEY` from your `local.properties` file.
* At build time, it generates a Java class called `BuildConfig`.
* We access the key in Kotlin safely:
```kotlin
val apiKey = BuildConfig.NEWS_API_KEY

```


* Since `local.properties` is listed in `.gitignore`, your key never gets pushed to GitHub!

###2. Networking (Retrofit)The app uses **Retrofit** to communicate with the News API.

* **Service Interface:** Defines the HTTP endpoints (e.g., `@GET("top-headlines")`).
* **ViewModel:** Launches a Coroutine to fetch data off the main thread to prevent the UI from freezing.
* **Response Handling:** The app handles three states: `Loading`, `Success` (updates the UI), and `Error` (shows a Toast message).

---

##🛠 Tech Stack* **Language:** Kotlin
* **HTTP Client:** Retrofit + OkHttp + Gson Converter
* **Image Loading:** Coil
* **Concurrency:** Kotlin Coroutines
* **Architecture:** MVVM (Model-View-ViewModel)

##🐛 Troubleshooting**"Unresolved reference: BuildConfig"**

* This happens if the project hasn't been built yet. Go to **Build > Make Project** to force the generation of the `BuildConfig` file.

**"401 Unauthorized"**

* Your API key is invalid or missing. Double-check your `local.properties` file and ensure there are no extra quotes `""` around the key.

```

***

### One Final Check for You (The Instructor)

Before you push this to your students, remember the **"First Clone Problem"**:

If a student clones the repo and the `local.properties` file is missing (which is normal for a fresh clone), the Gradle sync might fail immediately because it can't find the variable `NEWS_API_KEY`.

To make the README instructions work perfectly, ensure you have created and committed the **`local.defaults.properties`** file in your root folder as we discussed earlier:

**File: `local.defaults.properties`**
```properties
NEWS_API_KEY=DEFAULT_KEY

```

This ensures the project compiles immediately after cloning, allowing them to read the code before they even set up their own key.

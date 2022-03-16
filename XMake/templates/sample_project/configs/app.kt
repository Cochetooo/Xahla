// App metadata
val appName: String = env("APP_NAME", "Sample Project")
val appVersion: String = env("APP_VERSION", "1.0.0")
val appAuthor: String = env("APP_AUTHOR", "Unknown")
val appEnvironment: String = env("APP_ENV", "local")

// Engine logic
val updatePerSecond: Int = 60
val framePerSecond: Int = -1
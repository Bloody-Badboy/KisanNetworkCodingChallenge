
# Technologies

- Kotlin
- MVVM
- Coroutines
- AndroidX/Jetpack
- Koin
- Room (Arch components)
- OkHttp 3/Retrofit 2/Gson

## Use the project with your own Nexmo key

* Get you own Nexmo API key & secret. See the [get an API key][0].
* Find `.gradle` folder in your home directory, create a file named `gradle.properties` (if not present).

    Usually it can be found at:

        Windows: C:\Users\<Your Username>\.gradle
        Mac: /Users/<Your Username>/.gradle
        Linux: /home/<Your Username>/.gradle

        Inside it there would be a file named gradle.properties (just create it if there isn’t any).

* Open the `gradle.properties` file and paste your API key into the value of the `NEXMO_API_KEY` property and API secret into the value of the `NEXMO_API_SECRET` property, like this

    `NEXMO_API_KEY=PASTE-YOUR-API-KEY-HERE`
    `NEXMO_API_SECRET=PASTE-YOUR-API-SECRET-HERE`

* Now you should be able to successfully sync the project.

[0]: https://dashboard.nexmo.com/getting-started-guide
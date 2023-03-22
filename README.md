# PreferencesDelegate
Android SharedPreference delegate with kotlin flow
# How to use
Create some preference class
```
class SomePreferences (
    private val context: Context
) {
    companion object {
        private const val SOME_KEY = "SOME_KEY"
    }

    private val somePref =
        context.getSharedPreferences("SomePreference", Context.MODE_PRIVATE)

  //The place for preferences delegates

}
```
1. Use for simple value
```
var someValue: String by PreferencesDelegate<String, String>(
        preferences = userPref,
        name = SOME_KEY,
        defValue = ""
    )
```

2. Use for object
```
var someObject: SomeObject? by PreferencesDelegate<SomeObject?, String>(
        preferences = userPref,
        name = "SOME_KEY",
        defValue = null,
        onDifficultTypeTransform = {   // Transform the object to a string to write to preferences
            it?.let {
                Gson().toJson(someObject)
            } ?: ""
        },
        onPrimitiveTypeTransform = {   // Transform the string back to an object
            if (it.isEmpty()) {
                null
            } else {
                Gson().fromJson(it, SomeObject::class.java)
            }
        }
    )
```
3. Use with kotlin flow
```
private val _someValueFlow = MutableSharedFlow<String>(replay = 1)

/**
 * Returns the flow.
 */
val someValueFlow: SharedFlow<String> = _usersFlow

var someValue: String by PreferencesDelegate<String, String>(
        preferences = userPref,
        name = SOME_KEY,
        defValue = "",
        prefFlow = _someValueFlow
    )
```
# Sample
Download the project to see an sample of using PreferencesDelegate + RecyclerView + user list + flow

# To get a Git project into your build
Add it in your root build.gradle at the end of repositories
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```
dependencies {
	        implementation 'com.github.AndroidLab:PreferencesDelegate:Tag'
	}
```

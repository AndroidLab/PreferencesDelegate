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
        onDifficultTypeTransform = {
            it?.let {
                Gson().toJson(someObject)
            } ?: ""
        },
        onPrimitiveTypeTransform = {
            if (it.isEmpty()) {
                null
            } else {
                Gson().fromJson(it, SomeObject::class.java)
            }
        }
    )
```

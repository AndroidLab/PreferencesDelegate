# PreferencesDelegate
SharedPreference delegate with kotlin flow
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

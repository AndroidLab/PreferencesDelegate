package com.alab.preferencesdelegate

import android.content.Context
import com.alab.preferences_delegate.PreferencesDelegate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * User preferences.
 * @param context Context.
 */
@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val USERS = "USERS"
        private const val CURRENT_USER_ID = "CURRENT_USER_ID"
    }

    private val userPref =
        context.getSharedPreferences(UserPreferences::class.java.simpleName, Context.MODE_PRIVATE)

    private val _usersFlow = MutableSharedFlow<List<User>>(replay = 1)

    /**
     * Returns the flow of users.
     */
    val usersFlow: SharedFlow<List<User>> = _usersFlow

    /**
     * Returns or sets the list of users.
     */
    var users: List<User> by PreferencesDelegate(
        preferences = userPref,
        name = USERS,
        defValue = emptyList(),
        onDifficultTypeTransform = {
            Gson().toJson(it)
        },
        onPrimitiveTypeTransform = {
            Gson().fromJson(it, object : TypeToken<List<User>>(){}.type)
        },
        prefFlow = _usersFlow
    )


    private val _currentUserIdFlow = MutableSharedFlow<Int?>(replay = 1)

    /**
     * Returns the flow of the selected user id.
     */
    val currentUserIdFlow: SharedFlow<Int?> = _currentUserIdFlow

    /**
     * Возвращает выбранного пользователя для наблюдения.
     */
    var currentUserId: Int? by PreferencesDelegate(
        preferences = userPref,
        name = CURRENT_USER_ID,
        defValue = null,
        onDifficultTypeTransform = {
            it ?: 0
        },
        onPrimitiveTypeTransform = {
            if (it == 0) null else it
        },
        prefFlow = _currentUserIdFlow
    )

}
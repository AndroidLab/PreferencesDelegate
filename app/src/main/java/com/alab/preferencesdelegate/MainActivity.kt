package com.alab.preferencesdelegate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.alab.preferencesdelegate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding }

    @Inject
    lateinit var userPreferences: UserPreferences

    private val adapter by lazy {
        UsersAdapter(
            viewLifecycleOwner = this,
            onItemClick = { user ->
                userPreferences.currentUserId = user.id
            },
            onItemRemove = { user ->
                userPreferences.users -= user
                if (userPreferences.currentUserId == user.id) {
                    userPreferences.currentUserId = null
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.usersRecycler.adapter = adapter

        lifecycleScope.launch {
            userPreferences.usersFlow.collect { users ->
                adapter.submitList(users)
            }
        }

        lifecycleScope.launch {
            userPreferences.currentUserIdFlow.collect { userId ->
                binding.currentUser.text = "Current user:\n${userPreferences.users.firstOrNull {it.id == userId}}"
            }
        }

        binding.addUserBtn.setOnClickListener {
            userPreferences.users +=
                User(
                    id = Random().nextInt(Int.MAX_VALUE),
                    name = binding.userName.text.toString(),
                    salary = binding.userSalary.text.toString().toInt()
                )
        }

    }
}
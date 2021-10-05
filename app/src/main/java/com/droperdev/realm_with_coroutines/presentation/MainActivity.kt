package com.droperdev.realm_with_coroutines.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import com.droperdev.realm_with_coroutines.R
import com.droperdev.realm_with_coroutines.data.local.entity.Book
import com.droperdev.realm_with_coroutines.data.local.entity.Person
import com.droperdev.realm_with_coroutines.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.RealmList

class MainActivity : AppCompatActivity() {

    private val personViewModel by viewModels<PersonViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObservers()


    }

    private fun initUI() {
        binding.btnGet.setOnClickListener {
            personViewModel.getCount()
        }
        binding.btnSave.setOnClickListener {
            personViewModel.insert()
        }
    }

    private fun initObservers() {
        personViewModel.count.observe(this, {
            Log.d("-->", String.format("%s", it))
        })

        personViewModel.loading.observe(this, {
            binding.btnSave.visibility = if (it) GONE else VISIBLE
        })
    }


}
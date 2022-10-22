package com.kc.marvelapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kc.marvelapp.R
import com.kc.marvelapp.db.CharacterDatabase
import com.kc.marvelapp.repository.MarvelRepository
import com.kc.marvelapp.viewmodel.MarvelViewModel
import com.kc.marvelapp.viewmodel.MarvelViewModelFactory
import kotlinx.android.synthetic.main.activity_marvel.*

class MarvelActivity : AppCompatActivity() {

    lateinit var viewModel: MarvelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marvel)
        val marvelRepository = MarvelRepository(CharacterDatabase.getDatabase(this))
        val viewModelFactory = MarvelViewModelFactory(marvelRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MarvelViewModel::class.java)
        bottomNavigationView.setupWithNavController(marvelNavHostFragment.findNavController())
    }
}
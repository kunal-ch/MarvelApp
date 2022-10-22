package com.kc.marvelapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kc.marvelapp.R
import com.kc.marvelapp.db.CharacterDatabase
import com.kc.marvelapp.repository.MarvelRepository
import com.kc.marvelapp.ui.MarvelActivity
import com.kc.marvelapp.viewmodel.MarvelViewModel
import com.kc.marvelapp.viewmodel.MarvelViewModelFactory

class CharacterDetailFragment: Fragment(R.layout.fragment_character_detail) {
    lateinit var viewModel: MarvelViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val marvelRepository = MarvelRepository(CharacterDatabase.getDatabase(activity as MarvelActivity))
        val viewModelFactory = MarvelViewModelFactory(marvelRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MarvelViewModel::class.java)
    }
}
package com.kc.marvelapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kc.marvelapp.R
import com.kc.marvelapp.db.CharacterDatabase
import com.kc.marvelapp.models.ComicCharacter
import com.kc.marvelapp.repository.MarvelRepository
import com.kc.marvelapp.ui.MarvelActivity
import com.kc.marvelapp.viewmodel.MarvelViewModel
import com.kc.marvelapp.viewmodel.MarvelViewModelFactory
import kotlinx.android.synthetic.main.fragment_character_detail.*
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterDetailFragment: Fragment(R.layout.fragment_character_detail) {
    lateinit var viewModel: MarvelViewModel
    val args: CharacterDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val marvelRepository = MarvelRepository(CharacterDatabase.getDatabase(activity as MarvelActivity))
        val viewModelFactory = MarvelViewModelFactory(marvelRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MarvelViewModel::class.java)
        val character = args.character
        val thumbnail = character.thumbnail.path + "." +character.thumbnail.extension
        Glide.with(this).load(thumbnail).into(ivHero)
        tvDescription.text = character.description

        if (character.isFavourite) {
            fab.setImageDrawable(
                ContextCompat.getDrawable(
                    activity as MarvelActivity,
                    R.drawable.ic_favorite_filled
                )
            )
        } else {
            fab.setImageDrawable(
                ContextCompat.getDrawable(
                    activity as MarvelActivity,
                    R.drawable.ic_favorite
                )
            )
        }

        fab.setOnClickListener {
            character.isFavourite = !character.isFavourite
            setFabIcon(character)
            viewModel.addToFavourites(character)
            Snackbar.make(view, "Character liked successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun setFabIcon(character: ComicCharacter){
        if (character.isFavourite) {
            fab.setImageDrawable(
                ContextCompat.getDrawable(
                    activity as MarvelActivity,
                    R.drawable.ic_favorite_filled
                )
            )
        } else {
            fab.setImageDrawable(
                ContextCompat.getDrawable(
                    activity as MarvelActivity,
                    R.drawable.ic_favorite
                )
            )
        }
    }
}
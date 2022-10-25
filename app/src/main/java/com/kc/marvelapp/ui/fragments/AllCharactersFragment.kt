package com.kc.marvelapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc.marvelapp.R
import com.kc.marvelapp.adapters.CharacterAdapter
import com.kc.marvelapp.db.CharacterDatabase
import com.kc.marvelapp.repository.MarvelRepository
import com.kc.marvelapp.ui.MarvelActivity
import com.kc.marvelapp.util.Resource
import com.kc.marvelapp.viewmodel.MarvelViewModel
import com.kc.marvelapp.viewmodel.MarvelViewModelFactory
import kotlinx.android.synthetic.main.fragment_all_characters.*

class AllCharactersFragment: Fragment(R.layout.fragment_all_characters) {

    lateinit var viewModel: MarvelViewModel
    lateinit var characterAdapter: CharacterAdapter
    private val TAG =  "AllCharactersFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val marvelRepository = MarvelRepository(CharacterDatabase.getDatabase(activity as MarvelActivity))
        val viewModelFactory = MarvelViewModelFactory(marvelRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MarvelViewModel::class.java)
        setUpRecyclerView()
        setUpObservers()
        //viewModel.getAllCharacters()

        viewModel.getGetAllCharacterFromDB().observe(viewLifecycleOwner, Observer { characters ->
            Log.d("MarvelRepository", "db response successfull")
            characterAdapter.differ.submitList(characters)
        })

        characterAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("character", it)
            }
            findNavController().navigate(
                R.id.action_allCharactersFragment_to_characterDetailFragment,
                bundle
            )
        }
    }

    private fun setUpObservers() {
        viewModel.allCharacters.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideLoading()
                    response.data?.let { characterList ->
                        characterAdapter.differ.submitList(characterList)
                    }
                }
                is Resource.Error -> {
                    hideLoading()
                    response.message?.let { message ->
                        Log.d(TAG, "Error occurred : $message")
                    }
                }
                is Resource.Loading -> {
                    showLoading()
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        characterAdapter = CharacterAdapter()
        rvAllCharacters.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
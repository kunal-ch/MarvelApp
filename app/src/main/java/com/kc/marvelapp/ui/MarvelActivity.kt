package com.kc.marvelapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kc.marvelapp.R
import kotlinx.android.synthetic.main.activity_marvel.*

class MarvelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marvel)
        bottomNavigationView.setupWithNavController(marvelNavHostFragment.findNavController())
    }
}
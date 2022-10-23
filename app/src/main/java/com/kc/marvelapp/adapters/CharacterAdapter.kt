package com.kc.marvelapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kc.marvelapp.R
import com.kc.marvelapp.models.ComicCharacter
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter: RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<ComicCharacter>() {
        override fun areItemsTheSame(oldItem: ComicCharacter, newItem: ComicCharacter): Boolean {
            return oldItem.resourceURI == newItem.resourceURI
        }

        override fun areContentsTheSame(oldItem: ComicCharacter, newItem: ComicCharacter): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_character, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.itemView.apply {
            val character = differ.currentList[position]
            val thumbnail = character.thumbnail.path + "." +character.thumbnail.extension
            Glide.with(this).load(thumbnail).into(ivCharacterImage)
            tvName.text = character.name
            tvDescription.text = character.description
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
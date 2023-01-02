package com.example.marvel.ui.general.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.databinding.CharacterListItemBinding
import com.example.marvel.domain.models.Character

class GeneralCharactersAdapter(
    private val onClick: ((Int, View) -> Unit)
) :
    RecyclerView.Adapter<GeneralCharactersAdapter.ViewHolder>() {

    var items: List<Character> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CharacterListItemBinding.bind(view)
        fun bind(character: Character) {
            binding.characterItemName.text = character.name
            Glide
                .with(itemView.context)
                .load(character.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_captain_america)
                .into(binding.characterItemImage)
            binding.characterItemCard.setOnClickListener {
                onClick(character.id, it)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
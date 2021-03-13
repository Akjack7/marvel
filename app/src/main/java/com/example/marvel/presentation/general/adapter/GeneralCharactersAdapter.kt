package com.example.marvel.presentation.general.adapter

import Results
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.databinding.CharacterListItemBinding

class GeneralCharactersAdapter(
    private val listener: Action,
) :
    RecyclerView.Adapter<GeneralCharactersAdapter.ViewHolder>() {

    var items: MutableList<Results> = mutableListOf()

    interface Action {
        fun onclick(character: Results)
    }

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
        fun bind(result: Results) {
            binding.characterItemName.text = result.name
            val imagePath =
                result.thumbnail.path + "/landscape_xlarge." + result.thumbnail.extension
            Glide
                .with(itemView.context)
                .load(imagePath.replace("http", "https"))
                .centerCrop()
                .placeholder(R.drawable.ic_captain_america)
                .into(binding.characterItemImage)
            binding.characterItemCard.setOnClickListener {
                listener.onclick(result)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
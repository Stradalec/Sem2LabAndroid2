package com.example.sem2labandroid2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharacterAdapter : ListAdapter<Character, BaseCharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCharacterViewHolder {
        return when (viewType) {
            VIEW_TYPE_HUMAN -> HumanViewHolder(inflateView(R.layout.character_item, parent))
            VIEW_TYPE_ALIEN -> AlienViewHolder(inflateView(R.layout.character_item, parent))
            else -> OtherViewHolder(inflateView(R.layout.character_item, parent))
        }
    }

    override fun onBindViewHolder(holder: BaseCharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).species.lowercase()) {
            "human" -> VIEW_TYPE_HUMAN
            "alien" -> VIEW_TYPE_ALIEN
            else -> VIEW_TYPE_OTHER
        }
    }

    private fun inflateView(@LayoutRes layoutRes: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }

    companion object {
        private const val VIEW_TYPE_HUMAN = 0
        private const val VIEW_TYPE_ALIEN = 1
        private const val VIEW_TYPE_OTHER = 2
    }
}

abstract class BaseCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(character: Character)
}

class HumanViewHolder(itemView: View) : BaseCharacterViewHolder(itemView) {
    private val portraitImageView: ImageView = itemView.findViewById(R.id.ivCharacter_icon)
    private val nameTextView: TextView = itemView.findViewById(R.id.tvName)
    private val speciesTextView: TextView = itemView.findViewById(R.id.tvSpecies)
    private val originTextView: TextView = itemView.findViewById(R.id.tvAdditonal)

    override fun bind(character: Character) {

        nameTextView.text = character.name
        speciesTextView.text = character.species
        originTextView.text = "Origin: ${character.origin.name}"

        val imageUrl = character.image
        Glide.with(itemView.context)
            .load(imageUrl)
            .into(portraitImageView)
    }
}

class AlienViewHolder(itemView: View) : BaseCharacterViewHolder(itemView) {
    private val portraitImageView: ImageView = itemView.findViewById(R.id.ivCharacter_icon)
    private val nameTextView: TextView = itemView.findViewById(R.id.tvName)
    private val speciesTextView: TextView = itemView.findViewById(R.id.tvSpecies)
    private val planetTextView: TextView = itemView.findViewById(R.id.tvAdditonal)

    override fun bind(character: Character) {
        nameTextView.text = character.name
        speciesTextView.text = character.species
        planetTextView.text = "Planet: ${character.origin.name}"

        val imageUrl = character.image
        Glide.with(itemView.context)
            .load(imageUrl)
            .into(portraitImageView)
    }
}

class OtherViewHolder(itemView: View) : BaseCharacterViewHolder(itemView) {
    private val portraitImageView: ImageView = itemView.findViewById(R.id.ivCharacter_icon)
    private val nameTextView: TextView = itemView.findViewById(R.id.tvName)
    private val speciesTextView: TextView = itemView.findViewById(R.id.tvSpecies)
    private val typeTextView: TextView = itemView.findViewById(R.id.tvAdditonal)

    override fun bind(character: Character) {
        nameTextView.text = character.name
        speciesTextView.text = character.species
        typeTextView.text = "Type: ${character.type.ifEmpty { "Unknown" }}"

        val imageUrl = character.image
        Glide.with(itemView.context)
            .load(imageUrl)
            .into(portraitImageView)
    }
}
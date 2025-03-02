package com.example.sem2labandroid2

import androidx.recyclerview.widget.DiffUtil

class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem &&
                oldItem.name == newItem.name &&
                oldItem.species == newItem.species
    }
}
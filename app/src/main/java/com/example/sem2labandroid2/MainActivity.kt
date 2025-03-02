package com.example.sem2labandroid2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val viewModel: RickAndMortyViewModel by viewModels()
    private var adapter: CharacterAdapter = CharacterAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initialiseRecyclerView()
        observeCharacterList()
        getCharacters()

    }
    private fun initialiseRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.rView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    private fun observeCharacterList() {
        viewModel.characterData.observe(this) { characters ->
            adapter.submitList(characters)
        }
    }
    private fun getCharacters() {
        viewModel.fetchCharacters()
    }
}

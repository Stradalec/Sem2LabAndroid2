package com.example.sem2labandroid2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private var adapter: CharacterAdapter = CharacterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initialiseRecyclerView()
        observeCharacterList()

        findViewById<Button>(R.id.btnGetCharacters).setOnClickListener {
            val start = findViewById<EditText>(R.id.etStart).text.toString()
            val end = findViewById<EditText>(R.id.etEnd).text.toString()
            if (start.isNotEmpty() && end.isNotEmpty()) {
                getCharacters(start, end)
            }
        }
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

    private fun getCharacters(start: String, end: String) {
        viewModel.fetchCharacters(start.toInt(), end.toInt())
    }
}

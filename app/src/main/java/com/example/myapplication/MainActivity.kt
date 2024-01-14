// MainActivity.kt
package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.MagicCard

class MainActivity : AppCompatActivity() {

    private lateinit var magicRepository: MagicRepository
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        magicRepository = MagicRepository()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchAndDisplayCards()
    }

    private fun fetchAndDisplayCards() {
        magicRepository.getMagicCards(
            onSuccess = { magicCards ->
                val adapter = MagicCardAdapter(magicCards)
                recyclerView.adapter = adapter
            },
            onFailure = { throwable ->
                // Handle error
                throwable.printStackTrace()
            }
        )
    }
}

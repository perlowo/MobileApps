// MainActivity.kt
package com.example.myapplication

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
                val adapter = MagicCardAdapter(this,magicCards){ clickedMagicCard ->
                    val description = buildString {
                        val properties = clickedMagicCard::class.java.declaredFields
                        val maxDisplayLength = 60
                        // Set the maximum displayable length for property names and values
                        for (property in properties) {
                            property.isAccessible = true
                            val propertyName = property.name
                            val formattedPropertyName = if (propertyName.length > maxDisplayLength) {
                                propertyName.substring(0, maxDisplayLength - 3) + "..."
                            } else {
                                propertyName
                            }
                            val propertyValue = property.get(clickedMagicCard)?.toString() ?: ""
                            val formattedPropertyValue = if (propertyValue.length > maxDisplayLength) {
                                propertyValue.substring(0, maxDisplayLength - 3) + "..."
                            } else {
                                propertyValue
                            }
                            append("<b>${formattedPropertyName.capitalize()}:</b> $formattedPropertyValue<br>")
                        }

                        // Log the values for the sample MagicCard
                        Log.d("SampleMagicCard", "Name: ${clickedMagicCard.name}")
                        Log.d("SampleMagicCard", "Description: $this")
                    }

                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle(clickedMagicCard.name)
                    dialogBuilder.setMessage(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY))

                    dialogBuilder.setPositiveButton("OK") { dialog, which ->
                        // Handle OK button click if needed
                        dialog.dismiss()
                    }

                    val alertDialog = dialogBuilder.create()
                    alertDialog.show()

                    // Set the message text appearance for better formatting
                    val messageTextView = alertDialog.findViewById<TextView>(android.R.id.message)
                    messageTextView?.textSize = 18f
                    messageTextView?.gravity = Gravity.LEFT
                    messageTextView?.setLineSpacing(8f, 1.2f) // Adjust line spacing as needed
                    messageTextView?.setPadding(16, 8, 16, 8)
                }
                recyclerView.adapter = adapter
            },
            onFailure = { throwable ->
                // Handle error
                throwable.printStackTrace()
            }
        )
    }
}

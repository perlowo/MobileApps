package com.example.myapplication

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.model.MagicCard
import kotlin.random.Random


class MagicCardAdapter(private val magicCards: List<MagicCard>) :
    RecyclerView.Adapter<MagicCardAdapter.MagicCardViewHolder>() {

    class MagicCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardName: TextView = itemView.findViewById(R.id.cardName)
        val cardType: TextView = itemView.findViewById(R.id.cardType)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MagicCardViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_magic_card, parent, false)
        return MagicCardViewHolder(itemView)
    }

    private fun useTLS(imageUrl: String): String {
        return imageUrl.replace("http://", "https://")
    }

    override fun onBindViewHolder(holder: MagicCardViewHolder, position: Int) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val magicCard = magicCards[position]
        holder.cardName.text = magicCard.name
        holder.cardType.text = magicCard.type

        if (magicCard.foreignNames != null && magicCard.foreignNames.isNotEmpty()) {
//            val imageUrl = useTLS(magicCard.foreignNames[0].imageUrl.trim())
//            Log.d("Glide", "Loading image from: $imageUrl")

            Glide.with(holder.imageView.context)
                .load("https://www.mtgpics.com/pics/big/rvr/${Random.nextInt(150,300)}.jpg")
//                .load(imageUrl)
                .placeholder(ColorDrawable(Color.BLACK))
                .error(ColorDrawable(Color.BLACK))
                .override(400, 400)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int = magicCards.size
}

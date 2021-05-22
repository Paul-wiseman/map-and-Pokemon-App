package com.decagon.android.sq007.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.android.sq007.R
import com.decagon.android.sq007.pokemon.Result
import com.decagon.android.sq007.ui.ClickListener
import com.decagon.android.sq007.utils.getPokemonNo

class PokemonAdapter(private val pokemonList: List<Result>, var context: Context, private val listener: ClickListener) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    inner class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.iv_pokemon)
        val tv_name = view.findViewById<TextView>(R.id.tv_pokemon_name)
        val tv_id = view.findViewById<TextView>(R.id.tv_pokemonID)
        val cardLayout = view.findViewById<CardView>(R.id.cv_pokemon_cardview)

//        init {
//            view.setOnClickListener {
//                var intent = Intent(view.context, PokemonDetailsActivity::class.java)
//                context.startActivity(intent)
//            }
//        }

        fun bind(item: Result) {
            tv_name.text = item.name
            val pokemonNo = item.url?.let { getPokemonNo(it) }
            tv_id.text = pokemonNo

            // using glide for fetching loading and displaying of the pokemon image from the API
            Glide.with(context)
                // the URL from which the images are loaded from
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonNo.png")
                .placeholder(R.drawable.loading_placeholder)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_layout, parent, false)
        return PokemonViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])

        holder.cardLayout.setOnClickListener {
            val url = pokemonList[position].url
            if (url != null) {
                listener.onItemClicked(url)
            }
        }
    }
}

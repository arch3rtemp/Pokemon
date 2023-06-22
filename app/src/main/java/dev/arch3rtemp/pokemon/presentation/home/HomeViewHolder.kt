package dev.arch3rtemp.pokemon.presentation.home

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dev.arch3rtemp.pokemon.R
import dev.arch3rtemp.pokemon.databinding.PokemonItemBinding
import dev.arch3rtemp.pokemon.domain.model.Pokemon

class HomeViewHolder(
    private val binding: PokemonItemBinding,
    private val clickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @ColorInt private var dominantColor: Int = Color.GRAY

    fun bind(pokemon: Pokemon) = with(binding) {

        cardView.setCardBackgroundColor(dominantColor)

        setPokemonImages(pokemon.image.frontDefault)

        pokemonNumber.text = pokemonName.context.getString(R.string.pokemon_number_format, pokemon.id)
        pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
        pokeInfoTypeOne.text = pokemon.typeList[0].type.name

        if (pokemon.typeList.size > 1) {
            pokeInfoTypeTwo.visibility = View.VISIBLE
            pokeInfoTypeTwo.text = pokemon.typeList[1].type.name
        } else {
            pokeInfoTypeTwo.visibility = View.GONE
        }

        cardView.setOnClickListener {
            clickListener(pokemon.id)
        }
    }

    private fun setPokemonImages(image: String) {
        Glide.with(itemView.context.applicationContext)
            .asBitmap()
            .load(image)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource != null) {
                        setBgColor(resource)
                    }
                    return false
                }
            })
            .centerCrop()
            .into(binding.pokemonImage)
    }

    private fun setBgColor(resource: Bitmap) {
        Palette.Builder(resource).generate {
            it?.let { palette ->
                when (binding.pokemonImage.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        dominantColor = palette.getMutedColor(Color.GRAY)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        dominantColor = palette.getLightMutedColor(Color.GRAY)
                    }
                }
                binding.cardView.setCardBackgroundColor(dominantColor)
            }
        }
    }
}
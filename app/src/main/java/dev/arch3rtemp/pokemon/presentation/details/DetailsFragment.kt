package dev.arch3rtemp.pokemon.presentation.details

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.pokemon.R
import dev.arch3rtemp.pokemon.databinding.FragmentDetailsBinding
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.domain.model.TypeResponse

private const val ARG_ID = "arg_id"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var argId: Int? = null

    private var _binding: FragmentDetailsBinding? = null
    private val viewModel by viewModels<DetailsViewModel>()

    private var dominantColor = Color.GRAY

    val binding: FragmentDetailsBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argId = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setupObservers()
        viewModel.setEvent(DetailsContract.Event.OnPokeLoad(argId!!))
    }

    private fun setListeners() = with(binding) {
        pokeBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { result ->
            renderState(result.detailsState)
        }
    }

    private fun renderState(state: DetailsContract.DetailsState) {
        when (state) {
            DetailsContract.DetailsState.Idle -> Unit
            DetailsContract.DetailsState.Loading -> {}
            is DetailsContract.DetailsState.Error -> {}
            is DetailsContract.DetailsState.Success -> {
                setData(state.pokemon)
            }
        }
    }

    private fun setData(pokemon: Pokemon) = with(binding) {
        pokeXp.text = pokemon.baseExperience.toString()
        pokeHeight.text = getString(R.string.height_format, (pokemon.height.times(10)))
        pokeWeight.text = getString(R.string.weight_format, (pokemon.weight.div(10.0)))
        pokeHp.text = pokemon.statList[0].baseStat.toString()
        pokeAttack.text = pokemon.statList[1].baseStat.toString()
        pokeDefense.text = pokemon.statList[2].baseStat.toString()
        pokeSpecialAttack.text = pokemon.statList[3].baseStat.toString()
        pokeSpecialDefense.text = pokemon.statList[4].baseStat.toString()
        pokeSpeed.text = pokemon.statList[5].baseStat.toString()
        pokeInfoTypeOne.text = pokemon.typeList[0].type.name
        setPokemonTypes(pokemon.typeList)
        loadImage(pokeInfoImage, pokemon.image.frontDefault)
        pokeScrollView.setBackgroundColor(dominantColor)
    }

    private fun setPokemonTypes(types: List<TypeResponse>) = with(binding) {
        if (types.size > 1) {
            pokeInfoTypeTwo.text = types[1].type.name
            pokeInfoTypeTwo.visibility = View.VISIBLE
        } else {
            pokeInfoTypeTwo.visibility = View.GONE
        }
    }

    private fun loadImage(pokeInfoImage: ImageView, imageUrl: String) {
        Glide.with(requireContext().applicationContext)
            .asBitmap()
            .load(imageUrl)
            .centerCrop()
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
            .into(pokeInfoImage)
    }

    private fun setBgColor(resource: Bitmap) {
        Palette.Builder(resource).generate {
            it?.let { palette ->
                when (binding.pokeInfoImage.context.resources?.configuration?.uiMode?.and(
                    Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        dominantColor = palette.getMutedColor(Color.GRAY)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        dominantColor = palette.getLightMutedColor(Color.GRAY)
                    }
                }
                binding.root.setBackgroundColor(dominantColor)
                requireActivity().window?.statusBarColor = dominantColor
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.bg_color)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param id The id of pokemon object.
         * @return A new instance of fragment DetailsFragment.
         */
        @JvmStatic
        fun newInstance(id: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
    }
}
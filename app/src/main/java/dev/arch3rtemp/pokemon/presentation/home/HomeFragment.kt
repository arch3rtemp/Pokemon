package dev.arch3rtemp.pokemon.presentation.home

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.ColorRes
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.pokemon.R
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.presentation.details.DetailsFragment
import dev.arch3rtemp.pokemon.util.ShimmerHelper
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private var homeAdapter: HomeAdapter? = null

    private lateinit var rvHomeList: RecyclerView
    private lateinit var shimmerList: ShimmerFrameLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupObservers()
    }

    private fun setupRecyclerView(view: View) {
        homeAdapter = HomeAdapter { position ->
            navigateToDetails(position)
        }

        rvHomeList = view.findViewById(R.id.rvHomeList)
        shimmerList = view.findViewById(R.id.shimmerList)
        rvHomeList.adapter = homeAdapter
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { result ->

            renderState(result.homeState)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.consumeEach { effect ->
                    renderEffect(effect)
                }
            }
        }
    }

    private fun navigateToDetails(id: Int) {
        val detailsFragment = DetailsFragment.newInstance(id)
        parentFragmentManager
            .commit {
                replace(R.id.fragmentContainer, detailsFragment)
                addToBackStack(detailsFragment.tag)
            }
    }

    private fun renderState(state: HomeContract.HomeState) {

        when (state) {
            HomeContract.HomeState.Idle -> Unit
            HomeContract.HomeState.Loading -> showShimmer()
            HomeContract.HomeState.Empty -> hideShimmer()
            is HomeContract.HomeState.Error -> hideShimmer()
            is HomeContract.HomeState.Success -> {
                hideShimmer()
                homeAdapter?.submitList(state.pokemons)
            }
        }
    }

    private fun renderEffect(effect: HomeContract.Effect) {

        when (effect) {
            is HomeContract.Effect.ShowSnackBar -> {
                Snackbar.make(requireView(), effect.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun showShimmer() {

        shimmerList.visibility = View.VISIBLE
        rvHomeList.visibility = View.GONE

        val shimmerCount = getShimmerCount().toInt()

        ShimmerHelper
            .Builder(shimmerList, requireContext())
            .addView(R.layout.pokemon_item_placeholder, shimmerCount)
            .build()
    }

    private fun hideShimmer() {
        shimmerList.visibility = View.GONE
        rvHomeList.visibility = View.VISIBLE
    }

    private fun getScreenLength() = resources.displayMetrics.heightPixels

    private fun getShimmerCount() = convertPxToDp((getScreenLength() / 90f) - 1)

    private fun convertPxToDp(px: Float): Float {
        return px / Resources.getSystem().displayMetrics.density
    }

    override fun onDestroyView() {
        super.onDestroyView()

        homeAdapter = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
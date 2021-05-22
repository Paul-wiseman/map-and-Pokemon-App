package com.decagon.android.sq007.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.decagon.android.sq007.databinding.FragmentPokemonDetailBinding
import com.decagon.android.sq007.ui.adapter.ViewPagerAdapter
import com.decagon.android.sq007.utils.getPokemonName
import com.decagon.android.sq007.utils.getPokemonNo
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailpager_viewpager: ViewPager2
    private lateinit var tabLayout: TabLayout
    lateinit var tv_pokemonName: TextView
    lateinit var tv_pokemonID: TextView
    val args: PokemonDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokeUrl = args.pokemonUrl
        tv_pokemonName = binding.tvPokemonName
        tv_pokemonID = binding.tvPokemonId
        tv_pokemonName.text = getPokemonName(pokeUrl)
        tv_pokemonID.text = getPokemonNo(pokeUrl)

        Glide.with(requireContext())
            // the URL from which the images are loaded from
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${getPokemonNo(pokeUrl)}.png")
            .into(binding.ivPokemonImage)

        val fragments: ArrayList<Fragment> = arrayListOf(
            MoveFragment.newInstance(pokeUrl),
            StatFragment.newInstance(
                pokeUrl
            )
        )

        val adapter = ViewPagerAdapter(fragments, requireParentFragment())
        detailpager_viewpager = binding.detailpageViewPager
        detailpager_viewpager.adapter = adapter

        tabLayout = binding.tablayout
        TabLayoutMediator(
            tabLayout, detailpager_viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "MOVES" }
                    1 -> { tab.text = "STATS" }
                }
            }
        ).attach()
    }
}

package com.decagon.android.sq007.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.databinding.FragmentStatBinding
import com.decagon.android.sq007.model.network.ServiceBuilder
import com.decagon.android.sq007.ui.adapter.StatAdapter
import com.decagon.android.sq007.utils.POKE_URL
import com.decagon.android.sq007.viewmodel.PokemonViewModel
import com.decagon.android.sq007.viewmodel.ViewModelFactory

class StatFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentStatBinding? = null
    private val binding get() = _binding!!

    private var pokeUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokeUrl = it.getString(POKE_URL)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvStatFragment
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val pokeApi = ServiceBuilder.getPokemonApi()
        val viewModelFactory = ViewModelFactory(pokeApi)
        //  val model: PokemonViewModel by viewModels()
        val model = ViewModelProvider(this, viewModelFactory).get(PokemonViewModel::class.java)
//        var connectivityManager = ConnectivityMana
        pokeUrl?.let { model.getPokemnonDetails(it) }

        model.pokemonStats.observe(
            viewLifecycleOwner,
            Observer {
                recyclerView.adapter = it.stats?.let { it1 -> StatAdapter(it1) }
            }
        )
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MoveFragment().apply {
                arguments = Bundle().apply {
                    putString(POKE_URL, param1)
                }
            }
    }
}

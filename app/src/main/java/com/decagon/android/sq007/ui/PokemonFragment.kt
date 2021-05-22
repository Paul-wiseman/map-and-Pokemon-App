package com.decagon.android.sq007.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.databinding.FragmentPokemonBinding
import com.decagon.android.sq007.model.network.ServiceBuilder
import com.decagon.android.sq007.ui.adapter.PokemonAdapter
import com.decagon.android.sq007.viewmodel.PokemonViewModel
import com.decagon.android.sq007.viewmodel.ViewModelFactory

class PokemonFragment : Fragment(), ClickListener {
    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvPokemon

        val pokeApi = ServiceBuilder.getPokemonApi()
        val viewModelFactory = ViewModelFactory(pokeApi)
        val model = ViewModelProvider(this, viewModelFactory).get(PokemonViewModel::class.java)
        model.getPokemon()
        model.pokemon.observe(
            viewLifecycleOwner,
            Observer {
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = it.results?.let { results ->
                    PokemonAdapter(
                        results,
                        requireContext(),
                        this
                    )
                }
            }
        )
    }

    override fun onItemClicked(url: String) {
        val action = PokemonFragmentDirections.actionPokemonFragmentToPokemonDetailFragment(url)
        findNavController().navigate(action)
//        fragmentManager?.beginTransaction()?.replace(R.id.fl_fragment, PokemonDetailFragment())
//            ?.commit()
        //    String var intent = Intent(view.context, PokemonDetails)
//                context.startActivity(intent)
    }
}

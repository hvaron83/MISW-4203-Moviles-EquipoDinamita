package com.uniandes.vinilo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinilo.R
import com.uniandes.vinilo.databinding.ArtistaFragmentBinding
import com.uniandes.vinilo.ui.adapters.ArtistasAdapter
import com.uniandes.vinilo.viewmodels.ArtistaViewModel

class ArtistaFragment : Fragment() {
    private var _binding: ArtistaFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistaViewModel
    private var viewModelAdapter: ArtistasAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtistaFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = ArtistasAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.artistasRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_artista)
        viewModel = ViewModelProvider(this, ArtistaViewModel.Factory(activity.application))[ArtistaViewModel::class.java]
        viewModel.artistas.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.artistas = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
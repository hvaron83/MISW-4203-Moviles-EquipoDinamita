package com.uniandes.vinilo.ui.artista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uniandes.vinilo.R
import com.uniandes.vinilo.common.dto.Artista
import com.uniandes.vinilo.databinding.FragmentArtistaBinding
import com.uniandes.vinilo.ui.artista.adapter.ArtistaAdapter
import com.uniandes.vinilo.ui.artista.viewModel.DetailArtistaViewModel
import com.uniandes.vinilo.ui.artista.viewModel.ArtistaViewModel
import java.text.SimpleDateFormat
import java.util.*

class ArtistaFragment : Fragment(), OnClickListener {

    private lateinit var mArtistaViewModel: ArtistaViewModel
    private lateinit var mDetailArtistaViewModel: DetailArtistaViewModel

    private lateinit var mBinding: FragmentArtistaBinding

    private lateinit var mAdapter: ArtistaAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mArtistaViewModel = ViewModelProvider(this).get(ArtistaViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentArtistaBinding.inflate(inflater, container, false)
        setupViewModel()
        setupRecylcerView()
        return mBinding.root
    }

    private fun setupViewModel() {

        mArtistaViewModel.getMusicians().observe(viewLifecycleOwner,  { artistas->
            mAdapter.setArtistas(artistas)
        })

        mArtistaViewModel.getBands().observe(viewLifecycleOwner,  { artistas->
            mAdapter.setArtistas(artistas)
        })

        mArtistaViewModel.isShowProgress().observe(viewLifecycleOwner, { isShowProgress ->
            mBinding.progressBar.visibility = if (isShowProgress) View.VISIBLE else View.GONE
        })

        mDetailArtistaViewModel = ViewModelProvider(requireActivity()).get(DetailArtistaViewModel::class.java)

    }

    override fun onClick(artista: Artista) {
        launchDetailFragment(artista)
    }

    private fun launchDetailFragment(artista: Artista) {
        mDetailArtistaViewModel.setArtistaSelected(artista)

        val fragment = DetailArtistaFragment()
        val fragmentManager = getActivity()?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()

        fragmentTransaction?.add(R.id.fragmentoArtista, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun setupRecylcerView() {
        mAdapter = ArtistaAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(getActivity(), resources.getInteger(R.integer.main_columns))
        //getStores()

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

}
package com.uniandes.vinilo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.uniandes.vinilo.R
import com.uniandes.vinilo.common.dto.Album
import com.uniandes.vinilo.databinding.FragmentHomeBinding
import com.uniandes.vinilo.ui.home.adapter.AlbumAdapter
import com.uniandes.vinilo.ui.home.viewModel.DetailAlbumViewModel
import com.uniandes.vinilo.ui.home.viewModel.HomeViewModel

class HomeFragment : Fragment(), OnClickListener {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mDetailAlbumViewModel: DetailAlbumViewModel

    private lateinit var mBinding: FragmentHomeBinding

    private lateinit var mAdapter: AlbumAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        setupViewModel()
        setupRecylcerView()
        return mBinding.root
    }

    private fun setupViewModel() {

        mHomeViewModel.getAlbumes().observe(viewLifecycleOwner,  {albumes->
            mAdapter.setAlbumes(albumes)

        })
        mHomeViewModel.isShowProgress().observe(viewLifecycleOwner, {isShowProgress ->
            mBinding.progressBar.visibility = if (isShowProgress) View.VISIBLE else View.GONE
        })

        mDetailAlbumViewModel = ViewModelProvider(requireActivity()).get(DetailAlbumViewModel::class.java)

    }

    override fun onClick(album: Album) {
        launchDetailFragment(album)
    }

    private fun launchDetailFragment(album: Album) {
        mDetailAlbumViewModel.setAlbumSelected(album)

        val fragment = DetailAlbumFragment()
        val fragmentManager = getActivity()?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()

        fragmentTransaction?.add(R.id.fragmentoHome, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun setupRecylcerView() {
        mAdapter = AlbumAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(getActivity(), resources.getInteger(R.integer.main_columns))
        //getStores()

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }
}
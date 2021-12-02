package com.uniandes.vinilo.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.uniandes.vinilo.R
import com.uniandes.vinilo.databinding.AlbumRegisterFragmentBinding
import com.uniandes.vinilo.models.Album
import com.uniandes.vinilo.viewmodels.AlbumRegisterViewModel

class AlbumRegisterFragment : Fragment() {

    private lateinit var mBinding: AlbumRegisterFragmentBinding
    private lateinit var albumRegisterViewModel: AlbumRegisterViewModel
    private var mActivity: MainActivity? = null
    private lateinit var album: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumRegisterViewModel = ViewModelProvider(requireActivity())[AlbumRegisterViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {
        mBinding = AlbumRegisterFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.title_register_album)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {
                if (validateFields(mBinding.tilPhotoUrl, mBinding.tilRecord, mBinding.tilName, mBinding.tilGenre, mBinding.tilDescription, mBinding.tilReleaseDate)){
                    album = Album(null)
                    with(album){
                        name = mBinding.etName.text.toString().trim()
                        genre = mBinding.etGenre.text.toString().trim()
                        cover = mBinding.etPhotoUrl.text.toString().trim()
                        description = mBinding.etDescription.text.toString()
                        recordLabel = mBinding.etRecord.text.toString().trim()
                        releaseDate = mBinding.etReleaseDate.text.toString().trim()
                    }

                    albumRegisterViewModel.saveAlbum(album)
                    Snackbar.make(mBinding.root,
                        R.string.msg_save_album,
                        Snackbar.LENGTH_SHORT).show()
                    mActivity?.onBackPressed()

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateFields(vararg textFields: TextInputLayout): Boolean{
        var isValid = true

        for (textField in textFields){
            if (textField.editText?.text.toString().trim().isEmpty()){
                textField.error = getString(R.string.helper_required)
                isValid = false
            } else textField.error = null
        }

        if (!isValid) Snackbar.make(mBinding.root,
            R.string.edit_album_message_valid,
            Snackbar.LENGTH_SHORT).show()

        return isValid
    }

    private fun hideKeyboard(){
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null){
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.title_albums)

        setHasOptionsMenu(false)
        super.onDestroy()
    }

}
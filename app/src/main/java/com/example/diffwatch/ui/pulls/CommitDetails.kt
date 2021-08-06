package com.example.diffwatch.ui.pulls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.diffwatch.adapters.FilesAdapter
import com.example.diffwatch.adapters.PatchArrayAdapter
import com.example.diffwatch.data.models.compare.PatchMatrix
import com.example.diffwatch.data.models.compare.SplitViewObject
import com.example.diffwatch.data.models.files.required.CommitFile
import com.example.diffwatch.databinding.CommitDetailsFragmentBinding


class CommitDetails : Fragment() {

    companion object {
        fun newInstance() = CommitDetails()
    }

    private val commitDetailsViewModel: CommitDetailsViewModel by lazy {
        ViewModelProvider(this).get(CommitDetailsViewModel::class.java)
    }



    //private var _binding: CommitDetailsFragmentBinding? = null
    private lateinit var binding: CommitDetailsFragmentBinding

    private val sharedViewModel: PullDetailsViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CommitDetailsFragmentBinding.inflate(inflater)

        val root: View = binding.root






        //Binding files adapter to RecyclerView
        val filesAdapter = FilesAdapter()
        binding.fileDiffRecycler.adapter = filesAdapter





        //Binding files adapter to RecyclerView
        //val patchAdapter = PatchArrayAdapter()
        //binding.fileDiffRecycler.adapter = patchAdapter


        commitDetailsViewModel.commitFiles.observe(viewLifecycleOwner, {
            it.let {
                if(it.files != null){
                    reconstructSplitViewStrings(it.files)
                    filesAdapter.updateList(it.files)
                    //patchAdapter.data = it.patchMatrixList
                }

            }


        })

        commitDetailsViewModel.commitUrl = sharedViewModel.commitUrl

        commitDetailsViewModel.apply {
            println("commitUrl over here = $commitUrl")
            commitUrl?.let { sendCommitDetailsRequest(it) }
        }



        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }


    private fun reconstructSplitViewStrings(files: List<CommitFile>) {
        for(file in files){
            if(file.patch != null){
                //file.splitViewObject = commitDetailsViewModel.reconstructSplitViewStrings(file.patch)
                file.patchMatrixList = commitDetailsViewModel.reconstructSplitViewStrings(file.patch)
            }
        }

    }


}
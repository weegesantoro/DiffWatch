package com.example.diffwatch.ui.pulls

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.diffwatch.R
import com.example.diffwatch.adapters.CommitListListener
import com.example.diffwatch.adapters.CommitsAdapter
import com.example.diffwatch.data.models.files.required.CommitFiles
import com.example.diffwatch.databinding.PullDetailsFragmentBinding

class PullDetails : Fragment() {


    private var _binding: PullDetailsFragmentBinding? = null

    private val sharedViewModel: PullRequestsViewModel by activityViewModels()
    private val pullDetailsViewModel: PullDetailsViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PullDetailsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Binding adapter to RecyclerView
        val commitsAdapter = CommitsAdapter(CommitListListener {
            pullDetailsViewModel.commitUrl = it.url
            root.findNavController().navigate(R.id.action_pullDetails_to_commitDetails)
        })

        binding.commitsRecycler.adapter = commitsAdapter


        pullDetailsViewModel.pullsResponse.observe(viewLifecycleOwner, {
            commitsAdapter.data = it
        })

        pullDetailsViewModel.commitsUrl = sharedViewModel.commitsUrl

        pullDetailsViewModel.apply {
            println("commitsUrl over here = $commitsUrl")
            if(commitsUrl != null){
                sendCommitsRequest(commitsUrl!!)
            }
        }

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }



        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
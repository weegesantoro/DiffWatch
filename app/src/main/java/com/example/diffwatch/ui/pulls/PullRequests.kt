package com.example.diffwatch.ui.pulls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.diffwatch.R
import com.example.diffwatch.adapters.PullsAdapter
import com.example.diffwatch.adapters.PullsListListener
import com.example.diffwatch.databinding.FragmentPullRequestsBinding

class PullRequests : Fragment() {


    private var _binding: FragmentPullRequestsBinding? = null

    private val pullsViewModel: PullRequestsViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPullRequestsBinding.inflate(inflater, container, false)
        val root: View = binding.root



        // Binding adapter to RecyclerView
        val pullsAdapter = PullsAdapter(PullsListListener {
            pullsViewModel.commitsUrl = it.commitsUrl
            println("commitsUrl = ${pullsViewModel.commitsUrl}")
            root.findNavController().navigate(R.id.action_pullRequests_to_pullDetails)
        })

        binding.pullsRecycler.adapter = pullsAdapter


        pullsViewModel.pullsResponse.observe(viewLifecycleOwner, Observer {
            println("it.size = ${it.size}")
            pullsAdapter.data = it
        })

        // this will become dynamic
        pullsViewModel.sendPullsRequest("square","okhttp")

        slideInFromRight()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun slideInFromRight(){
        binding.apply {
            masterConstraint.alpha = 0f
            masterConstraint.visibility = View.VISIBLE
            masterConstraint.animate().apply {
                duration = 1
                translationXBy(1000f)
            }.withEndAction {
                masterConstraint.animate().apply {
                    duration = 1000
                    masterConstraint.alpha = 1f
                    translationXBy(-1000f)
                }
            }
        }
    }
}
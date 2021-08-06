package com.example.diffwatch.ui.onboarding

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.diffwatch.R
import com.example.diffwatch.databinding.LaunchScreenFragmentBinding
import kotlinx.coroutines.*

class LaunchScreen : Fragment() {

    companion object {
        fun newInstance() = LaunchScreen()
    }

    private lateinit var viewModel: LaunchScreenViewModel

    private var _binding: LaunchScreenFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // viewModel
        viewModel = ViewModelProvider(this).get(LaunchScreenViewModel::class.java)

        // Binding root view
        _binding = LaunchScreenFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.skipButton.setOnClickListener {
            findNavController().navigate(R.id.action_launchScreen_to_pullRequests)
        }

        if(!onBoarded()){
            onboardingAnimation()
        }
        return root
    }

    private fun onBoarded(): Boolean {
        return false
    }

    private fun onboardingAnimation() {
        binding.apply {

            // pre-set alphas and visibility and location for login constraint
            loginConstraint.alpha = 0f
            loginConstraint.animate().translationYBy(2000f)


            // animate right logo
            logoRight.alpha = 0f
            logoRight.visibility = View.VISIBLE
            logoRight.animate().apply {
                duration = 1
                translationXBy(1000f)
            }.withEndAction {
                logoRight.animate().apply {
                    duration = 2000
                    logoRight.alpha = 1f
                    translationXBy(-1000f)
                }
            }


            // animate left logo
            logoLeft.alpha = 0f
            logoLeft.visibility = View.VISIBLE
            logoLeft.animate().apply {
                duration = 1
                translationXBy(-1000f)
            }.withEndAction {
                logoLeft.animate().apply {
                    duration = 2500
                    logoLeft.alpha = 1f
                    translationXBy(1000f)
                }
            }


            // animate left title word
            titleLeft.alpha = 0f
            titleLeft.visibility = View.VISIBLE
            titleLeft.animate().apply {
                duration = 1
                translationYBy(1000f)
            }.withEndAction {
                titleLeft.animate().apply {
                    duration = 2000
                    titleLeft.alpha = 1f
                    translationYBy(-1000f)
                }
            }

            // animate right title word
            titleRight.alpha = 0f
            titleRight.visibility = View.VISIBLE
            titleRight.animate().apply {
                duration = 1
                translationYBy(1000f)
            }.withEndAction {
                titleRight.animate().apply {
                    duration = 2500
                    titleRight.alpha = 1f
                    translationYBy(-1000f)
                }.withEndAction {

                    // animate logo up and away
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)
                        logoConstraint.animate().apply {
                            duration = 1000
                            translationYBy(-1000f)
                            alpha(0f)
                        }.withEndAction {

                            // animate login constraint up from below
                            loginConstraint.animate().apply {
                                duration = 1500
                                translationYBy(-2000f)
                                alpha(1f)
                            }
                        }
                    }

                }
            }


        }
    }

}
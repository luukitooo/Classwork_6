package com.lukabaia.classwork_6.fragments

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lukabaia.classwork_6.BaseFragment
import com.lukabaia.classwork_6.databinding.FragmentHomeBinding
import com.lukabaia.classwork_6.utils.SessionHandler
import com.lukabaia.classwork_6.utils.SessionKeys
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val args: HomeFragmentArgs by navArgs()

    override fun viewCreated() {

        init()

        onClickListeners()

    }

    private fun init() {
        binding.tvEmail.text = args.email
    }

    private fun onClickListeners() = with(binding) {
        btnLogOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                SessionHandler(requireActivity().application).also {
                    it.save(SessionKeys.SESSION_KEY, "")
                    it.save(SessionKeys.EMAIL, "")
                }
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLogInFragment())
            }
        }
    }

}
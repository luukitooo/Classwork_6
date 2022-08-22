package com.lukabaia.classwork_6.fragments

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lukabaia.classwork_6.BaseFragment
import com.lukabaia.classwork_6.databinding.FragmentLogInBinding
import com.lukabaia.classwork_6.extensions.areLinesEmpty
import com.lukabaia.classwork_6.model.UserInfo
import com.lukabaia.classwork_6.utils.FragmentResult
import com.lukabaia.classwork_6.utils.SessionHandler
import com.lukabaia.classwork_6.utils.SessionKeys
import com.lukabaia.classwork_6.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun viewCreated() {

        checkSession()

        fragmentResultListener()

        onClickListeners()

        observers()

    }

    private fun checkSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            val handler = SessionHandler(requireActivity().application)
            handler.read(SessionKeys.SESSION_KEY)?.let {
                if (it.isNotEmpty())
                    findNavController().navigate(LogInFragmentDirections.toHomeFragment(handler.read(SessionKeys.EMAIL)!!))
            }
        }
    }

    private fun fragmentResultListener() {
        setFragmentResultListener(FragmentResult.REQUEST_KEY) { _, bundle ->
            val email = bundle.getString(FragmentResult.EMAIL)
            val password = bundle.getString(FragmentResult.PASSWORD)
            binding.apply {
                etEmail.setText(email)
                etPassword.setText(password)
            }
        }
    }

    private fun onClickListeners() = with(binding) {
        btnRegister.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.toRegisterFragment())
        }
        btnLogin.setOnClickListener {
            if (!binding.root.areLinesEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getLoginResult(getUserInfo())
                }
            }
        }
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginFlow.collect {
                it?.let { result ->
                    if (binding.cbRemember.isChecked) {
                        SessionHandler(requireActivity().application).also {
                            it.save(SessionKeys.SESSION_KEY, result.token!!)
                            it.save(SessionKeys.EMAIL, binding.etEmail.text.toString())
                        }
                    }
                    findNavController().navigate(
                        LogInFragmentDirections.toHomeFragment(
                            email = binding.etEmail.text.toString()
                        )
                    )
                }
            }
        }
    }

    private fun getUserInfo() = UserInfo(
        email = binding.etEmail.text.toString(),
        password = binding.etPassword.text.toString()
    )

}
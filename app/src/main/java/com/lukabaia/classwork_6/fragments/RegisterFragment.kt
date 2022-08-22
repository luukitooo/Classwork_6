package com.lukabaia.classwork_6.fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lukabaia.classwork_6.BaseFragment
import com.lukabaia.classwork_6.databinding.FragmentRegisterBinding
import com.lukabaia.classwork_6.extensions.areLinesEmpty
import com.lukabaia.classwork_6.model.UserInfo
import com.lukabaia.classwork_6.utils.FragmentResult
import com.lukabaia.classwork_6.viewmodel.RegisterViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun viewCreated() {

        onClickListeners()

        observers()

    }

    private fun onClickListeners() = with(binding) {
        btnRegister.setOnClickListener {
            if (!binding.root.areLinesEmpty() && confirmRegistration()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val userInfo = getUserInfo()
                    viewModel.getRegisterResult(userInfo)
                    setFragmentResult(
                        requestKey = FragmentResult.REQUEST_KEY,
                        result = bundleOf(
                            FragmentResult.EMAIL to userInfo.email,
                            FragmentResult.PASSWORD to userInfo.password
                        )
                    )
                }
            }
        }
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registerFlow.collect {
                it?.let {
                    findNavController().navigate(RegisterFragmentDirections.toLogInFragment())
                }
            }
        }
    }

    private fun getUserInfo() = UserInfo(
        email = binding.etEmail.text.toString(),
        password = binding.etPassword.text.toString()
    )

    private fun confirmRegistration(): Boolean {
        val password = binding.etPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()
        if (password == repeatPassword)
            return true
        return false
    }

}
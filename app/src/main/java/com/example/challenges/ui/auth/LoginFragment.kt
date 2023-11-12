package com.example.challenges.ui.auth

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.challenges.Hasil
import com.example.challenges.MainActivity
import com.example.challenges.databinding.FragmentLoginBinding
import com.example.challenges.ui.ViewModelFactories


class LoginFragment {
    class LoginFragment : Fragment() {
        private var _binding: FragmentLoginBinding? = null
        private val binding get() = _binding!!
        private val viewModel by viewModels<LoginViewModel> {ViewModelFactories.getInstance(requireActivity()
            )
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View {
            // Inflate the layout for this fragment
            _binding = FragmentLoginBinding.inflate(layoutInflater)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setUpAction()
        }

        private fun setUpAction() {
            binding.btnLogin.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(action)
            }

            binding.btnLogin.setOnClickListener {
                val email = binding.etlogin.text.toString()
                val password = binding.etpassword.text.toString()

//                observeLogin(email, password)
            }
        }

//        private fun observeLogin(email: String, password: String) {
//            viewModel.login(email, password).observe(viewLifecycleOwner) {
//                when (it) {
//                    is Hasil.Loading -> {
//                        binding.progressIndicator.show()
//                        binding.btnLogin.isEnabled = false
//                    }
//
//                    is Hasil.Success -> {
//                        binding.progressIndicator.hide()
//                        saveUser(it.data)
//                    }
//
//                    is Hasil.Error -> {
//                        binding.progressIndicator.hide()
//                        binding.btnLogin.isEnabled = true
//                        toast(it.error)
//                    }
//
//                    else -> false
//                }
//            }
//        }
//
//        private fun saveUser(token: String) {
//            viewModel.saveSession(token)
//            val intent = Intent(requireContext(), MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//        }
//
//        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(100)
//
//        override fun onDestroy() {
//            super.onDestroy()
//            _binding = null
//        }

    }
}
package com.example.challenges.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challenges.Hasil
import com.example.challenges.MainActivity
import com.example.challenges.R
import com.example.challenges.databinding.FragmentRegisterBinding
import com.example.challenges.util.toast
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RegisterViewModel> { ViewModelFactory.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerValidation()
        playAnimation()

        binding.btnRegister.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeRegister(name: String, email: String, password : String) {
        viewModel.register(name, email, password).observe(viewLifecycleOwner) {
            when (it) {
                is Hasil.Loading -> {
                    binding.progressIndicator.show()
                    binding.btnRegister.isEnabled = false
                }
                is Hasil.Success -> {
                    binding.progressIndicator.hide()
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("Register")
                        setMessage(getString(R.string.register_succeed))
                        setPositiveButton(getString(R.string.continue_login)) { _, _ ->
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        create()
                        show()
                    }
                }
                is Hasil.Error -> {
                    binding.progressIndicator.hide()
                    binding.btnRegister.isEnabled = true
                    toast(it.error)
                }
            }
        }
    }

    private fun registerValidation() {

        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            observeRegister(name, email, password)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
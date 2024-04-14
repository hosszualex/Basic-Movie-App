package com.example.basicmovieapp.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.basicmovieapp.databinding.FragmentErrorDialogBinding

class ErrorDialogFragment : DialogFragment() {
    private var errorMessage: String? = null
    private var _binding: FragmentErrorDialogBinding? = null
    private val binding: FragmentErrorDialogBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { errorMessage = it.getString(ARG_ERROR_MESSAGE) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentErrorDialogBinding.inflate(inflater, container, false)
        binding.textViewErrorMessage.text = errorMessage
        binding.buttonDismiss.setOnClickListener { dismiss() }
        return binding.root
    }

    companion object {
        private const val ARG_ERROR_MESSAGE = "error_message"

        fun newInstance(message: String): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            val args = Bundle()
            args.putString(ARG_ERROR_MESSAGE, message)
            fragment.arguments = args
            return fragment
        }
    }
}

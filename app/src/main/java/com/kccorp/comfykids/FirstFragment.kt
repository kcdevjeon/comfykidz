package com.kccorp.comfykids

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kccorp.comfykids.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    companion object {
        const val TAG = "FirstFragment"
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentFirstBinding
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(MainViewModel::class.java)
            binding.vm = viewModel
            binding.lifecycleOwner = this
        }
        TtsManager.initialize(requireContext())
        return binding.root
    }

    override fun onDestroyView() {
        TtsManager.destroy()
        super.onDestroyView()
    }
}
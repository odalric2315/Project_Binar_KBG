package com.project_binar.kbg.ui.lending_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project_binar.kbg.databinding.FragmentLanding3Binding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LandingFragment3 : Fragment() {
    private  lateinit var binding: FragmentLanding3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanding3Binding.inflate(inflater,container,false)
        return binding.root
    }

}
package com.sylko.twitchapi.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.sylko.twitchapi.R
import com.sylko.twitchapi.databinding.FragmentReviewBinding

class ReviewFragment : Fragment(R.layout.fragment_review) {

    private lateinit var binding: FragmentReviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewBinding.bind(view)

        activity?.setActionBar(binding.reviewToolbar)

        binding.btnSend.setOnClickListener {
            activity?.onBackPressed() }
    }

}
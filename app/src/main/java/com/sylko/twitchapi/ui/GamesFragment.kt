package com.sylko.twitchapi.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sylko.twitchapi.R
import com.sylko.twitchapi.adapters.GamesAdapter
import com.sylko.twitchapi.databinding.FragmentGamesBinding
import com.sylko.twitchapi.entities.GameEntity

class GamesFragment: Fragment(R.layout.fragment_games){

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentGamesBinding
    private var adapter: GamesAdapter? = null
    private lateinit var viewModel: GamesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGamesBinding.bind(view)

        recyclerView = binding.rvGames

        observeLiveData()

    }

    private fun observeLiveData(){
       viewModel = ViewModelProvider(this).get(GamesViewModel::class.java)

        viewModel.games.observe(viewLifecycleOwner, {
            initAdapter(it)
        })

    }

    private fun initAdapter(data: List<GameEntity>) {
        adapter = GamesAdapter(data)
        recyclerView.adapter = adapter
    }

}
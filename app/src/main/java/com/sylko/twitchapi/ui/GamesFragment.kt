package com.sylko.twitchapi.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sylko.twitchapi.R
import com.sylko.twitchapi.adapters.GamesAdapter
import com.sylko.twitchapi.databinding.FragmentGamesBinding
import io.reactivex.android.schedulers.AndroidSchedulers

class GamesFragment: Fragment(R.layout.fragment_games){

    private val viewModel: GamesViewModel by lazy {
        ViewModelProvider(this).get(GamesViewModel::class.java)
    }

    private val adapter: GamesAdapter by lazy {
        GamesAdapter()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentGamesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGamesBinding.bind(view)

        recyclerView = binding.rvGames
        initRecycler()

        activity?.setActionBar(binding.gamesToolbar)
        activity?.onCreateOptionsMenu(binding.gamesToolbar.menu)
    }

    private var recyclerState: Parcelable? = null

    private fun initRecycler() {
        val linearLayoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter
        subscribeToList()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", recyclerView.layoutManager?.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            recyclerState = savedInstanceState.getParcelable("lmState")
        }
    }

    private fun subscribeToList() {
        val disposable = viewModel.gameList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    adapter.submitList(list)
                    if (recyclerState != null) {
                        recyclerView.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null
                    }
                },
                { e ->
                    Log.e("NGVL", "Error", e)
                }
            )
    }

}
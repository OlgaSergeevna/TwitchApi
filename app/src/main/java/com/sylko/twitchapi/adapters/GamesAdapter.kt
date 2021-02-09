package com.sylko.twitchapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sylko.twitchapi.R
import com.sylko.twitchapi.databinding.ItemGameBinding
import com.sylko.twitchapi.entities.GameEntity


class GamesAdapter : PagedListAdapter<GameEntity, GamesAdapter.ViewHolder>(gameDiff) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_game, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val games = getItem(position)
                with(holder) {
            name.text = games?.name
            Picasso.get().load(games?.image).into(image)
            channel.text = games?.channels.toString()
            viewer.text = games?.viewers.toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGameBinding.bind(view)

        val name = binding.tvName
        val image = binding.ivPicture
        val channel = binding.etChannels
        val viewer = binding.etViewers
    }

    companion object {
        val gameDiff = object : DiffUtil.ItemCallback<GameEntity>() {
            override fun areItemsTheSame(old: GameEntity, new: GameEntity): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: GameEntity, new: GameEntity): Boolean {
                return old == new
            }
        }
    }
}


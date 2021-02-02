package com.sylko.twitchapi.adapters
import com.sylko.twitchapi.entities.GameEntity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sylko.twitchapi.R
import com.sylko.twitchapi.databinding.ItemGameBinding

class GamesAdapter(
    private val gamesList: List<GameEntity>
) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val games = gamesList[position]
        with(holder) {
            name.text = games.name
            Picasso.get().load(games.image).into(image)
            channel.text = games.channels.toString()
            viewer.text = games.viewers.toString()
        }
    }

    override fun getItemCount() = gamesList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGameBinding.bind(view)

        val name = binding.tvName
        val image = binding.ivPicture
        val channel = binding.etChannels
        val viewer = binding.etViewers

    }

}

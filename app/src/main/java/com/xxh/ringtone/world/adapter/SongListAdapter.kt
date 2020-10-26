package com.xxh.ringtone.world.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxh.ringtone.world.R
import com.xxh.ringtone.world.data.model.Song

class SongListAdapter(private var data: MutableList<Song>?): RecyclerView.Adapter<SongListAdapter.SongHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ringbox, parent, false)
        return SongHolder(v)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        val song: Song = data!![position]

        holder.titleView!!.text = song.title
    }

    override fun getItemCount(): Int {
        if (data != null) {
            return data!!.size
        }
        return 0
    }

    internal fun setRingtones(ringtones: List<Song>) {
        this.data = ringtones as MutableList<Song>
        notifyDataSetChanged()
    }

    class SongHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var titleView: TextView = itemView.findViewById(R.id.ringtone_share_card)

    }
}



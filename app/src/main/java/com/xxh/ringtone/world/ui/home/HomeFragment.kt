package com.xxh.ringtone.world.ui.home

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxh.ringtone.world.R
import com.xxh.ringtone.world.adapter.SongListAdapter
import com.xxh.ringtone.world.data.model.Song
import com.xxh.ringtone.world.databinding.FragmentHomeBinding
import com.xxh.ringtone.world.utils.ReadJsonFile

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(layoutInflater)

        recyclerView = binding.root.findViewById(R.id.song_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setItemViewCacheSize(2000)
        }

        GetSongs().execute(
            ReadJsonFile.Companion.MyTaskParams(
                this.requireActivity(),
                ReadJsonFile.ringtoneFileName
            )
        )
        return binding.root
    }

    private inner class GetSongs :
        AsyncTask<ReadJsonFile.Companion.MyTaskParams, String, MutableList<Song>>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: ReadJsonFile.Companion.MyTaskParams?): MutableList<Song> {
            val context = params[0]!!.context
            val fileName = params[0]!!.fileName
            return ReadJsonFile.getRingtoneList(context)
        }

        override fun onPostExecute(result: MutableList<Song>?) {
            super.onPostExecute(result)

            val len = result!!.size
//            Log.i("TAG", "$len")

            recyclerView.adapter = SongListAdapter(result)
            recyclerView.setHasFixedSize(true)
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
        }

    }
}
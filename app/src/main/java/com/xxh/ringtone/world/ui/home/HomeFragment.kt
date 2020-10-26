package com.xxh.ringtone.world.ui.home

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxh.ringtone.world.R
import com.xxh.ringtone.world.adapter.SongListAdapter
import com.xxh.ringtone.world.data.model.Song
import com.xxh.ringtone.world.data.repository.RingtoneRepository
import com.xxh.ringtone.world.data.repository.RingtoneRoomDatabase
import com.xxh.ringtone.world.data.viewmodel.RingtoneViewModel
import com.xxh.ringtone.world.databinding.FragmentHomeBinding
import com.xxh.ringtone.world.utils.ReadJsonFile
import com.xxh.ringtone.world.utils.Utils


class HomeFragment() : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var ringtoneViewModel: RingtoneViewModel

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
            adapter = SongListAdapter(null)
        }

        ringtoneViewModel =
            ViewModelProvider(this.requireActivity()).get(RingtoneViewModel::class.java)
        setDatabase()

        return binding.root
    }

    fun setDatabase() {
        if (!Utils.checkPermission(this.requireActivity())) {
            return
        }

        ringtoneViewModel =
            ViewModelProvider(this.requireActivity()).get(RingtoneViewModel::class.java)
        val adapter = recyclerView.adapter as SongListAdapter
        ringtoneViewModel.getAllRingtones().observe(this.requireActivity(), Observer { ringtones ->
            ringtones?.let {
                adapter.setRingtones(it)
            }
        })

    }

}
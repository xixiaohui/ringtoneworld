package com.xxh.ringtone.world.ui.download

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider
import com.xxh.ringtone.world.R
import com.xxh.ringtone.world.data.model.Song
import com.xxh.ringtone.world.databinding.FragmentSongBinding
import com.xxh.ringtone.world.utils.MediaHolder
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "song"

/**
 * A simple [Fragment] subclass.
 * Use the [SongFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SongFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var song: Song? = null

    private lateinit var binding: FragmentSongBinding

//    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaHolder: MediaHolder

    private var slider: Slider? = null
    private var startTime: Double = 0.0
    private var finalTime: Double = 0.0

    private lateinit var startTimeTextView: TextView
    private lateinit var endTimeTextView: TextView

    private val mHandler: Handler = Handler()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            song = it.getParcelable(ARG_PARAM1)
        }
        binding = FragmentSongBinding.inflate(layoutInflater)

        if (song != null) {
            binding.songName.text = song!!.title
            slider = binding.slider
            slider!!.apply {
                valueFrom = 0.0.toFloat()
            }

            startTimeTextView = binding.songTimeStart
            endTimeTextView = binding.songTimeEnd

            mediaHolder = MediaHolder.get()
            binding.songImagePlay.setOnClickListener {

                this.onClickPlayButton()
            }
        }


    }

    private fun onClickPlayButton() {
        mediaHolder.start(song!!.url, object : MediaHolder.DoAction {
            override fun doAfter() {
                updateSlider()


                binding.songLoadingBg.visibility = View.INVISIBLE
                binding.songImagePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                finalTime = mediaHolder.duration().toDouble()
                startTime = mediaHolder.currentPosition().toDouble()
                endTimeTextView.apply {
                    text = String.format(
                        "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) - TimeUnit.MILLISECONDS.toMinutes(
                            finalTime.toLong()
                        )
                    )
                }

                initStartTimeTextView()

                slider!!.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
                    override fun onStartTrackingTouch(slider: Slider) {
//                        Log.i("TAG",slider.value.toString())
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onStopTrackingTouch(slider: Slider) {
//                        Log.i("TAG",slider.value.toString())
                        if (mediaHolder != null){
                            val process = slider.value.toLong()
                            mediaHolder.seekAndPlay(process)

                        }
                    }
                })
//

                slider!!.apply {
                    valueTo = (mediaHolder.duration() / 1000.0).toFloat()
                    valueFrom = 0.0.toFloat()
                }

            }

            override fun doBefore() {

                binding.songLoadingBg.visibility = View.VISIBLE

            }

        })
    }

    private fun initStartTimeTextView(){
        startTime = mediaHolder.currentPosition().toDouble()
        if (startTime<0){
            startTime = 0.0
        }

        startTimeTextView.apply {
            text = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) - TimeUnit.MILLISECONDS.toMinutes(
                    startTime.toLong()
                )
            )
        }
    }
    private fun updateSlider(){
        this.requireActivity().runOnUiThread(object : Runnable{
            override fun run() {
                if (mediaHolder != null){
                    val mCurrentPosition = (mediaHolder.currentPosition() / 1000).toFloat()

                    if(mCurrentPosition>=0.0){
                        slider!!.value = mCurrentPosition
                        initStartTimeTextView()
                    }
                }
                mHandler.postDelayed(this,1000)
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaHolder!=null){
            mediaHolder.pause()

        }
    }

    override fun onPause() {
        super.onPause()
        if (mediaHolder!=null){
            mediaHolder.pause()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Song, param2: String) =
            SongFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}
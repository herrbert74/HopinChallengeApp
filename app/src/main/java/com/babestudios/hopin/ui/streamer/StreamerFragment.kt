package com.babestudios.hopin.ui.streamer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.babestudios.hopin.HopinViewModel
import com.babestudios.hopin.databinding.FragmentStreamerBinding
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.launch


class StreamerFragment : Fragment() {

    private lateinit var exoPlayer: ExoPlayer

    private val hopinViewModel : HopinViewModel by activityViewModels()

    private var _binding: FragmentStreamerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStreamerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val url = hopinViewModel.getStreamUrl()
            url?.let {
                initializePlayer(it)
            }
        }
    }

    private fun initializePlayer(url: String) {

        val loadControl = DefaultLoadControl()
        exoPlayer = SimpleExoPlayer.Builder(requireContext())
                .setLoadControl(loadControl)
                .build()

        binding.playerView.player = exoPlayer

        val mediaItem: MediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }
}
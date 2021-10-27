package com.android.rickandmorty.fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.android.rickandmorty.databinding.FragmentEpisodeDetailsBinding

//просмотр деталей фрагмента
class EpisodeDetailsFragment : Fragment() {
    private val args by navArgs<EpisodeDetailsFragmentArgs>()
    private lateinit var episodeDetailsBinding: FragmentEpisodeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        episodeDetailsBinding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        val view = episodeDetailsBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodeDetailsBinding.episodeDetailName.setText("Name: " + args.episode.name)
        episodeDetailsBinding.episodeDetailAirDate.setText("AirDate: " + args.episode.air_date)
        episodeDetailsBinding.episodeDetailCode.setText("Code: " + args.episode.episode)

    }
}
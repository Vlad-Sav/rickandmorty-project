package com.android.rickandmorty.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.rickandmorty.R
import com.android.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.android.rickandmorty.databinding.FragmentLocationDetailsBinding

//просмотр деталей локации
class LocationDetailsFragment : Fragment() {
    private val args by navArgs<LocationDetailsFragmentArgs>()
    private lateinit var locationDetailsBinding: FragmentLocationDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationDetailsBinding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        val view = locationDetailsBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationDetailsBinding.locationDetailName.setText("Name: " + args.location.name)
        locationDetailsBinding.locationDetailType.setText("Type: " + args.location.type)
        locationDetailsBinding.locationDetailDimension.setText("Dimension: " + args.location.dimension)

    }


}
package com.android.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.rickandmorty.fragments.CharacterDetailsFragmentArgs
import com.android.rickandmorty.R
import com.android.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.squareup.picasso.Picasso
//детальный просмотр персонажей
class CharacterDetailsFragment : Fragment() {
    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private lateinit var characterDetailsBinding: FragmentCharacterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        characterDetailsBinding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        val view = characterDetailsBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get()
            .load(args.character.image)
            .resize(196, 196)
            .centerCrop()
            .into(characterDetailsBinding.characterDetailImage)
        characterDetailsBinding.characterDetailName.setText("Name: " + args.character.name)
        characterDetailsBinding.characterDetailGender.setText("Gender: " + args.character.gender)
        characterDetailsBinding.characterDetailSpecies.setText("Species: " + args.character.species)
        characterDetailsBinding.characterDetailStatus.setText("Status: " + args.character.status)
    }


}
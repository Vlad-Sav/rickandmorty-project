package com.android.rickandmorty.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.android.rickandmorty.R

import com.android.rickandmorty.databinding.FragmentFilterCharacterBinding

//фрагмент выбора параметров фильтрации персонажей
class FilterFragment: DialogFragment() {
    private var _fragmentFilterBinding: FragmentFilterCharacterBinding? = null
    private val fragmentFilterBinding get() = _fragmentFilterBinding!!
    //колбэк для передачи параметров фильтрации во фрагмент персонажей
    interface Callbacks {
        fun onFilterSubmitted(gender: String, status: String);
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFilterBinding = FragmentFilterCharacterBinding.inflate(inflater, container, false)

        val genders = resources.getStringArray(R.array.gender_filter)
        val arrayAdapterGenders = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        fragmentFilterBinding.autoCompleteTextViewGender.setAdapter(arrayAdapterGenders)

        val statuses = resources.getStringArray(R.array.status_filter)
        val arrayAdapterStatus = ArrayAdapter(requireContext(), R.layout.dropdown_item, statuses)
        fragmentFilterBinding.autoCompleteTextViewStatus.setAdapter(arrayAdapterStatus)

        fragmentFilterBinding.cancelButton.setOnClickListener{
            dismiss()
        }

        fragmentFilterBinding.okayButton.setOnClickListener {
            val gender = fragmentFilterBinding.autoCompleteTextViewGender.text.toString()
            val status = fragmentFilterBinding.autoCompleteTextViewStatus.text.toString()
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onFilterSubmitted(gender, status)
            }
            dismiss()
        }

        return fragmentFilterBinding.root
    }


}
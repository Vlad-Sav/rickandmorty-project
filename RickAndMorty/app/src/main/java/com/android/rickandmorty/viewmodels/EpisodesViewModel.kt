package com.android.rickandmorty.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.rickandmorty.api.ApiRepository
import com.android.rickandmorty.model.Characters
import com.android.rickandmorty.model.Episode

class EpisodesViewModel: ViewModel() {
    //номера страниц
    var pageNumber: Int
    val MAX_PAGE_NUMBER = 3
    //репозиторий api
    val apiRepository: ApiRepository?
    //список данных для вывода в список
    var episodesLiveData: LiveData<List<Episode>>

    init {
        apiRepository =  ApiRepository.getInstance()
        pageNumber = 1
        episodesLiveData = apiRepository!!.getEpisodes(pageNumber)
    }
    //выполнение запросов и обновление данных списка
    fun updateEpisodesList() {
        episodesLiveData = apiRepository!!.getEpisodes(pageNumber)
    }

    fun searchEpisodes(name: String){
        episodesLiveData = apiRepository!!.searchEpisodes(name)
    }
}
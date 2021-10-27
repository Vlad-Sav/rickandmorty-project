package com.android.rickandmorty.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.rickandmorty.api.responses.CharacterResponse
import com.android.rickandmorty.api.responses.EpisodeResponse
import com.android.rickandmorty.api.responses.LocationResponse
import com.android.rickandmorty.model.Characters
import com.android.rickandmorty.model.Episode
import com.android.rickandmorty.model.Location
import com.android.rickandmorty.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "Characters"
class ApiRepository {
    //объекты livedata, которые будут возвращены в viewmodels соответствующих объектов для подиски на них фрагментами
    val charactersResponseLiveData: MutableLiveData<List<Characters>> = MutableLiveData()
    val locationsResponseLiveData: MutableLiveData<List<Location>> = MutableLiveData()
    val episodesResponseLiveData: MutableLiveData<List<Episode>> = MutableLiveData()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RickAndMortyApi by lazy {
        retrofit.create(RickAndMortyApi::class.java)
    }
    //реазилация методов запросов
    fun getCharacters(number: Int): LiveData<List<Characters>>{

        val charactersRequest: Call<CharacterResponse> = api.getCharacters(number)

        charactersRequest.enqueue(object : Callback<CharacterResponse> {
            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {

                val characterResponse: CharacterResponse? = response.body()
                var characters: List<Characters> = characterResponse?.characters
                    ?: mutableListOf()
                charactersResponseLiveData.value = characters
            }
        })
        return charactersResponseLiveData;
    }

    fun getLocations(number: Int): LiveData<List<Location>>{

        val locationsRequest: Call<LocationResponse> = api.getLocations(number)

        locationsRequest.enqueue(object : Callback<LocationResponse> {
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {

                val locationResponse: LocationResponse? = response.body()
                var locations: List<Location> = locationResponse?.locations
                    ?: mutableListOf()
                locationsResponseLiveData.value = locations
            }
        })
        return locationsResponseLiveData;
    }

    fun getEpisodes(number: Int): LiveData<List<Episode>>{

        val episodesRequest: Call<EpisodeResponse> = api.getEpisodes(number)

        episodesRequest.enqueue(object : Callback<EpisodeResponse> {
            override fun onFailure(call: Call<EpisodeResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<EpisodeResponse>,
                response: Response<EpisodeResponse>
            ) {
                val episodeResponse: EpisodeResponse? = response.body()
                var episodes: List<Episode> = episodeResponse?.episodes
                    ?: mutableListOf()
                episodesResponseLiveData.value = episodes

            }
        })
        return episodesResponseLiveData;
    }

    fun searchCharacters(name: String): LiveData<List<Characters>>{

        val charactersRequest: Call<CharacterResponse> = api.searchCharacters(name)

        charactersRequest.enqueue(object : Callback<CharacterResponse> {
            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {

                val characterResponse: CharacterResponse? = response.body()
                var characters: List<Characters> = characterResponse?.characters
                    ?: mutableListOf()
                charactersResponseLiveData.value = characters
            }
        })
        return charactersResponseLiveData;
    }

    fun searchLocations(name: String): LiveData<List<Location>>{

        val locationsRequest: Call<LocationResponse> = api.searchLocations(name)

        locationsRequest.enqueue(object : Callback<LocationResponse> {
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                val locationResponse: LocationResponse? = response.body()
                var locations: List<Location> = locationResponse?.locations
                    ?: mutableListOf()
                locationsResponseLiveData.value = locations
            }
        })
        return locationsResponseLiveData;
    }

    fun searchEpisodes(name: String): LiveData<List<Episode>>{

        val episodesRequest: Call<EpisodeResponse> = api.searchEpisodes(name)

        episodesRequest.enqueue(object : Callback<EpisodeResponse> {
            override fun onFailure(call: Call<EpisodeResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<EpisodeResponse>,
                response: Response<EpisodeResponse>
            ) {
                val episodeResponse: EpisodeResponse? = response.body()
                var episodes: List<Episode> = episodeResponse?.episodes
                    ?: mutableListOf()
                episodesResponseLiveData.value = episodes
            }
        })
        return episodesResponseLiveData;
    }

    fun findCharacters(status: String, gender: String): LiveData<List<Characters>>{
        val charactersRequest: Call<CharacterResponse> = api.filterCharacters(status, gender)
        charactersRequest.enqueue(object : Callback<CharacterResponse> {
            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                val characterResponse: CharacterResponse? = response.body()
                var characters: List<Characters> = characterResponse?.characters
                    ?: mutableListOf()
                charactersResponseLiveData.value = characters

            }
        })
        return charactersResponseLiveData;
    }
    //фабричный метод создания репозитория
    companion object {
        private var INSTANCE: ApiRepository? = null

        fun getInstance(): ApiRepository? {
            if (INSTANCE == null) {
                INSTANCE = ApiRepository()
            }
            return INSTANCE
        }
    }
}
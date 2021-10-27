package com.android.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rickandmorty.R
import com.android.rickandmorty.model.Characters
import com.android.rickandmorty.model.Episode
import com.android.rickandmorty.viewmodels.CharactersViewModel
import com.android.rickandmorty.viewmodels.EpisodesViewModel

//фрагмент вкладки эпизодов
class EpisodesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodesViewModel: EpisodesViewModel
    private lateinit var buttonNext: Button
    private lateinit var buttonPrev: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episodesViewModel = ViewModelProviders.of(this).get(EpisodesViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_episodes, container, false)
        recyclerView = view.findViewById(R.id.episodeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        buttonNext = view.findViewById(R.id.buttonNextEpisode)
        buttonPrev = view.findViewById(R.id.buttonPrevEpisode)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //подписка на обновление данных персонажей
        episodesViewModel.episodesLiveData.observe(
            viewLifecycleOwner,
            Observer { episodes ->
                recyclerView.adapter =  EpisodesFragment.EpisodesAdapter(episodes)
            })
        //смена страниц
        buttonNext.setOnClickListener{
            episodesViewModel.pageNumber = episodesViewModel.pageNumber + 1
            episodesViewModel.updateEpisodesList()
            if(episodesViewModel.pageNumber > 1){
                buttonPrev.isEnabled = true
            }
            if(episodesViewModel.pageNumber == episodesViewModel.MAX_PAGE_NUMBER){
                buttonNext.isEnabled = false
            }
        }
        buttonPrev.setOnClickListener{
            episodesViewModel.pageNumber = episodesViewModel.pageNumber - 1

            episodesViewModel.updateEpisodesList()

            if(episodesViewModel.pageNumber == 1){
                buttonPrev.isEnabled = false
            }
            if(episodesViewModel.pageNumber < episodesViewModel.MAX_PAGE_NUMBER){
                buttonNext.isEnabled = true
            }
        }
        if(episodesViewModel.pageNumber == 1){
            buttonPrev.isEnabled = false
        }
        if(episodesViewModel.pageNumber == episodesViewModel.MAX_PAGE_NUMBER){
            buttonNext.isEnabled = true
        }
    }
    //создание пунктов меню и инициализация поиска
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val filterItem: MenuItem = menu.findItem(R.id.menu_item_filter)
        filterItem.isVisible = false
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    episodesViewModel.searchEpisodes(queryText)
                    return true
                }
                override fun onQueryTextChange(queryText: String): Boolean {
                    return false
                }
            })
        }

    }
    //обработка результатов пунктов меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_item_clear){
            episodesViewModel.updateEpisodesList()
        }
        return super.onOptionsItemSelected(item)
    }
    //возвращение данных для фильтрации из диалогового окна + фильтрация
    private class EpisodeHolder(view: View): RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewCode: TextView
        val cardView: CardView
        init {
            cardView = view.findViewById(R.id.episodeCard)
            textViewName = view.findViewById(R.id.episodeName)
            textViewCode = view.findViewById(R.id.episodeCode)
        }
        fun bindName(episode: Episode){
            textViewName.setText(episode.name)
            textViewCode.setText(episode.episode)
            cardView.setOnClickListener {
                val action = EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeDetailsFragment(episode)
                cardView.findNavController().navigate(action)
            }
        }
    }
    //инициализация RecyclerView
    private class EpisodesAdapter(private val episodeItems: List<Episode>) : RecyclerView.Adapter<EpisodesFragment.EpisodeHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ):EpisodesFragment.EpisodeHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.episodes_list_item, parent, false)
            return EpisodesFragment.EpisodeHolder(view)
        }

        override fun getItemCount(): Int = episodeItems.size

        override fun onBindViewHolder(holder: EpisodesFragment.EpisodeHolder, position: Int) {
            val episodeItem = episodeItems[position]
            holder.bindName(episodeItem)
        }
    }
}
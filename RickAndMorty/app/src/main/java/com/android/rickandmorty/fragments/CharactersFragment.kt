package com.android.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rickandmorty.viewmodels.CharactersViewModel
import com.android.rickandmorty.R
import com.android.rickandmorty.model.Characters
import com.squareup.picasso.Picasso
import java.util.zip.Inflater


private const val TAG = "Characters"
private const val REQUEST_FILTER = 0
//фрагмент вкладки персонадей
class CharactersFragment : Fragment(), FilterFragment.Callbacks {
    private lateinit var recyclerView: RecyclerView
    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var buttonNext: Button
    private lateinit var buttonPrev: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_characters, container, false)
        recyclerView = view.findViewById(R.id.characterRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        buttonNext = view.findViewById(R.id.buttonNextCharacter)
        buttonPrev = view.findViewById(R.id.buttonPrevCharacter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //подписка на обновление данных персонажей
        charactersViewModel.charactersLiveData.observe(
            viewLifecycleOwner,
            Observer { characters ->
                recyclerView.adapter = CharactersAdapter(characters)
            })
        //смена страниц
        buttonNext.setOnClickListener{
            charactersViewModel.pageNumber = charactersViewModel.pageNumber + 1
            charactersViewModel.updateCharactersList()
            if(charactersViewModel.pageNumber > 1){
                buttonPrev.isEnabled = true
            }
            if(charactersViewModel.pageNumber == charactersViewModel.MAX_PAGE_NUMBER){
                buttonNext.isEnabled = false
            }
        }
        buttonPrev.setOnClickListener{
            charactersViewModel.pageNumber = charactersViewModel.pageNumber - 1

            charactersViewModel.updateCharactersList()

            if(charactersViewModel.pageNumber == 1){
                buttonPrev.isEnabled = false
            }
            if(charactersViewModel.pageNumber < charactersViewModel.MAX_PAGE_NUMBER){
                buttonNext.isEnabled = true
            }
        }
        if(charactersViewModel.pageNumber == 1){
            buttonPrev.isEnabled = false
        }
        if(charactersViewModel.pageNumber == charactersViewModel.MAX_PAGE_NUMBER){
            buttonNext.isEnabled = true
        }
    }
    //создание пунктов меню и инициализация поиска
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    charactersViewModel.searchCharacters(queryText)
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
            charactersViewModel.updateCharactersList()
        }
        if(item.itemId == R.id.menu_item_filter){
            val dialog = FilterFragment()
            dialog.setTargetFragment(this@CharactersFragment, REQUEST_FILTER)
            dialog.show(parentFragmentManager, "Filter Characters")
        }
        return super.onOptionsItemSelected(item)
    }
    //возвращение данных для фильтрации из диалогового окна + фильтрация
    override fun onFilterSubmitted(gender: String, status: String) {
        charactersViewModel.filterCharacters(status, gender)
    }
    //инициализация RecyclerView
    private class CharacterHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView
        val cardView: CardView
        init {
            cardView = view.findViewById(R.id.characterCard)
            textView = view.findViewById(R.id.characterName)
            imageView = view.findViewById(R.id.characterImage)
        }
        fun bindName(character: Characters){
            textView.setText(character.name)
            Picasso.get()
                .load(character.image)
                .resize(64, 64)
                .centerCrop()
                .into(imageView)
            cardView.setOnClickListener {
                val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(character)
                cardView.findNavController().navigate(action)
            }
        }
    }

    private class CharactersAdapter(private val characterItems: List<Characters>) : RecyclerView.Adapter<CharacterHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CharacterHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.characters_list_item, parent, false)

            return CharacterHolder(view)
        }

        override fun getItemCount(): Int = characterItems.size

        override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
            val characterItem = characterItems[position]
            holder.bindName(characterItem)
        }
    }
}
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
import com.android.rickandmorty.model.Location
import com.android.rickandmorty.viewmodels.CharactersViewModel
import com.android.rickandmorty.viewmodels.LocationsViewModel
import org.w3c.dom.Text

//фрагмент вкладки локаций
class LocationFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationsViewModel: LocationsViewModel
    private lateinit var buttonNext: Button
    private lateinit var buttonPrev: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_locations, container, false)
        recyclerView = view.findViewById(R.id.locationRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        buttonNext = view.findViewById(R.id.buttonNextLocation)
        buttonPrev = view.findViewById(R.id.buttonPrevLocation)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //подписка на обновление данных персонажей
        locationsViewModel.locationsLiveData.observe(
            viewLifecycleOwner,
            Observer { locations ->
                Log.d("Locations", "${locations}")
                recyclerView.adapter = LocationFragment.LocationsAdapter(locations)
            })
        //смена страниц
        buttonNext.setOnClickListener{
            locationsViewModel.pageNumber = locationsViewModel.pageNumber + 1
            locationsViewModel.updateLocationsList()
            if(locationsViewModel.pageNumber > 1){
                buttonPrev.isEnabled = true
            }
            if(locationsViewModel.pageNumber == locationsViewModel.MAX_PAGE_NUMBER){
                buttonNext.isEnabled = false
            }
        }
        buttonPrev.setOnClickListener{
            locationsViewModel.pageNumber = locationsViewModel.pageNumber - 1

            locationsViewModel.updateLocationsList()

            if(locationsViewModel.pageNumber == 1){
                buttonPrev.isEnabled = false
            }
            if(locationsViewModel.pageNumber < locationsViewModel.MAX_PAGE_NUMBER){
                buttonNext.isEnabled = true
            }
        }
        if(locationsViewModel.pageNumber == 1){
            buttonPrev.isEnabled = false
        }
        if(locationsViewModel.pageNumber == locationsViewModel.MAX_PAGE_NUMBER){
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
                    locationsViewModel.searchLocations(queryText)
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
            locationsViewModel.updateLocationsList()
        }
        return super.onOptionsItemSelected(item)
    }
    //инициализация RecyclerView
    private class LocationHolder(view: View): RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewType: TextView
        val cardView: CardView
        init {
            cardView = view.findViewById(R.id.locationCard)
            textViewName = view.findViewById(R.id.locationName)
            textViewType = view.findViewById(R.id.locationType)
        }
        fun bindName(location: Location){
            textViewName.setText(location.name)
            textViewType.setText(location.type)
            cardView.setOnClickListener {
                val action = LocationFragmentDirections.actionLocationFragmentToLocationDetailsFragment(location)
                cardView.findNavController().navigate(action)
            }
        }
    }

    private class LocationsAdapter(private val locationItems: List<Location>) : RecyclerView.Adapter<LocationHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): LocationHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.locations_list_item, parent, false)
            return LocationHolder(view)
        }

        override fun getItemCount(): Int = locationItems.size

        override fun onBindViewHolder(holder: LocationHolder, position: Int) {
            val locationItem = locationItems[position]
            holder.bindName(locationItem)
        }
    }

}
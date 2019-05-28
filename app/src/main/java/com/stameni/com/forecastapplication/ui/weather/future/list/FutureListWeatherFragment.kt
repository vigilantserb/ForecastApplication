package com.stameni.com.forecastapplication.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.stameni.com.forecastapplication.R
import com.stameni.com.forecastapplication.common.BaseFragment
import com.stameni.com.forecastapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import javax.inject.Inject

class FutureListWeatherFragment : BaseFragment() {

    private lateinit var viewModel: FutureListWeatherViewModel

    @Inject
    lateinit var viewModelFactory: FutureListWeatherViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getPresentationComponent().inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)

        viewModel.getFutureWeather().observe(this, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE

            initRecyclerView(it.toFutureWeatherItems())
        })

        viewModel.getWeatherLocation().observe(this, Observer {
            if(it == null) return@Observer

            updateLocation(it.name)
            updateSubtitle()
        })
    }

    private fun updateSubtitle() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next week"
    }

    private fun updateLocation(name: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = name
    }

    private fun List<UnitSpecificSimpleFutureWeatherEntry>.toFutureWeatherItems(): List<FutureWeatherItem>{
        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>){
        val groupieAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = groupieAdapter
        }

        groupieAdapter.setOnItemClickListener { item, view ->
            Toast.makeText(this.context, "Kliknut sam", Toast.LENGTH_SHORT).show()
        }
    }
}

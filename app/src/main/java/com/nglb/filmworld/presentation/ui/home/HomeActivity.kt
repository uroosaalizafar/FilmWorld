package com.nglb.filmworld.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nglb.filmworld.R
import com.nglb.filmworld.data.model.Movie
import com.nglb.filmworld.data.model.LatestMovie
import com.nglb.filmworld.presentation.adapter.LatestMovieAdapter
import com.nglb.filmworld.presentation.adapter.MoviePagingAdapter
import com.nglb.filmworld.presentation.ui.detail.DetailActivity
import com.nglb.filmworld.presentation.ui.search.SearchMovieActivity
import com.nglb.filmworld.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint //inject dependencies in classes
class HomeActivity : AppCompatActivity(), View.OnClickListener,
    MoviePagingAdapter.OnItemClickListener {
    private val networkMonitor = NetworkMonitorUtil(this)

    lateinit var homeVM: HomeVM
    lateinit var search_movie_iv: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MoviePagingAdapter
    lateinit var latestMovieVM: LatestMovieVM
    lateinit var progressBar: ProgressBar
    lateinit var latestMovierecyclerView: RecyclerView

    @Inject
    lateinit var latestmovieadapter: LatestMovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //Initialize all the widgets in this method
        initializeWidgets()
        // this class show the alert if internet connection failed
        Dialogbox().getInternetAlert(networkMonitor, this@HomeActivity)
        // latest movie UI setup
        setupUI()
        // latesest movie api call
        setupAPICall()
        //discover movie UI SETUP and API CALL
        // I implemented endless scrolling
        setupAllMovieUI()
    }

    // intialize all widgets of home activity
    fun initializeWidgets() {
        search_movie_iv = findViewById(R.id.search_movie)
        search_movie_iv.setOnClickListener(this)
        recyclerView = findViewById(R.id.movie_recyclerView)
        progressBar = findViewById(R.id.progressBar)
        latestMovierecyclerView = findViewById(R.id.latest_recyclerView)
    }

    // this click fun move from home to search activity
    override fun onClick(p0: View?) {
        val intent = Intent(this@HomeActivity, SearchMovieActivity::class.java)
        startActivity(intent)
        this@HomeActivity?.finish()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }


    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    // this is a recycleview item on click method .
    override fun onItemClick(movie: Movie) {
        val intent = Intent(this@HomeActivity, DetailActivity::class.java)
        // I create a fun in I pass the Intent
        items(movie, intent)
        startActivity(intent)
        this@HomeActivity?.finish()
    }

    // this method is for MOVIE DISCOVER
    // in which i intialize the UI of movie and also call the api
    private fun setupAllMovieUI() {
        homeVM = ViewModelProvider(this).get(HomeVM::class.java)

        adapter = MoviePagingAdapter(this)
        // GridLayoutManager is used to show list into grid and also 3 indicates that 3 items in a row
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        homeVM.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }


    private fun setupUI() { // This method setup the ui of LATEST MOVIE
        latestMovierecyclerView.layoutManager = LinearLayoutManager(this)
        latestmovieadapter = LatestMovieAdapter()
        latestMovierecyclerView.addItemDecoration(
            DividerItemDecoration(
                latestMovierecyclerView.context,
                (latestMovierecyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        latestMovierecyclerView.adapter = latestmovieadapter
    }

    private fun setupAPICall() {// This method call the api to fetch latest mvie data
        latestMovieVM = ViewModelProvider(this).get(LatestMovieVM::class.java)
        latestMovieVM.fetchLatestMovie(Constant.API_KEY).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data.let { latestmovieData -> renderList(listOf(latestmovieData!!.body()!!)) }
                    latestMovierecyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    latestMovierecyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Log.e("ERRORR", "" + it.message)
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    // this method add the data of latest movie in to arraylist
    private fun renderList(latestmovie: List<LatestMovie>) {
        latestmovieadapter.apply {
            addData(latestmovie)
            notifyDataSetChanged()
        }
    }
}
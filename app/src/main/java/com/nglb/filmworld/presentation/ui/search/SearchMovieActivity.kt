package com.nglb.filmworld.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nglb.filmworld.R
import com.nglb.filmworld.data.model.Movie
import com.nglb.filmworld.presentation.adapter.MoviePagingAdapter
import com.nglb.filmworld.presentation.ui.detail.DetailActivity
import com.nglb.filmworld.presentation.ui.home.HomeActivity
import com.nglb.filmworld.utils.Constant.MOVIE_BACKDROP
import com.nglb.filmworld.utils.Constant.MOVIE_OVERVIEW
import com.nglb.filmworld.utils.Constant.MOVIE_POSTER
import com.nglb.filmworld.utils.Constant.MOVIE_RELEASE_DATE
import com.nglb.filmworld.utils.Constant.MOVIE_TITLE
import com.nglb.filmworld.utils.Constant.MOVIE_VOTE_AVG
import com.nglb.filmworld.utils.Constant.MOVIE_VOTE_COUNT
import com.nglb.filmworld.utils.Dialogbox
import com.nglb.filmworld.utils.NetworkMonitorUtil
import com.nglb.filmworld.utils.items
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieActivity : AppCompatActivity(), MoviePagingAdapter.OnItemClickListener,
    View.OnClickListener {
    private val networkMonitor = NetworkMonitorUtil(this)


    lateinit var searchMovieVM: SearchMovieVM
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MoviePagingAdapter
    lateinit var backtohome: ImageView
    lateinit var search_movie_et: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_search_activity)
        intialize()
        Dialogbox().getInternetAlert(networkMonitor, this@SearchMovieActivity)
        searchMovieVM = ViewModelProvider(this).get(SearchMovieVM::class.java)

        adapter = MoviePagingAdapter(this@SearchMovieActivity)

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        searchMovieVM.searchMovie.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
        search_movie_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                recyclerView.scrollToPosition(0)
                searchMovieVM.searchMovieString(editable.toString())
            }
        })

    }

    fun intialize() {
        recyclerView = findViewById(R.id.movie_search_recyclerView)
        search_movie_et = findViewById(R.id.search_movie_et)
        backtohome = findViewById(R.id.backtohome);
        backtohome.setOnClickListener(this)


    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(this@SearchMovieActivity, DetailActivity::class.java)
        items(movie, intent)
        startActivity(intent)
        this@SearchMovieActivity?.finish()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }


    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this@SearchMovieActivity, HomeActivity::class.java)
        startActivity(intent)
        this@SearchMovieActivity?.finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@SearchMovieActivity, HomeActivity::class.java)
        startActivity(intent)
        this@SearchMovieActivity?.finish()
    }
}
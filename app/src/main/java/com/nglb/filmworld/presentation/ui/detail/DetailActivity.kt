package com.nglb.filmworld.presentation.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.nglb.filmworld.R
import com.nglb.filmworld.presentation.ui.home.HomeActivity
import com.nglb.filmworld.utils.Constant
import com.nglb.filmworld.utils.Constant.BASE_URL_IMG_POSTER
import com.nglb.filmworld.utils.Constant.MOVIE_BACKDROP
import com.nglb.filmworld.utils.Constant.MOVIE_OVERVIEW
import com.nglb.filmworld.utils.Constant.MOVIE_POSTER
import com.nglb.filmworld.utils.Constant.MOVIE_RELEASE_DATE
import com.nglb.filmworld.utils.Constant.MOVIE_TITLE
import com.nglb.filmworld.utils.Constant.MOVIE_VOTE_AVG
import com.nglb.filmworld.utils.Constant.MOVIE_VOTE_COUNT

class DetailActivity : AppCompatActivity(),View.OnClickListener {


    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView

    private lateinit var rating: TextView
    private lateinit var vote_count: TextView
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    lateinit var backtohome: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        Initialize()
        val extras = intent.extras
        if (extras != null) {
            MovieDetails(extras)
        } else {
            finish()
        }
    }

    private fun Initialize() {
        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.vote_avg)
        releaseDate = findViewById(R.id.release_date_tv)
        vote_count = findViewById(R.id.vote_count)
        overview = findViewById(R.id.movie_overview)
        backtohome = findViewById(R.id.backtohome);
        backtohome.setOnClickListener(this)
    }


    private fun MovieDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load(BASE_URL_IMG_POSTER+backdropPath)
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load(
                    BASE_URL_IMG_POSTER+posterPath)
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.text = extras.getDouble(MOVIE_VOTE_AVG, 0.0).toString()
        vote_count.text = extras.getInt(MOVIE_VOTE_COUNT, 0).toString() + " Votes"
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }
    override fun onClick(p0: View?) {
        val intent = Intent(this@DetailActivity, HomeActivity::class.java)
        startActivity(intent)
        this@DetailActivity?.finish()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@DetailActivity, HomeActivity::class.java)
        startActivity(intent)
        this@DetailActivity?.finish()
    }
}
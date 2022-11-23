package com.nglb.filmworld

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp //trigger hilt code generation
class MovieApp : Application() {
}
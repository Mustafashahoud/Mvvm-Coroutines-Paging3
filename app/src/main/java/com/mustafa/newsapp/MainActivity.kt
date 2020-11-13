package com.mustafa.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mustafa.newsapp.util.UiHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var uiHelper: UiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if(!uiHelper.getConnectivityStatus()) {
//            uiHelper.showSnackBar(
//                mainActivityRootView,
//                resources.getString(R.string.error_network_connection)
//            )
//            return
//        }
    }
}
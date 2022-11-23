package com.nglb.filmworld.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class Dialogbox {
    fun getInternetAlert(networkMonitor: NetworkMonitorUtil, context: Activity){
        networkMonitor.result = { isAvailable, type ->
            context.runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                            }
                            ConnectionType.Cellular -> {
                            }
                            else -> {}
                        }
                    }
                    false -> {
                        val dialogBuilder = AlertDialog.Builder(context)

                        // set message of alert dialog
                        dialogBuilder.setMessage("Please Check Your Internet Connection")
                            // if the dialog is cancelable
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                                context.finish()
                            })

                        // create dialog box
                        val alert = dialogBuilder.create()
                        // set title for alert dialog box
                        alert.setTitle("No Internet")
                        // show alert dialog
                        alert.show()
                    }
                }
            }
        }
    }
}
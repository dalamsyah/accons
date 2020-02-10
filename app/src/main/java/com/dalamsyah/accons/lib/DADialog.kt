package com.dalamsyah.accons.lib

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import com.dalamsyah.accons.R

class DADialog {

    class DialoagOne(context: Context){

        lateinit var progress : LinearLayout
        lateinit var success : LinearLayout
        lateinit var dialog : Dialog
        lateinit var btnOke : Button

        init {

            dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_one)
            dialog.setCancelable(false)
            progress = dialog.findViewById(R.id.content_progress)
            success = dialog.findViewById(R.id.content_success)
            btnOke = dialog.findViewById(R.id.btnOke)

            btnOke.setOnClickListener {
                dialog.dismiss()
            }

            progress.visibility = View.VISIBLE
            success.visibility = View.GONE

        }

        fun hideProgress(){

            progress.visibility = View.GONE
            success.visibility = View.VISIBLE
        }

        fun show(){
            dialog.show()
        }


    }

}
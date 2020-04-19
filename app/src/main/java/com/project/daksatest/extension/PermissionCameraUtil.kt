package com.project.daksatest.extension

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionCameraUtil {
    val READ_EXTERNAL_STORAGE = 100
    val CAMERA = 200


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkPermission(context: Context, permissionCode: Int,
                        permissionString: String, permissionRationale: String): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permissionString) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permissionString)) {
                    val alertBuilder = AlertDialog.Builder(context)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setMessage(permissionRationale)
                    alertBuilder.setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(context as Activity,
                                arrayOf(permissionString), permissionCode)
                    })
                    val alert = alertBuilder.create()
                    alert.show()
                } else {
                    ActivityCompat.requestPermissions(context as Activity,
                            arrayOf(permissionString), permissionCode)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

}
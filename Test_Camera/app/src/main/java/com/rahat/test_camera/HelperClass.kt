package com.rahat.test_camera

import android.content.Context
import es.dmoral.toasty.Toasty

class HelperClass {
    companion object {
        const val CAMERA_PERM_CODE = 101
        const val CAMERA_REQUEST_CODE = 102
        const val GALLERY_REQUEST_CODE = 105
        fun showMsg(context: Context, msg: String) {
            Toasty.success(context, msg, Toasty.LENGTH_LONG).show()
        }

        fun errorMsg(context: Context, msg: String) {
            Toasty.error(context, msg, Toasty.LENGTH_LONG).show()
        }


    }
}
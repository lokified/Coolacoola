package com.loki.coolacoola.util.extension

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.showToast(content: String) {

    Toast.makeText(context, content, Toast.LENGTH_LONG).show()
}
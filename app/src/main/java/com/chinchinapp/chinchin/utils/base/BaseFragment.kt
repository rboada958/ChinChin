package com.chinchinapp.chinchin.utils.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.utils.Commons
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar

abstract class BaseFragment : Fragment {

    private val unknownErrorMessage = Commons.getString(R.string.unknown_error)
    private val timeoutErrorMessage = Commons.getString(R.string.try_again)
    private val noInternetError = Commons.getString(R.string.no_internet)

    protected var fragmentView: View? = null

    constructor() : super()
    constructor(@LayoutRes layout: Int) : super(layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentView = null
    }

    protected fun getDefaultNavOptions(fade: Boolean = false) = navOptions {
        if (!fade) {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popExit = R.anim.slide_out_right
                popEnter = R.anim.slide_in_left
            }
        } else {
            anim {
                enter = R.anim.fade_in
                exit = R.anim.fade_out
                popExit = R.anim.fade_out
                popEnter = R.anim.fade_in
            }
        }
    }

    fun showSnackSuccessLong(msg: String) {
        Snackbar
            .make(fragmentView!!, msg, Snackbar.LENGTH_INDEFINITE)
            .setBackgroundTint(resources.getColor(R.color.colorPrimary))
            .setDuration(5000)
            .show()
    }

    fun showSnackSuccess(msg: String, long: Boolean = false) {
        val snack = if (!long) fragmentView?.snackbar(msg)
        else fragmentView?.longSnackbar(msg)
        snack?.view?.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimary
            )
        )
    }

    fun showSnackError(msg: String, long: Boolean = false) {
        val snack = if (!long) fragmentView?.snackbar(msg)
        else fragmentView?.longSnackbar(msg)
        snack?.view?.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorRed
            )
        )
        val textView =
            snack?.view?.findViewById(com.google.android.material.R.id.snackbar_text) as TextView //Get reference of snackbar textview
        textView.maxLines = 5
    }

    fun showErrorMessage(errorType: ErrorType) {
        when (errorType) {
            ErrorType.NETWORK -> showSnackError(noInternetError)
            ErrorType.TIMEOUT -> showSnackError(timeoutErrorMessage)
            else -> showSnackError(unknownErrorMessage)
        }
    }
}
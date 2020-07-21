package com.chinchinapp.chinchin.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.OpenableColumns
import com.google.api.client.util.*
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.app.ChinChinApp
import com.chinchinapp.chinchin.ui.MainActivity
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object Commons {

    const val MINUTE_IN_MILLIS = 60000L
    val dm: DisplayMetrics = Resources.getSystem().displayMetrics

    fun dpToPx(dp: Int): Int {
        val displayMetrics = ChinChinApp.appContext.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    fun pxToDp(px: Float): Float {
        val metrics = ChinChinApp.appContext.resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun getColor(color: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ChinChinApp.appContext.getColor(color)
        else
            ChinChinApp.appContext.resources.getColor(color)
    }

    fun getDrawable(drawable: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ChinChinApp.appContext.getDrawable(drawable)
        else
            ChinChinApp.appContext.resources.getDrawable(drawable)
    }

    fun getString(string: Int): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ChinChinApp.appContext.getString(string)
        else
            ChinChinApp.appContext.resources.getString(string)
    }

    fun hideKeyboard(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.localizedMessage ?: "Something wrong happened"
        }
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun copy(context: Context, srcUri: Uri, dstFile: File) {
        try {
            val inputStream = context.contentResolver.openInputStream(srcUri) ?: return
            val outputStream = FileOutputStream(dstFile)
            IOUtils.copy(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getFileName(context: Context, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
            try {
                if (cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    fun getFilePathFromURI(context: Context, contentUri: Uri): String? {
        //copy file and send new file path
        val fileName = getFileName(context, contentUri)
        if (!TextUtils.isEmpty(fileName)) {
            val copyFile = File(Environment.DIRECTORY_DOWNLOADS + File.separator + fileName)
            copy(context, contentUri, copyFile)
            return copyFile.absolutePath
        }
        return null
    }

    fun imageGlide(context: Context, view: ImageView, url: String) {
        Glide.with(context)
            .load(url)
            .override(300, 300)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(view)
    }

    fun showToast(text: String) {
        Toast.makeText(ChinChinApp.appContext, text, Toast.LENGTH_SHORT).show()
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File =
            ChinChinApp.appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            val currentPhotoPath = toURI().toString()
        }
    }

    fun camelCase(str: String): String {
        val builder = StringBuilder(str)

        var isLastSpace = true

        for (i in builder.indices) {
            val ch = builder[i]
            isLastSpace =
                if (isLastSpace && ch >= 'a' && ch <= 'z') {
                    builder.setCharAt(i, (ch.toInt() + ('A' - 'a')).toChar())
                    false
                } else ch == ' '
        }
        return builder.toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun parseStringDate(date: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(date)!!
    }

    fun getMonthString(yourdate: Date, context: Context): String {
        val c = Calendar.getInstance()
        c.time = yourdate // yourdate is an object of type Date
        val arrayMonth = context.resources.getStringArray(R.array.month_array)
        val month = c.get(Calendar.MONTH) // this will for example return 3 for tuesday
        return arrayMonth[month]
    }

    fun getMonth(yourdate: Date): String {
        val c = Calendar.getInstance()
        c.time = yourdate // yourdate is an object of type Date
        val month = c.get(Calendar.MONTH) // this will for example return 3 for tuesday
        val newMonth = month + 1
        return newMonth.toString()
    }

    fun getDayOfMonth(yourdate: Date): Int {
        val c = Calendar.getInstance()
        c.time = yourdate // yourdate is an object of type Date
        return c.get(Calendar.DAY_OF_MONTH)
    }

    fun getYear(yourdate: Date): Int {
        val c = Calendar.getInstance()
        c.time = yourdate // yourdate is an object of type Date
        return c.get(Calendar.YEAR)
    }

    fun getHour(date: String): String {

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val formatHour = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val yourDate = format.parse(date)!!

        return formatHour.format(yourDate)
    }


}
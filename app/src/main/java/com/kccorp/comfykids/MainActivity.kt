package com.kccorp.comfykids

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.room.Room
import com.kccorp.comfykids.database.AppDatabase
import com.kccorp.comfykids.database.MessageHistory
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private var permissionList = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "message_history_db"
        ).allowMainThreadQueries().build()
//        db.messageHistoryDao().insert(MessageHistory("a", "b", "c"))
//        Log.d(TAG, db.messageHistoryDao().getAll().toString())

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
//            R.id.action_share -> {
//                if (checkPermission())
//                    takeScreenshot()
//                true
//            }
            R.id.action_about -> {
                val version = packageManager.getPackageInfo(packageName, 0).versionName
                val message = "제작: 기철\n디자인: 채연,소윤\n구경: 이수희\n\n버젼:$version"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun takeScreenshot() {
        val now = Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        try {
            // image naming and path  to include sd card  appending name you choose for file

            // create bitmap screen capture
            val v1: View = window.decorView.rootView

            v1.isDrawingCacheEnabled = true
//            v1.buildDrawingCache(true)
            val bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false

            val mPath: String = "myfuture_1.jpg"
//            val imageFile = File(mPath)
            val imageFile = File(getExternalFilesDir(null), mPath)

            Log.d(TAG, "takeScreenshot: " + imageFile.path)
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            shareScreenshot(imageFile)
        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }
    }

    private fun shareScreenshot(imageFile: File) {
        val extRoot = getExternalFilesDir(null)
        val someFile = "myfuture_1.jpg"
        Log.d(TAG, "shareScreenshot: " + imageFile.path)

        val imgFile = File("files/", someFile)
        Log.d(TAG, "shareScreenshot1: " + imgFile.path)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/jpeg" // 엑셀파일 공유 시
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        val contentUri1 = FileProvider.getUriForFile(
            applicationContext,
            applicationContext.packageName + ".fileprovider",
            imgFile
        )
        val contentUri = FileProvider.getUriForFile(
            applicationContext,
            applicationContext.packageName + ".fileprovider",
            imgFile
        )
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        startActivity(Intent.createChooser(shareIntent, "공유"))


//        val uri: Uri = Uri.fromFile(imageFile)
//        val sharingIntent = Intent(Intent.ACTION_SEND)
//        sharingIntent.type = "image/*"
//        val shareBody = "My highest score with screen shot"
//        sharingIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "My Catch score")
//        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
//
//        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    private fun checkPermission(): Boolean {
        Log.d(TAG, "checkPermission: ")

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return false

        for (permission in permissionList) {
            val chk = checkCallingOrSelfPermission(permission)
            return if (chk == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissionList, 0)
                false
            } else {
                true
            }
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            for (result in grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    takeScreenshot()
                } else {
                    Toast.makeText(applicationContext, "앱권한설정하세요", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    companion object {
        const val TAG: String = "COMFYKIDS"
    }
}
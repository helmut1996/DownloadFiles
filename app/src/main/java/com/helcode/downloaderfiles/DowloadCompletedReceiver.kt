@file:Suppress("UNREACHABLE_CODE")

package com.helcode.downloaderfiles

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.util.Log


class DowloadCompletedReceiver() : BroadcastReceiver(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE"){
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L){
                Log.d("Descarga","Descagaga del id $id finalizada")

            }
            if (context != null) {

                scheduleInstallation(context, id, 6000)
                Log.d("instalar","instalacion del id $id finalizada")
            }
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DowloadCompletedReceiver> {
        override fun createFromParcel(parcel: Parcel): DowloadCompletedReceiver {
            return DowloadCompletedReceiver(parcel)
        }

        override fun newArray(size: Int): Array<DowloadCompletedReceiver?> {
            return arrayOfNulls(size)
        }
    }


    private fun installApk(context: Context, downloadId: Long) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        // Obtener la URI del archivo descargado
        val downloadFileUri = downloadManager.getUriForDownloadedFile(downloadId)
        if (downloadFileUri != null) {
            // Crear un intent para instalar la APK
            val installIntent = Intent(Intent.ACTION_VIEW)
            installIntent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive")
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

//            // Verificar si la versión de Android es 7.0 (Nougat) o superior
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                // Si es Android 7.0 o superior, se necesita proporcionar el Content URI a través de FileProvider
//                val contentUri = FileProvider.getUriForFile(
//                    context,
//                    context.applicationContext.packageName + ".provider",
//                    File(downloadFileUri.path)
//                )
//                installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive")
//                installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            }

            // Iniciar la instalación
            context.startActivity(installIntent)
        }
    }


    private fun scheduleInstallation(context: Context, downloadId: Long, delayMillis: Long) {
        val handler = Handler()
        handler.postDelayed(Runnable { installApk(context, downloadId) }, delayMillis)
    }

}
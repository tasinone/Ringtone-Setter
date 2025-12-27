package com.android.ringtonesetter

import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val audioPicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { setAsRingtone(it) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Request Modify System Settings permission
        if (!Settings.System.canWrite(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnPick).setOnClickListener {
            audioPicker.launch("audio/*")
        }
    }

    private fun setAsRingtone(uri: Uri) {
        try {
            RingtoneManager.setActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_RINGTONE,
                uri
            )

            Toast.makeText(
                this,
                "Ringtone set successfully!",
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Failed to set ringtone: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

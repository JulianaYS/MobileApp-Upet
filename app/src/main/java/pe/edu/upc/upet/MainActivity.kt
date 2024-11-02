package pe.edu.upc.upet

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cloudinary.android.MediaManager
import pe.edu.upc.upet.navigation.Navigation
import pe.edu.upc.upet.ui.theme.UpetTheme

class MainActivity : ComponentActivity() {
    private var config: HashMap<String, String> = HashMap()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState )
        enableStrictMode()
        config["cloud_name"] = "dqgpis4fg"
        config["api_key"] = "824527285689877"
        config["api_secret"] = "GXHwHHEhNbEFOyPP0r6VuOQ84Dc"
        config["secure"] = "true"
        MediaManager.init(this, config)
        setContent {
            UpetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

fun enableStrictMode() {
    StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectNetwork()
            .penaltyLog()
            .build()
    )
}
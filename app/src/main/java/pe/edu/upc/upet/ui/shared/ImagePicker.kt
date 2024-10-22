package pe.edu.upc.upet.ui.shared

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import pe.edu.upc.upet.feature_auth.data.remote.UpdateUserRequest
import pe.edu.upc.upet.feature_auth.data.repository.AuthRepository
import pe.edu.upc.upet.ui.screens.petowner.getRole

@Composable
fun ImagePicker(
    id: Int,
    newImageUri: MutableState<Uri?>,
    imageUrl: MutableState<String>,
    showDialog: MutableState<Boolean>,
    onUpdateImageUrl: (String) -> Unit
) {
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        newImageUri.value = uri
    }

    val isLoading = remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ImageEdit(
            imageUrl = imageUrl.value,
            newImageUri = newImageUri.value,
            onImageClick = { imageLauncher.launch("image/*") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        newImageUri.value?.let {
            CustomButton(
                text = "Save Image",
                icon = Icons.Default.Image,
                onClick = {
                    isLoading.value = true
                    uploadImage(it) { url ->
                        if (url.isNotEmpty()) {
                            AuthRepository().updateUser(
                                id,
                                UpdateUserRequest(
                                    image_url = url,
                                    role = getRole() ?: ""
                                )
                            ) { success ->
                                if (success) {
                                    showDialog.value = true
                                    newImageUri.value = null
                                    onUpdateImageUrl(url)
                                    imageUrl.value = url
                                }
                                isLoading.value = false
                            }
                        }
                    }
                }
            )
        }
        if (isLoading.value) {
            LinearProgressIndicator()
        }
    }
}

fun uploadImage(imageUrl: Uri, callback: (String) -> Unit) {
    MediaManager.get().upload(imageUrl)
        .option("public_id", "olympic_flag")
        .callback(object : UploadCallback {
            override fun onStart(requestId: String) {

            }

            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {

            }

            override fun onSuccess(requestId: String, resultData: Map<*, *>) {

                var uploadedImageUrl = resultData["url"].toString()
                uploadedImageUrl = uploadedImageUrl.replace("http", "https")
                println("Upload successful: $resultData")
                callback(uploadedImageUrl)
            }

            override fun onError(requestId: String, error: ErrorInfo) {

                println("Upload error: ${error.description}")
                callback("")
            }

            override fun onReschedule(requestId: String, error: ErrorInfo) {

            }
        })
        .dispatch()
}
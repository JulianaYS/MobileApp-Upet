package pe.edu.upc.upet.ui.shared

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.upet.ui.theme.Pink

@Composable
fun ImageEdit(imageUrl: String, newImageUri: Uri?, onImageClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(75.dp))
            .clickable(onClick = onImageClick)
    ) {
        if (newImageUri != null) {
            GlideImage(
                imageModel = { newImageUri },
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
        } else {
            GlideImage(
                imageModel = { imageUrl },
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Icon",
                tint = Pink
            )
        }
    }
}
package zuper.dev.android.dashboard.screens

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import zuper.dev.android.dashboard.data.model.Slice

@Composable
fun StackedBar(modifier: Modifier, slices: List<Slice>) {
    Canvas(modifier = modifier)
    {
        val canvasWidth = size.width
        val canvasHeight = size.height

        var currentX = 0f
        slices.forEachIndexed { _: Int, slice: Slice ->
            val width = (slice.value) / 20f * canvasWidth

            // Draw Rectangles
            drawRect(
                color = Color(slice.color), topLeft = Offset(currentX, 0f), size = Size(
                    width,
                    canvasHeight
                )
            )
            currentX += width
        }
    }

}



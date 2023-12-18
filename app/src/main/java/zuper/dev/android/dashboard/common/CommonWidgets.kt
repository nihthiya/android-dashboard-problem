package zuper.dev.android.dashboard.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zuper.dev.android.dashboard.data.model.Slice
import zuper.dev.android.dashboard.screens.StackedBar

@Composable
fun DefaultHeaderText(
    text: String, modifier: Modifier
) {
    Text(
        text,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color.Black,
        modifier = modifier
    )
}

@Composable
fun DrawRec(color: Color, modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(6f, 6f),
            size = Size(width = 12.dp.toPx(), height = 12.dp.toPx())
        )
    }
}

@Composable
fun StatusColorText(
    text: String, modifier: Modifier
) {
    Text(
        text,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = modifier
    )
}

@Composable
fun Chart(slices: List<Slice>, text: String, text2: String) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            text,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(12.dp, 6.dp, 0.dp, 0.dp)
                .weight(1f)
        )


        Text(
            text2,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(0.dp, 6.dp, 12.dp, 0.dp)
                .weight(1f)

        )
    }

    Spacer(
        modifier = Modifier
            .height(10.dp)
    )
    StackedBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(14.dp, 0.dp, 14.dp, 0.dp),
        slices = slices
    )
}



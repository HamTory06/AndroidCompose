package com.example.chapter3

import android.icu.util.Measure
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.chapter3.ui.theme.Test_composeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderDemo()
            /*
            BoxWithConstraints(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.width(min(400.dp, maxWidth)),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    val color = remember { mutableStateOf(Color.Magenta) }
                    ColorPicker(color)
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color.value),
                        text = "#${color.value.toArgb().toUInt().toString(16)}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4.merge(
                            TextStyle(
                                color = color.value.complementary()
                            )
                        )
                    )
                }
            }
             */
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Test_composeTheme {
        Greeting("Android")
    }
}
@Composable
fun ColoredTextDemo(
    text: String = "",
    color: Color = Color.Black
) {
    Text(
        text = text,
        style = TextStyle(color = color)
    )
}

@Composable
fun ShortColoredTextDemo(
    text: String = "",
    color: Color = Color.Black
) = Text(
    text = text,
    style = TextStyle(color = color)
)

@Suppress("ComposableLambdaParameterPosition")
@Composable
fun Layout(
    context: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    measure: MeasurePolicy
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
}

data class Message(val author: String, val body: String)


@Composable
fun MessageCard(msg: Message){
    Row{
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture"
        )
        Column{
            Text(text = msg.author)
            Text(text = msg.body)
        }
    }

}

@Composable
fun ColorPicker(color: MutableState<Color>){
    val red = color.value.red
    val green = color.value.green
    val blue = color.value.blue
    Column {
        Slider(
            value = red,
            onValueChange = { color.value = Color(it, green, blue) }
        )
        Slider(
            value = green,
            onValueChange = { color.value = Color(red, it, blue) }
        )
        Slider(
            value = blue,
            onValueChange = { color.value = Color(red, green, it) }
        )
    }
}

fun Color.complementary() = Color(
    red = 1F - red,
    green = 1F - green,
    blue = 1F - blue
)

@Preview(showBackground = true)
@Composable
fun OrderDemo(){
    var color by remember { mutableStateOf(Color.Blue) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .clickable {
                color = if(color == Color.Blue)
                    Color.Red
                else
                    Color.Blue
            }
            .border(BorderStroke(width = 2.dp, color = color))
            .background(Color.LightGray)

    )
}
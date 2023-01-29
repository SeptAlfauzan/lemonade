package com.example.lemonade

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DefaultPreview()
                }
            }
        }
    }
}

@Composable
fun Step(name: String, image: Painter, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello $name!", fontSize = 18.sp)
        Spacer(Modifier.height(16.dp))
        Image(painter = image, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    var state: Int by remember {
        mutableStateOf(0)
    }

    var random: Int = 0
    var squeezed: Int = 0
    var isSqueezed: Boolean = false

    val images: List<Painter> = listOf(
        painterResource(R.drawable.lemon_tree),
        painterResource(R.drawable.lemon_squeeze),
        painterResource(R.drawable.lemon_drink),
        painterResource(R.drawable.lemon_restart),
    )

    val texts: List<String> = listOf(
        "Tap the lemon tree to select a lemon",
        "Keep tapping the lemon to squeeze it",
        "Tap the lemonade to drink it",
        "Tap the empty glass to start again"
    )


    fun onTap(){
            if(state == 1){
            if(!isSqueezed) random = (2..4).random()
            isSqueezed = true
            squeezed++

            if(squeezed == random){
                isSqueezed = false
                state += 1
                squeezed = 0
            }

            if (isSqueezed) Toast.makeText(context, "Keep squeezing", Toast.LENGTH_LONG).show()
        }else{
            state = if(state >= images.size - 1) 0 else state + 1
        }
    }
    LemonadeTheme {
        Step(texts[state], images[state]){
            onTap()
        }
    }
}
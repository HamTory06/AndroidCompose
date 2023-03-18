package com.example.chapter2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chapter2.ui.theme.Test_composeTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Factorial()
                    BoxDemo()
                }
            }
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

@Preview
@Composable
fun Factorial(){
    //mutableStateOf()는 상태를 생성할 수 있다.
    //버튼상태 저장
    var expended by remember { mutableStateOf(false) }
    //Text에 나올 글 저장
    var text by remember { mutableStateOf(factorialAsString(0))}
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.clickable {
                expended = true
                Log.d("상태","클릭")
            },
            text = text,
            style = MaterialTheme.typography.h2
        )
        DropdownMenu(expanded = expended,
            onDismissRequest = {
                expended = false
            }) {
            for (n in 0 until 10){
                DropdownMenuItem(onClick = {
                    expended = false
                    text = factorialAsString(n)
                }) {
                    Text("$n!")
                }
            }
        }
    }
}

@Preview
@Composable
fun ButtonDemo() {
    Box{
            Text(
                text = "Click me!",
                modifier = Modifier.clickable {
                    Log.d("상태","클릭")
                }
            )
        }
    }

fun factorialAsString(n: Int): String{
    var result = 1L
    for(i in 1..n){
        result *= i
    }
    return "$n! = $result"
}
@Composable
@Preview
fun BoxDemo(){
    Box(contentAlignment = Alignment.Center){
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 100.dp)
                .background(Color.Green)
        )
        Box(
            modifier = Modifier
                .size(width = 80.dp, height = 80.dp)
                .background(Color.Yellow)
        )
        Text(
            text = "Hello",
            color = Color.Black,
            modifier = Modifier.align(Alignment.TopStart)
        )
    }
}
package com.example.test_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.test_compose.ui.theme.Test_composeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Hello()
                }
            }
        }
    }
}

@Composable
//@Preview error
//매개 변수가 있는 함수에는 @Preview 어노테이션으로 미리보기를 할수 없다
fun Greeting(name: String) {
    Text(
        //<string name="welcome">%s ,Welcome</string> %s자리에 매개변수 name가 들어간다
        text = stringResource(id = R.string.welcome,name),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.subtitle1
    )
}


@Preview
@Composable
//이를 해결 하기 위해서 컴포저블(Composable) 함수를 감싼 함수를 만드는 방법이 있다.
//이 함수는 미리 보기를 가능케 한다는 점 외에는 추가적인 가치를 부여하지 않는다
fun GreetingWrapper(){
    Greeting("jetPack Compose")
}
 @Composable
 @Preview
 //컴포저블 함수에 기본값을 추가 할수 있다
 fun AltGreeting(name: String = "JetPack Compose"){
     Text(
         text = stringResource(id = R.string.hello, name),
         textAlign = TextAlign.Center,
         style = MaterialTheme.typography.subtitle1
     )
 }

//@PreviewParameter를 사용하면 미리 보기에만 영향을 주면서 컴포저블 함수에 값을 전달할 수 있다.
//이 기능은 새로운 클래스를 작성해야 한다.
class HelloProvider: PreviewParameterProvider<String>{
    override val values: Sequence<String>
        get() = listOf("PreviewParameterProvider").asSequence()
}
@Composable
@Preview
fun AltGreeting2(@PreviewParameter(HelloProvider::class)
                 name: String){
    Text(
        text = stringResource(id = R.string.hello, name),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.subtitle1
    )
}


@Composable
fun Welcome(){
    Text(
        text = stringResource(id = R.string.welcome),
        style = MaterialTheme.typography.subtitle1
    )
}

@Composable
fun TextAndButton(name: MutableState<String>,
                  nameEntered: MutableState<Boolean>){
    //Row()함수는 modifier라는 매개변수를 받는다
    //Rom()함수의 내부 요소를 가로로 정렬한다
    //modifier(변경자)는 컴퓨저블 함수의 외형고 행위에 영향을 준다.
    //padding(top = 8.dp)는 열 상단에 밀도 독립 픽셀값을 8만큼 설정해 패딩에 추가(위에 있는 환영 인사말과 8dp만큼 떨어지게 된다.)
    Row(modifier = Modifier.padding(top = 8.dp)) {
        //TestAndBut()에 전달된 name nameEntered 변수를 사용하는데 이들의 타입은 MutableState다 MutableState객체는 변경 할 수 있는 값이라는 특징을 지닌다
        //name.value나 nameEntered.value와 같은 방식으로 값에 접근 할수 있다.
        TextField(
            //value 매개변수는 이미 입력된 텍스트와 같은 텍스트 입력 필드의 현재 값을 인자로 받는다.
            value = name.value,
            //onValueChange는 텍스트를 변경하는 일이 발생하는 경우 호출된다.
            onValueChange = {
                name.value = it
            },
            //사용자가 입력하기 전까지 보여줄 텍스트를 포함한다.
            //xml에서 textview의 hint
            placeholder = {
                Text(text = stringResource(id = R.string.hint))
            },
            //
            modifier = Modifier
                //alignByBaseline()을 사용하면 특정Row()내부에서 다른 컴포지션 함수들의 기준선을 정렬할 수 있다
                .alignByBaseline()
                .weight(1.0F),
            singleLine = true,
            //keyboardOptions과 keyboardActions에서는 화면에 나타난 키보드의 동작을 기술한다
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                capitalization = KeyboardCapitalization.Words,
            ),
            //특정 동작을 수행하면 nameEntered.value = true로 설정 된다
            keyboardActions = KeyboardActions(onAny = {
                nameEntered.value = true
            })
        )
        Button(modifier = Modifier
            //버튼의 기준선을 텍스트 입력 필드와 맞추고자 alignByBaseline()을 호출
            .alignByBaseline()
            //버튼의 모든 면에 패딩을 적용
            .padding(8.dp),
        onClick = {
            //클릭을 하면 버튼 nameEntered.value = true
            nameEntered.value = true
        }) {
            //Text R.string.done 보여주는 화면
            Text(text = stringResource(id = R.string.done))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Hello(){
    //name변수와 nameEntered변수를 초기화
    val name = remember { mutableStateOf("") }
    val nameEntered = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        if(nameEntered.value){
            Greeting(name.value)
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Welcome()
                TextAndButton(name, nameEntered)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Test_composeTheme {
        Greeting("Hello")
    }
}
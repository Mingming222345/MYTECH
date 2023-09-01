package com.example.my

import PortfolioScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.my.ui.theme.MyTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                val navController = rememberNavController()
                var listEmpty = listOf<Int>()
                var listA by remember {
                    mutableStateOf(listEmpty)
                }
                NavHost(navController = navController, startDestination = "first") {
                    composable("first") {
                        listA = mainScreen(navController)
                    }
                    composable("second") {
                        testScreen(navController)
                    }
                    composable("third") {
                        PortfolioScreen(navController, listA)
                    }
                    composable("forth") {
                        CommuScreen(navController)
                    }
                }
//                SplashScreen(navigateToMain = {
//                    // 스플래시 화면이 표시된 후에 호출되는 함수
//                    navController.navigate("first") // 예시로 첫 번째 화면으로 이동
//                })
            }
        }
    }
}




@Composable
fun mainScreen(navController: NavController) : List<Int> {3
    var isAllocationCompleted by remember { mutableStateOf(false) }
    var riskFreeRate by remember { mutableStateOf("0.00") }
    var listEmpty = listOf<Int>()
    var listA by remember {
        mutableStateOf(listEmpty)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F6F8)
    ) {
        Column {
            Top("MVO 자산배분")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .verticalScroll(state = ScrollState(-1))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(size = 24.dp)
                        )
                        .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
//                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        listA = DropdownList()
                        TargetReturn()
                        Dropdown2()
//                                Month()
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    modifier = Modifier
                        .width(265.dp)
                        .height(100.dp)
                        .padding(top = 20.dp, bottom = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =if (isAllocationCompleted) Color(0xFF00BADA) else Color.White,
                        contentColor = Color(0xFF00BADA)
                    ),
                    onClick = {
                        isAllocationCompleted = true

                    }) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize(),
                        text = if (isAllocationCompleted) "자산배분 완료" else "자산배분 시작",
                        style = TextStyle(
                            fontSize = 20.sp
                        ),
                        fontWeight = FontWeight(600),
                        color = if (isAllocationCompleted) Color.White else Color(0xFF00BADA),
                    )
                }

            }
            RowIcon(navController)
        }
    }
    return listA
}


@Composable
fun testScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F6F8)
    ) {
        Column {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .verticalScroll(state = ScrollState(-1))
            ) {
                Top("백테스팅")
                Text(text = "프리미엄버전입니다.")
            }
            RowIcon(navController)
        }
    }
}

@Composable
fun CommuScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F6F8)
    ) {
        Column {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .verticalScroll(state = ScrollState(-1))
            ) {
                Top("커뮤니티")
                Text(text = "프리미엄버전입니다.")
            }
            RowIcon(navController)
        }
    }
}


@Composable
fun RowIcon(navController: NavController) {
    Box(
        Modifier
            .shadow(
                elevation = 16.dp,
                spotColor = Color(0xFFE8EBEF),
                ambientColor = Color(0xFFE8EBEF)
            )
            .fillMaxWidth()
            .height(140.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 24.dp)
            )
//            .padding(start = 22.dp, top = 8.dp, end = 22.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon1(navController)
            Icon2(navController)
            Icon3(navController)
            Icon4(navController)
        }
    }
}

@Composable
fun Icon1(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("first")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .width(90.dp)
            .height(100.dp)
            .background(color = Color.Transparent)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier
                    .padding(0.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .background(color = Color.Transparent),
                painter = painterResource(id = R.drawable.mvo),
                contentDescription = "자산배분",
                tint = Color(0xFFFF00BADA)
            )
        }
    }
}


@Composable
fun Icon2(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("second")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .width(90.dp)
            .height(100.dp)
            .background(color = Color.Transparent)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .background(color = Color.Transparent),
                painter = painterResource(id = R.drawable.back),
                contentDescription = "백테스팅",
                tint = Color(0xFFFF00BADA)
            )
        }
    }
}

@Composable
fun Icon3(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("third")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .width(90.dp)
            .height(100.dp)
            .background(color = Color.Transparent)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier
                    .padding(0.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .background(color = Color.Transparent),
                painter = painterResource(id = R.drawable.pot),
                contentDescription = "포트폴리오",
                tint = Color(0xFFFF00BADA)
            )
        }
    }
}

@Composable
fun Icon4(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("forth")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .width(90.dp)
            .height(100.dp)
            .background(color = Color.Transparent)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier
                    .padding(0.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .background(color = Color.Transparent),
                painter = painterResource(id = R.drawable.messages),
                contentDescription = "커뮤니티",
                tint = Color(0xFFFF00BADA)
            )
        }
    }
}


@Composable
fun Top(name: String) {
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            Image(
                modifier = Modifier
                    .padding(0.dp)
                    .width(22.dp)
                    .height(22.dp),
                painter = painterResource(id = R.drawable.logoc),
                contentDescription = "image description",
                contentScale = ContentScale.None
            )
            Text(
                modifier = Modifier
                    .width(100.dp)
                    .height(23.dp),
                text = "Mytech",
                style = TextStyle(
                    fontSize = 18.32.sp
                ),
                fontWeight = FontWeight(900),
                color = Color(0xFF00BADA)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .width(200.dp)
                    .height(29.dp),
                text = name,
                style = TextStyle(
                    fontSize = 24.sp,
                ),
                fontWeight = FontWeight(600),
                color = Color(0xFF131313)
            )
        }
    }
}

//@Composable
//fun SplashScreen(navigateToMain: () -> Unit) {
//    LaunchedEffect(key1 = true) {
//        delay(1000) // 1.5초 대기
//        navigateToMain.invoke() // 네비게이션 화면으로 전환
//    }
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp)
//        .background(color = Color(0xFF00BADA)),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.logo), // 로고 이미지 리소스
//                contentDescription = "Logo",
//                modifier = Modifier
//                    .scale(4f) // 로고 크기 조절
//            )
//            Spacer(modifier = Modifier.height(25.dp)) // 로고와 텍스트 사이의 간격 조정
//            Text(
//                text = "Mytech",
//                style = TextStyle(
//                    fontSize = 36.sp,
//                    fontWeight = FontWeight(900),
//                    color = Color.White,
//                )
//            )
//        }
//    }
//}





package com.example.my

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my.ui.theme.MyTheme
import kotlin.math.min


class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
            }
        }
    }
}

@Composable
fun ETF(text: String, profit: String, color: Long) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(298.dp)
            .height(43.dp)
            .padding(top = 12.dp, bottom = 12.dp, start = 30.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = Color(color),
            )
        )
        Text(
            text = profit,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(color),
            )
        )
    }
}

@Composable
fun Result(text: String, profit: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight()
            .padding(top = 12.dp, bottom = 12.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF333333),
            )
        )
        Text(
            text = profit,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF333333),
            )
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetReturn() {
    var targetReturn by remember { mutableStateOf("0.00") }
    var savedTargetReturn by remember { mutableStateOf(0.0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 12.dp))
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "연간목표수익률(%)",
                    style = TextStyle(
                        fontSize = 16.sp,
                    ),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF404041),
                )
                TextField(
                    value = targetReturn,
                    onValueChange = {
                        targetReturn = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(
                        color = Color(0xFF00BADA),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
//                        .padding(2.dp)
                        .background(color = Color.Transparent)
                        .size(95.dp, 55.dp)
                )
                if (targetReturn.toDoubleOrNull() != null) {
                    savedTargetReturn = targetReturn.toDouble()
                }
            }
        }
    }
}

@Composable
fun Dropdown2() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var dropdown by remember { mutableStateOf(false) }
        var text by remember { mutableStateOf("목적함수옵션을 추가해주세요.") }
        DropdownMenu(
            expanded = dropdown, onDismissRequest = { dropdown = false }) {
            DropdownMenuItem(text = { Text("샤프비율 최대화") }, onClick = { text = "샤프비율 최대화" })
            DropdownMenuItem(text = { Text("리스크 최소화") }, onClick = { text = "리스크 최소화" })
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
                .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 12.dp))
                .padding(start = 20.dp, top = 25.dp, end = 20.dp, bottom = 25.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 16.sp,
                    ),
                    fontWeight = FontWeight(500),
                    color = Color.Black,
                )
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00BADA),
                        contentColor = Color.Black
                    ),
                    onClick = {
                        dropdown = true
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownList(): List<Int> {
    var selectedAssetNames by remember { mutableStateOf(listOf<StockCountry>()) }
    val selectedIndices = selectedAssetNames.map { it.index }
//    var selectedIndices by remember { mutableStateOf(listOf<Int>()) }
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(
                start = 20.dp,
                top = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.Top
            ),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = "자산목록")
            Dropdown(selectedAssetNames) { selectedAssetNames = it }
            Dropdown(selectedAssetNames) { selectedAssetNames = it }
            Dropdown(selectedAssetNames) { selectedAssetNames = it }
            Dropdown(selectedAssetNames) { selectedAssetNames = it }
            Dropdown(selectedAssetNames) { selectedAssetNames = it }
//            Text("Selected Assets: ${selectedAssetNames.joinToString(", ")}")
//            Text("Selected Assets: ${selectedAssetNames.map { it.index }}")
        }
    }
    return selectedIndices
}

enum class StockCountry(val display: String, val index: Int) {
    Korea("주식-한국", 0),
    China("주식-중국", 1),
    Japan("주식-일본", 2),
    SouthAmerica("주식-남아메리카", 3),
    America("주식-미국", 4),
    AmericaTech("주식-미국기술", 5),
    Europe("주식-유럽", 6),
    KoLong("채권-한국 국고채 중기", 7),
    KoMid("채권-한국 국고채 장기", 8),
    Deb("채권-한국 회사채 중기", 9),
    Bond("채권-선진국 하이일드", 10),
    Oil("대체투자-원유", 11),
    Gold("대체투자-금", 12),
    Agri("대체투자-농산물", 13),
    Copper("대체투자-구리", 14),
    AlterAmeri("대체투자-미국부동산", 15),
}

@Composable
fun Dropdown(
    selectedAssets: List<StockCountry>,
    onSelectedAssetsChanged: (List<StockCountry>) -> Unit
) {
    var selectedItem by remember { mutableStateOf<StockCountry?>(null) }
    var dropdown by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = dropdown, onDismissRequest = { dropdown = false }
    ) {
        for (item in StockCountry.values()) {
            DropdownMenuItem(
                text = { Text(item.display) },
                onClick = {
                    selectedItem = item
                    dropdown = false

                    if (!selectedAssets.contains(item)) {
                        val updatedList = selectedAssets + item
                        onSelectedAssetsChanged(updatedList)
                    }
                }
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(start = 20.dp, top = 25.dp, end = 20.dp, bottom = 25.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .horizontalScroll(state = ScrollState(1)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selectedItem?.display ?: "자산을 추가해주세요",
                style = TextStyle(
                    fontSize = 16.sp,
                ),
                fontWeight = FontWeight(500),
                color = Color.Black,
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BADA),
                    contentColor = Color.Black
                ),
                onClick = {
                    dropdown = true

                },
            ) {
                Icon(painter = painterResource(id = R.drawable.arrow), contentDescription = null)
            }
        }
    }
}
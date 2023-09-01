import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.my.ETF
import com.example.my.PieSlice
import com.example.my.Result
import com.example.my.RowIcon
import com.example.my.Top
import kotlin.math.min
import kotlin.random.Random

private val assetNames = listOf(
    "주식-한국", "주식-중국", "주식-일본", "주식-남아메리카", "주식-미국",
    "주식-미국기술", "주식-유럽", "채권-한국 국고채 중기", "채권-한국 국고채 장기",
    "채권-한국 회사채 중기", "채권-선진국 하이일드", "대체투자-원유",
    "대체투자-금", "대체투자-농산물", "대체투자-구리", "대체투자-미국 부동산"
)

private val assetReturns = doubleArrayOf(
    0.0562, 0.022, 0.0836, -0.0293, 0.1274, 0.2163, 0.0459, 0.004,
    0.0128, 0.0008, 0.0305, -0.1487, 0.0328, -0.0363, 0.0177, 0.0894
)

// 개별 자산의 예상 수익률
private val covarianceMatrix = arrayOf(
    doubleArrayOf(
        0.008738461,
        0.00384452,
        0.003464117,
        0.006758192,
        0.006175747,
        0.004565417,
        0.006572445,
        9.33E-05,
        5.64E-05,
        0.0001058,
        0.002238156,
        0.009943959,
        0.000926113,
        0.001597853,
        0.005055308,
        0.005351194
    ),
    doubleArrayOf(
        0.00384452,
        0.011048909,
        0.002694245,
        0.00448149,
        0.003736577,
        0.00286704,
        0.003557938,
        -9.58E-05,
        -0.000387536,
        2.06E-05,
        0.001119124,
        0.006301352,
        0.000114782,
        -0.0003993,
        0.003336427,
        0.002072051
    ),
    doubleArrayOf(
        0.003464117,
        0.002694245,
        0.004494761,
        0.003402805,
        0.004026714,
        0.004539209,
        0.004670744,
        3.34E-05,
        -7.83E-05,
        6.03E-05,
        0.001158197,
        0.005935124,
        -0.001276011,
        -0.000484629,
        0.001453739,
        0.002448121
    ),
    doubleArrayOf(
        0.006758192,
        0.00448149,
        0.003402805,
        0.021416209,
        0.006442076,
        0.004104901,
        0.007423587,
        -0.000160889,
        -0.000489357,
        4.87E-05,
        0.002970499,
        0.018693212,
        0.001463048,
        0.003491022,
        0.006395555,
        0.004725569
    ),
    doubleArrayOf(
        0.006175747,
        0.003736577,
        0.004026714,
        0.006442076,
        0.008034112,
        0.00710217,
        0.007310397,
        8.70E-05,
        0.000123099,
        0.000114855,
        0.002393118,
        0.010144455,
        -9.39E-05,
        0.000637558,
        0.005358965,
        0.006406969
    ),
    doubleArrayOf(
        0.004565417,
        0.00286704,
        0.004539209,
        0.004104901,
        0.00710217,
        0.009336452,
        0.005934842,
        9.25E-05,
        0.000137364,
        0.000164255,
        0.001775285,
        0.00624569,
        -0.001282708,
        -0.001000256,
        0.002407328,
        0.004968652
    ),
    doubleArrayOf(
        0.006572445,
        0.003557938,
        0.004670744,
        0.007423587,
        0.007310397,
        0.005934842,
        0.010356867,
        7.12E-05,
        5.90E-05,
        0.000131001,
        0.002319174,
        0.011107302,
        -0.001164794,
        -9.65E-05,
        0.00409263,
        0.006168886
    ),
    doubleArrayOf(
        9.33E-05,
        -9.58E-05,
        3.34E-05,
        -0.000160889,
        8.70E-05,
        9.25E-05,
        7.12E-05,
        9.20E-05,
        0.000225409,
        7.27E-05,
        7.02E-05,
        -0.000306518,
        0.00015688,
        -0.000205465,
        -3.48E-05,
        0.000206092
    ),
    doubleArrayOf(
        5.64E-05,
        -0.000387536,
        -7.83E-05,
        -0.000489357,
        0.000123099,
        0.000137364,
        5.90E-05,
        0.000225409,
        0.000778955,
        0.000162759,
        0.000134302,
        -0.000743511,
        0.000526815,
        -0.00082402,
        -0.00037107,
        0.000735496
    ),
    doubleArrayOf(
        0.0001058,
        2.06E-05,
        6.03E-05,
        4.87E-05,
        0.000114855,
        0.000164255,
        0.000131001,
        7.27E-05,
        0.000162759,
        8.66E-05,
        8.22E-05,
        6.85E-06,
        7.34E-05,
        -0.000183537,
        8.66E-06,
        0.000192691
    ),
    doubleArrayOf(
        0.002238156,
        0.001119124,
        0.001158197,
        0.002970499,
        0.002393118,
        0.001775285,
        0.002319174,
        7.02E-05,
        0.000134302,
        8.22E-05,
        0.001182356,
        0.00504377,
        0.000267262,
        0.000585722,
        0.001953873,
        0.002202809
    ),
    doubleArrayOf(
        0.009943959,
        0.006301352,
        0.005935124,
        0.009943959,
        0.006301352,
        0.005935124,
        0.018693212,
        0.010144455,
        0.00624569,
        0.011107302,
        -0.000306518,
        -0.000743511,
        6.85E-06,
        0.00504377,
        0.051488026,
        -0.000360133,
        0.00456291,
        0.011240255,
        0.007907237
    ),
    doubleArrayOf(
        0.000926113,
        0.000114782,
        -0.001276011,
        0.001463048,
        -9.39E-05,
        -0.001282708,
        -0.001164794,
        0.00015688,
        0.000526815,
        7.34E-05,
        0.000267262,
        -0.000360133,
        0.005743309,
        0.00070385,
        0.001613705,
        0.000823536
    ),
    doubleArrayOf(
        0.001597853,
        -0.0003993,
        -0.000484629,
        0.003491022,
        0.000637558,
        -0.001000256,
        -9.65E-05,
        -0.000205465,
        -0.00082402,
        -0.000183537,
        0.000585722,
        0.00456291,
        0.00070385,
        0.008619532,
        0.003059693,
        0.000577976
    ),
    doubleArrayOf(
        0.005055308,
        0.003336427,
        0.001453739,
        0.006395555,
        0.005358965,
        0.002407328,
        0.00409263,
        -3.48E-05,
        -0.00037107,
        8.66E-06,
        0.001953873,
        0.011240255,
        0.001613705,
        0.003059693,
        0.012949686,
        0.003606747
    ),
    doubleArrayOf(
        0.005351194,
        0.002072051,
        0.002448121,
        0.004725569,
        0.006406969,
        0.004968652,
        0.006168886,
        0.000206092,
        0.000735496,
        0.000192691,
        0.002202809,
        0.007907237,
        0.000823536,
        0.000577976,
        0.003606747,
        0.00859802
    )

) // 공분산 행렬

@Composable
fun PortfolioScreen(navController: NavController, listA: List<Int>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F6F8)
    ) {
        Column {
            Top(name = "포트폴리오")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .verticalScroll(state = ScrollState(-1))
            ) {
                val colors = listOf(
                    0xFF00BADA,  // 컬러 1
                    0xFF00849B,  // 컬러 2
                    0xFFF75462,  // 컬러 3
                    0xFF325789,  // 컬러 4
                    0xFF7137D1   // 컬러 5
                )

                val best = remember(listA) { calcData(listA) }

                val data = mutableListOf<PieSlice>() // 빈 MutableList 생성

                for (i in listA.indices) {
                    val assetName = assetNames[listA[i]]
                    val weightPercentage =
                        (best.weightsForSharpe[i] * 100).roundToTwoDecimalPlaces()
                    val color = colors[i % colors.size]

                    val pieSlice =
                        PieSlice(assetName, weightPercentage.toFloat(), color = Color(color))
                    data.add(pieSlice)
                }
                PieChart(data)
                Spacer(modifier = Modifier.height(10.dp))
                CalculateAll(colors, listA, best)
                Spacer(modifier = Modifier.height(6.dp))
                CalculateAll2(colors, listA, best)

            }
            RowIcon(navController)
        }

    }
}

@Composable
fun PieChart(data: List<PieSlice>) {
    Canvas(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .fillMaxWidth(0.95f)
            .height(300.dp)
            .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = min(center.x, center.y)
        var startAngle = 0f
        val totalValue = data.sumBy { it.value.toInt() }
        data.forEach { slice ->
            val sweepAngle = (slice.value / totalValue) * 360f
            val arcColor = slice.color
            drawArc(
                color = arcColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            startAngle += sweepAngle
        }
    }
}

data class Best(
    val sharpeRatio: Double,
    val risk: Double,
    val weightsForSharpe: DoubleArray,
    val weightsForRisk: DoubleArray,
)

fun calcData(selectedAssets: List<Int>): Best {
    val numSelectedAssets = selectedAssets.size
    val numPortfolios = 100000
    val riskFreeRate = 0.02 //고정값
    val targetReturn = 0.01 //목표수익률

    var bestSharpeRatio = Double.MIN_VALUE
    var bestRisk = Double.MAX_VALUE
    var bestWeightsForSharpe = DoubleArray(numSelectedAssets)
    var bestWeightsForRisk = DoubleArray(numSelectedAssets)

    val history = mutableListOf<Triple<DoubleArray, Double, Double>>()

    repeat(numPortfolios) {
        val weights = generateRandomWeights(numSelectedAssets)
        val portfolioReturn = calculatePortfolioReturn(assetReturns, selectedAssets, weights)
        val portfolioVariance =
            calculatePortfolioVariance(covarianceMatrix, selectedAssets, weights)
        val sharpeRatio = calculateSharpeRatio(portfolioReturn, portfolioVariance)
        val risk = portfolioVariance.sqrt()

        history.add(Triple(weights, portfolioReturn, sharpeRatio))

        if (portfolioReturn >= targetReturn && sharpeRatio > bestSharpeRatio) {
            bestSharpeRatio = sharpeRatio
            bestWeightsForSharpe = weights.copyOf()
        }

        if (portfolioReturn >= targetReturn && risk < bestRisk) {
            bestRisk = risk
            bestWeightsForRisk = weights.copyOf()
        }

    }
    return Best(bestSharpeRatio, bestRisk, bestWeightsForSharpe, bestWeightsForRisk)
}

@Composable
fun CalculateAll(colors: List<Long>, selectedAssets: List<Int>, best: Best) {
    Spacer(modifier = Modifier.height(10.dp))
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
        Column {
            Text(
                text = "상세정보 <샤프비율최대화>",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF1E1E1E),
                )
            )
            Result(
                text = "기대수익률 (%)",
                profit = "${
                    (calculatePortfolioReturn(
                        assetReturns,
                        selectedAssets,
                        best.weightsForSharpe
                    ) * 100).roundToTwoDecimalPlaces()
                }"
            )
            Result(
                text = "변동성 (%)",
                profit = "${
                    (calculatePortfolioVariance(
                        covarianceMatrix,
                        selectedAssets,
                        best.weightsForSharpe
                    ) * 100).roundToTwoDecimalPlaces()
                }"
            )
            Result(text = "샤프비율", profit = "${best.sharpeRatio.roundToTwoDecimalPlaces()}")
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(320.dp)
                    .wrapContentHeight()
                    .padding(top = 12.dp, bottom = 12.dp)
            ) {
                Text(
                    text = "편입종목(ETF)",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF333333),
                    )
                )
            }
            for (i in selectedAssets.indices) {
                ETF(
                    "${assetNames[selectedAssets[i]]}",
                    "${(best.weightsForSharpe[i] * 100).roundToTwoDecimalPlaces()}",
                    colors[i % colors.size]
                )
            }
        }
    }
}



@Composable
fun CalculateAll2(colors: List<Long>, selectedAssets: List<Int>, best: Best) {
    Spacer(modifier = Modifier.height(10.dp))
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
        Column {
            Text(
                text = "상세정보 <리스크최소화>",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF1E1E1E),
                )
            )
            Result(
                text = "기대수익률 (%)",
                profit = "${
                    (calculatePortfolioReturn(assetReturns, selectedAssets, best.weightsForRisk)
                     * 100).roundToTwoDecimalPlaces()
                }"
            )
            Result(
                text = "변동성 (%)",
                profit = "${
                    (calculatePortfolioVariance(covarianceMatrix, selectedAssets, best.weightsForRisk)
                     * 100).roundToTwoDecimalPlaces()
                }"
            )
            Result(text = "샤프비율", profit = "${(calculateSharpeRatio(calculatePortfolioReturn(assetReturns, selectedAssets, best.weightsForRisk), calculatePortfolioVariance(covarianceMatrix, selectedAssets, best.weightsForRisk))
                    ).roundToTwoDecimalPlaces()}")
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(320.dp)
                    .wrapContentHeight()
                    .padding(top = 12.dp, bottom = 12.dp)
            ) {
                Text(
                    text = "편입종목(ETF)",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF333333),
                    )
                )
            }
            for (i in selectedAssets.indices) {
                ETF(
                    "${assetNames[selectedAssets[i]]}",
                    "${(best.weightsForRisk[i] * 100).roundToTwoDecimalPlaces()}",
                    colors[i % colors.size]
                )
            }
        }
    }
}



fun generateRandomWeights(numSelectedAssets: Int): DoubleArray {
    val weights = DoubleArray(numSelectedAssets) { Random.nextDouble() }
    val sum = weights.sum()
    for (i in weights.indices) {
        weights[i] /= sum
    }
    return weights
}

fun calculatePortfolioReturn(
    assetReturns: DoubleArray,
    selectedAssets: List<Int>,
    assetWeights: DoubleArray
): Double {
    var portfolioReturn = 0.0
    for (i in selectedAssets.indices) {
        portfolioReturn += assetReturns[selectedAssets[i]] * assetWeights[i]
    }
    return portfolioReturn
}

fun calculatePortfolioVariance(
    covarianceMatrix: Array<DoubleArray>,
    selectedAssets: List<Int>,
    assetWeights: DoubleArray
): Double {
    val numSelectedAssets = selectedAssets.size
    var portfolioVariance = 0.0
    for (i in 0 until numSelectedAssets) {
        for (j in 0 until numSelectedAssets) {
            val assetIndexI = selectedAssets[i]
            val assetIndexJ = selectedAssets[j]
            portfolioVariance += assetWeights[i] * assetWeights[j] * covarianceMatrix[assetIndexI][assetIndexJ]
        }
    }
    return portfolioVariance
}

fun calculateSharpeRatio(portfolioReturn: Double, portfolioVariance: Double): Double {
    val riskFreeRate = 0.02
    val sharpeRatio = (portfolioReturn - riskFreeRate) / portfolioVariance
    return sharpeRatio
}

fun Double.sqrt() = kotlin.math.sqrt(this)

fun Double.roundToTwoDecimalPlaces(): Double {
    return String.format("%.2f", this).toDouble()
}

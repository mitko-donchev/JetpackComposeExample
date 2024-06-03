package com.mitko.jetpackcomposeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    TopRow()
                    SecondRow()
                    ThirdRow(scope, snackbarHostState)
                }
            }
        }
    }
}

@Composable
fun TopRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val painter = painterResource(id = R.drawable.dogy)
        val description = "Pit-bull good dog"
        val title = "Pit-bull good dog"
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 32.dp)
        ) {
            ImageCard(
                painter = painter,
                contentDescription = description,
                title = title
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 32.dp)
        ) {
            ColorBox()
        }
    }
}

@Composable
fun ThirdRow(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            MoveInsideTheBox()
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            SnackBarBox(scope, snackbarHostState)
        }
    }
}

@Composable
fun SecondRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            val list = (1..100).map { (1..999).random().toString() }
            ListOfItems(list)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            ConstraintLayoutBox()
        }
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}

@Composable
fun ColorBox(modifier: Modifier = Modifier) {
    val color = remember {
        mutableStateOf(Color.Red)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .background(color = color.value)
            .clickable {
                color.value = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            }) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Press here!",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = getContrastColor(color = color.value)
                    )
                )
            }
        }
    }
}

@Composable
fun MoveInsideTheBox(modifier: Modifier = Modifier) {
    var cardWidth by remember { mutableIntStateOf(0) }
    var cardHeight by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableFloatStateOf(0f) }
        var offsetY by remember { mutableFloatStateOf(0f) }

        Card(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .onSizeChanged { intSize ->
                    cardWidth = intSize.width
                    cardHeight = intSize.height
                },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Box(
                Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .background(Color.White)
            ) {
                Box(
                    Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .clip(shape = RoundedCornerShape(16.dp))
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                // Calculate maximum allowable offsets
                                val maxX = cardWidth.toFloat() - size.width
                                val maxY = cardHeight.toFloat() - size.height

                                // Update offsetX and offsetY, ensuring they stay within bounds
                                offsetX = min(maxX, max(0f, offsetX + dragAmount.x))
                                offsetY = min(maxY, max(0f, offsetY + dragAmount.y))
                            }
                        }
                        .background(Color.Black)
                        .clipToBounds(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Move me", color = Color.White, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun SnackBarBox(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .background(color = Color.White)
            .clickable {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Here is a snack!",
                        actionLabel = "Snack",
                        duration = SnackbarDuration.Short
                    )
                }
            }) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    text = "Click for a snack",
                    style = TextStyle(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
fun ListOfItems(list: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(
            Modifier
                .height(200.dp)
                .width(200.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(text = "That's a lazy column:", fontSize = 16.sp)
            }
            LazyColumn(modifier = Modifier.padding(vertical = 24.dp)) {
                items(list) {
                    Text(text = it, fontSize = 64.sp)
                }
            }
        }
    }
}

@Composable
fun ConstraintLayoutBox() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(
            Modifier
                .height(200.dp)
                .width(200.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            val constrains = ConstraintSet {
                val greenBox = createRefFor("greenbox")
                val redBox = createRefFor("redbox")

                constrain(greenBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.75f)
                    height = Dimension.percent(0.25f)
                }

                constrain(redBox) {
                    top.linkTo(greenBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.75f)
                    height = Dimension.percent(0.25f)
                }
                createVerticalChain(greenBox, redBox)
            }

            ConstraintLayout(constrains, modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(Color.Green)
                        .layoutId("greenbox"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Constraint", fontSize = 24.sp, color = Color.Black)
                }
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(Color.Red)
                        .layoutId("redbox"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Layout", fontSize = 24.sp, color = Color.Black)
                }
            }
        }
    }
}

// TODO - Utils
fun getContrastColor(color: Color): Color {
    // Calculate the luminance of the color
    val luminance = (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue)
    // Return black or white depending on the luminance
    return if (luminance > 0.5) Color.Black else Color.White
}
package com.example.drinklemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drinklemonade.ui.theme.DrinkLemonadeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrinkLemonadeTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "Lemonade",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            },
                            colors = TopAppBarDefaults.largeTopAppBarColors(
                                containerColor = colorResource(id = R.color.dark_yellow)
                            )
                        )
                    }
                ){ innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(innerPadding),
                        color = Color.White
                    ){
                        DrinkLemonadeApp()
                    }

                }

            }
        }
    }
}

@Preview
@Composable
fun DrinkLemonadeApp(){
    LemonadeWithImageAndText(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun LemonadeWithImageAndText(modifier: Modifier = Modifier) {

    var numbersOfTaps by remember {
        mutableIntStateOf(0)
    }

    var currentStep by remember {
        mutableIntStateOf(1)
    }


    val instruction =
        when(currentStep) {
            1 -> stringResource(id = R.string.select_lemon)
            2 -> stringResource(id = R.string.squeeze_lemon)
            3 -> stringResource(id = R.string.drink_lemon)
            else -> stringResource(id = R.string.start_again)
        }

    val imageResource =
        when(currentStep){
            1 -> painterResource(id = R.drawable.lemon_tree)
            2 -> painterResource(id = R.drawable.lemon_squeeze)
            3 -> painterResource(id = R.drawable.lemon_drink)
            else -> painterResource(id = R.drawable.lemon_restart)

        }

    val description =
        when (currentStep) {
            1 -> stringResource(id = R.string.description_1)
            2 -> stringResource(id = R.string.description_2)
            3 -> stringResource(id = R.string.description_3)
            else -> stringResource(id = R.string.description_4)
        }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                when(currentStep) {
                    1 -> {
                        currentStep = 2
                        numbersOfTaps = (2..4).random()
                    }

                    2-> {
                        numbersOfTaps--
                        if(numbersOfTaps == 0){
                            currentStep = 3
                        }
                    }

                    3 -> currentStep = 4

                    4 -> currentStep = 1
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xffc2ecd3)),
            shape = RoundedCornerShape(40.dp),
        ) {
            Image(
                painter = imageResource,
                contentDescription = description,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = instruction,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

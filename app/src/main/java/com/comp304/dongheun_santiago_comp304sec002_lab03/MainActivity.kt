package com.comp304.dongheun_santiago_comp304sec002_lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.comp304.dongheun_santiago_comp304sec002_lab03.navigation.AppNavigationContent
import com.comp304.dongheun_santiago_comp304sec002_lab03.navigation.DeviceFoldPosture
import com.comp304.dongheun_santiago_comp304sec002_lab03.navigation.NavigationType
import com.comp304.dongheun_santiago_comp304sec002_lab03.navigation.Screens
import com.comp304.dongheun_santiago_comp304sec002_lab03.navigation.isBookPosture
import com.comp304.dongheun_santiago_comp304sec002_lab03.navigation.isSeparating
import com.comp304.dongheun_santiago_comp304sec002_lab03.ui.theme.Dongheun_Santiago_COMP304Sec002_Lab03Theme
import com.comp304.dongheun_santiago_comp304sec002_lab03.viewmodel.WeatherViewModel
import com.comp304.dongheun_santiago_comp304sec002_lab03.views.WeatherScreen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deviceFoldingPostureFlow = WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DeviceFoldPosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DeviceFoldPosture.SeparatingPosture(
                            foldingFeature.bounds,
                            foldingFeature.orientation
                        )
                    else -> DeviceFoldPosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DeviceFoldPosture.NormalPosture
            )
        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            Dongheun_Santiago_COMP304Sec002_Lab03Theme {
//                applicationContext.deleteDatabase("Weather_Db")
                val navigationType: NavigationType
                when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> {
                        navigationType = NavigationType.BottomNavigation
                    }

                    WindowWidthSizeClass.Medium -> {
                        navigationType = NavigationType.NavigationRail
                    }

                    WindowWidthSizeClass.Expanded -> {
                        navigationType = NavigationType.NavigationRail
                    }
                    else -> {
                        navigationType = NavigationType.BottomNavigation
                    }
                }
                AppNavigationContent(
                    currentScreen = currentScreen,
                    navigationType = navigationType,
                    onScreenChanged = {
                        currentScreen = it
                    }
                )
            }
        }
    }
}

var currentScreen = Screens.WeatherScreen.route

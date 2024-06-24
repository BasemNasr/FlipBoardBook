@file:OptIn(ExperimentalFoundationApi::class)

package com.bn.flipboard

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bn.flipboard.data.newsPapers
import com.bn.flipboard.ui.FakeAppBar
import com.bn.flipboard.ui.HeadlineArticle
import com.bn.flipboard.ui.HeadlineArticleLandscape
import com.bn.flipboard.ui.flip.FlipPager
import com.bn.flipboard.ui.flip.FlipPagerOrientation
import com.bn.flipboard.ui.theme.FlipBoardAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}


@Composable
private fun App() {
    val isSystemDark = isSystemInDarkTheme()
    var darkMode by remember {
        mutableStateOf(isSystemDark)
    }

    val configuration = LocalConfiguration.current

    FlipBoardAnimationTheme(darkTheme = darkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                Modifier.navigationBarsPadding()
            ) {
                var orientation: FlipPagerOrientation by remember {
                    mutableStateOf(FlipPagerOrientation.Horizontal)
                }
                val state = rememberPagerState { newsPapers.size }
                FakeAppBar(
                    orientation = orientation,
                    darkMode = darkMode,
                    setOrientation = { if (!state.isScrollInProgress) orientation = it },
                    setDarkMode = { darkMode = it }
                )

                FlipPager(
                    state = state,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    orientation = orientation,
                ) { page ->
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                    ) {
                        when (configuration.orientation) {
                            Configuration.ORIENTATION_LANDSCAPE -> {
                                HeadlineArticleLandscape(
                                    modifier = Modifier.align(Alignment.Center),
                                    newsPaper = newsPapers[page],
                                )
                            }
                            else -> {
                                HeadlineArticle(
                                    modifier = Modifier.align(Alignment.Center),
                                    newsPaper = newsPapers[page],
                                )
                            }
                        }


                    }
                }
            }
        }
    }
}










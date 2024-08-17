package com.example.basickscodelab

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.media.RouteListingPreference
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight
import com.example.basickscodelab.ui.theme.BasicksCodelabTheme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicksCodelabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background)
                {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun expanded_text(textnumber: Int, modifier: Modifier = Modifier) {
    Text(
        text = if (textnumber == 0) "hello мир! манера крутит мир" else "не придумала",
        modifier = Modifier
            .padding(start = 5.dp, bottom = 5.dp)
    )
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
    } else {
        Greetings(onContinueClicked = {shouldShowOnboarding = true})
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit,
    names: MutableList<String> = mutableListOf("World", "Compose")
) {
    for (i in 0..50){
        names.add("$i")
    }
    Column {
        Column(modifier = modifier
            .padding(top = 4.dp, bottom = 5.dp)
            .verticalScroll(rememberScrollState())
            .weight(1f)  ) {
            for (name in names) {
                Greeting(name = name, textnumber = names.indexOf(name))
            }
        }

        Button(
            modifier = Modifier.padding(start = 10.dp, top = 5.dp),
            onClick = onContinueClicked
        ) {
            Text("back")
        }
    }
}


@Composable
private fun Greeting(name: String, textnumber: Int, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val botpad by animateDpAsState(
        if (expanded and (textnumber > 1)) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column (
            modifier = Modifier.padding(bottom = botpad)
        ){

            Row(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = "Hello")
                    Text(name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold)

                    )
                }
                ElevatedButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "show less" else "show more")
                }
            }
            if (expanded and (textnumber < 2)) {
                expanded_text(textnumber = textnumber)
            }
        }
    }
    println(expanded)
}


@Preview(showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark")
@Preview(showBackground = true,
    widthDp = 320)
@Composable
fun GreetingsPreview() {
    BasicksCodelabTheme {
        Greetings(onContinueClicked = {})
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicksCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable  // просмотр результата во время разработки
fun GreetingPreview() {
    BasicksCodelabTheme {
        MyApp()
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    BasicksCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}
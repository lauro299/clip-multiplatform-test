package test.multiplatform.project.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview
import mx.clip.db.createDB
import mx.clip.db.dataSources.ClipDataSourceImp
import mx.clip.test.sqldelight.mx.clip.db.CatalogItem
import mx.clip.test.sqldelight.mx.clip.db.HockeyPlayer
import test.multiplatform.project.androidApp.feature.items.itemsCatalogList
import test.multiplatform.project.androidApp.feature.items.viewItemsCatalog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}
@Composable
@Preview
fun MyApp(){
    val navController = rememberNavController()
    MaterialTheme(){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Hello there!!")
            Button(onClick = {
            }, content = { Text(text = "Boton!!!!") })
        }
        NavHost(navController = navController, startDestination = Screen.Initial.route){
            composable(Screen.Initial.route){
                viewItemsCatalog(navController)
            }
        }
    }
}

sealed class Screen(val route:String){
    object Initial:Screen("initial")
    object CatalogList: Screen("catalogList")
}
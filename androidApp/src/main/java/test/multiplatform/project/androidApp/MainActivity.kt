package test.multiplatform.project.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import kotlinx.coroutines.flow.collect
import mx.clip.db.createDB
import mx.clip.db.dataSources.ClipDataSourceImp
import mx.clip.test.sqldelight.mx.clip.db.HockeyPlayer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
        createDB()?.let {
            ClipDataSourceImp(it)
        }?.let {
            it.insertPlayer(HockeyPlayer(10, "layer"))
            it.getPlayers()?.let {
                /*it.collect{
                    println(it)
                }*/
            }
        }
    }
}
@Composable
fun MyApp(){
    MaterialTheme {
        Text(text = "Hello there!!")
    }
}
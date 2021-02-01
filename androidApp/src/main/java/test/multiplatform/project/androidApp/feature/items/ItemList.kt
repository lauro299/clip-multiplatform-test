package test.multiplatform.project.androidApp.feature.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.collect
import mx.clip.test.sqldelight.mx.clip.db.CatalogItem
import test.multiplatform.project.androidApp.R

@Composable
fun itemCatalog(item:CatalogItem) {
    Row() {
        val imageModifier = Modifier.preferredSize(46.dp).clip(shape = CircleShape)
        val image = vectorResource(R.drawable.ic_atoms_assets_illustrations_success)
        Image(asset=image, modifier = imageModifier, contentScale = ContentScale.Crop)
        Column(modifier = Modifier.padding(start = 8.dp).gravity(Alignment.CenterVertically)) {
            Text(text = item.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.h6)
        }
    }
}

@Composable
fun viewItemsCatalog(navController: NavController, catalogViewModel: CatalogItemViewModel){
    catalogViewModel.list?.collectAsState(initial = emptyList())
            ?.value?.let {
                itemsCatalogList(items = it, navController = navController)
            }
}

@Composable
fun itemsCatalogList(items:List<CatalogItem>?, navController: NavController){
    //println("catalogs:$items")
    if(items?.isEmpty() == true){
        Text(text = "Lista vacia")
    } else {
        items?.let {
            LazyColumnFor(items = it) {
                itemCatalog(item = it)
                Divider()
            }
        }
    }
}

@Composable
fun floatButton(catalogItemViewModel: CatalogItemViewModel){
    MaterialTheme() {
        Column {
            val openDialog = remember { mutableStateOf(false) }
            FloatingActionButton(
                    onClick = {
                              openDialog.value = true
                    },
                    icon = {vectorResource(id = R.drawable.ic_atoms_assets_illustrations_success)})
            if(openDialog.value){
                val name = remember { mutableStateOf(TextFieldValue()) }
                val price = remember { mutableStateOf(TextFieldValue()) }
                AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = { Text(text = "Add Icon") },
                        text = {
                            Column(modifier = Modifier.fillMaxWidth()){
                                Text(text = "Name")
                                TextField(value = name.value , onValueChange = { name.value = it})
                                Text(text = "Price")
                                TextField(value = price.value, onValueChange = { price.value = it })
                            }
                               },
                        confirmButton = {
                            Button(onClick = {
                                openDialog.value = false
                                catalogItemViewModel.add(name.value.text, price.value.text.toDouble())
                            }) {
                                Text(text = "Aceptar")
                            }
                        })
            }
        }
    }
}

@Composable
@Preview
fun CatalogItemForm(){
}
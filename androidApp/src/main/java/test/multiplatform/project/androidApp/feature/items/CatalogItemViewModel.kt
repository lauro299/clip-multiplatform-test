package test.multiplatform.project.androidApp.feature.items

import androidx.lifecycle.ViewModel
import mx.clip.db.createDB
import mx.clip.db.dataSources.ClipItemCatalogDataSourceImp
import mx.clip.test.sqldelight.mx.clip.db.CatalogItem

class CatalogItemViewModel: ViewModel(){
    private val clipItemCatalogDS = createDB()
        ?.let {
            ClipItemCatalogDataSourceImp(it)
        }

    val list = clipItemCatalogDS?.getItems()

    fun add(text: String, toDouble: Double) {
        clipItemCatalogDS?.insert(CatalogItem(0, text, toDouble))
    }

}
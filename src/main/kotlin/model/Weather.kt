package com.marcfearby.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class Weather: JsonModel {

    val descProperty = SimpleStringProperty()
    var description: String? by descProperty

    val iconProperty = SimpleStringProperty()
    var icon: String? by iconProperty

    override fun updateModel(json: JsonObject) {
        super.updateModel(json)

        with(json) {
            description = getString("description")
            icon = getString("icon")
        }
    }

}
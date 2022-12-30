package com.marcfearby.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class Weather: JsonModel {

    val descProperty = SimpleStringProperty()
    var description: String? by descProperty

    val codeProperty = SimpleIntegerProperty()
    var code: Int by codeProperty

    override fun updateModel(json: JsonObject) {
        super.updateModel(json)

        with(json) {
            description = getString("description")
            code = getInt("code")
        }
    }

}
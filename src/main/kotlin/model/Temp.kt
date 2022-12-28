package com.marcfearby.model

import javafx.beans.property.SimpleFloatProperty
import tornadofx.*
import javax.json.JsonObject

class Temp: JsonModel {

    val minTempProperty = SimpleFloatProperty()
    var minTemp: Float by minTempProperty

    val maxTempProperty = SimpleFloatProperty()
    var maxTemp: Float by maxTempProperty

    override fun updateModel(json: JsonObject) {
        super.updateModel(json)
        with(json) {
            minTemp = getFloat("min_temp")
            maxTemp = getFloat("max_temp")
        }
    }

}
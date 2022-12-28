package com.marcfearby.model

import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import javax.json.JsonObject

class Forecast: JsonModel {

    // UNIX epoch timestamp
    val tsProperty = SimpleIntegerProperty()
    var ts by tsProperty

    val minTempProperty = SimpleFloatProperty()
    var minTemp: Float by minTempProperty

    val maxTempProperty = SimpleFloatProperty()
    var maxTemp: Float by maxTempProperty

    val rhProperty = SimpleIntegerProperty()
    var humidity by rhProperty

    val weatherProperty = SimpleObjectProperty<Weather>()
    var weather by weatherProperty

    override fun updateModel(json: JsonObject) {
        super.updateModel(json)

        with(json) {
            ts = getInt("ts")
            weather = getJsonObject("weather").toModel()
            humidity = getInt("rh")
            minTemp = getFloat("min_temp")
            maxTemp = getFloat("max_temp")
        }
    }

}
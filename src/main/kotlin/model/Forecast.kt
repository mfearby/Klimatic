package com.marcfearby.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import javax.json.JsonObject

class Forecast: JsonModel {

    // UNIX epoch timestamp
    val tsProperty = SimpleIntegerProperty()
    var ts by tsProperty

    val minTempProperty = SimpleDoubleProperty()
    var minTemp: Double by minTempProperty

    val maxTempProperty = SimpleDoubleProperty()
    var maxTemp: Double by maxTempProperty

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
            minTemp = getDouble("min_temp")
            maxTemp = getDouble("max_temp")
        }
    }

}
package com.marcfearby.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class Forecast: JsonModel {

    // UNIX epoch timestamp
    val tsProperty = SimpleIntegerProperty()
    var ts by tsProperty

    val minTempProperty = SimpleStringProperty()
    var minTemp: String by minTempProperty

    val maxTempProperty = SimpleStringProperty()
    var maxTemp: String by maxTempProperty

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
//            minTemp = getString("min_temp")
//            maxTemp = getString("max_temp")
        }
    }

}
package com.marcfearby.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class Payload: JsonModel {

    private val cityProperty = SimpleStringProperty()
    var city: String by cityProperty

    private val forecastsProperty = SimpleListProperty<Forecast>()
    var forecasts: List<Forecast> by property(forecastsProperty)

    override fun updateModel(json: JsonObject) {
        super.updateModel(json)

        with(json) {
            city = getString("city_name")
            forecasts = getJsonArray("data").toModel()
        }
    }
}
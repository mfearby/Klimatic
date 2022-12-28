package com.marcfearby.model

import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

// https://api.weatherbit.io/v2.0/current?key=***********************&city=Port%20Macquarie&country=AU

// https://api.weatherbit.io/v2.0/forecast/daily?city=Port%20Macquarie,AU&key=************************

class City: JsonModel {

    val nameProperty = SimpleStringProperty()
    var name: String? by nameProperty

    override fun updateModel(json: JsonObject) {
        super.updateModel(json)
        with(json) {
            name = getString("city_name")
        }
    }

    override fun toString() = name ?: "No Name!"

}

class CityModel: ItemViewModel<City>() {
    val name: Property<String> = bind(City::nameProperty)
}
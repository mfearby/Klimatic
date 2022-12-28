package com.marcfearby.controller

import javafx.beans.property.Property
import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

// https://api.weatherbit.io/v2.0/current?key=***********************&city=Port%20Macquarie&country=AU

class City: JsonModel {

    val nameProperty = SimpleStringProperty()
    var name: String? by nameProperty

    val tempProperty = SimpleFloatProperty()
    var temp: Float by tempProperty

    override fun updateModel(json: JsonObject) {
        super.updateModel(json)
        with(json) {
            name = getString("city_name")
            temp = getFloat("temp")
        }
    }

    override fun toString() = name ?: "<No Name>"

}

class CityModel: ItemViewModel<City>() {
    val name: Property<String> = bind(City::nameProperty)
    val temp: Property<Float> = bind(City::tempProperty)
}
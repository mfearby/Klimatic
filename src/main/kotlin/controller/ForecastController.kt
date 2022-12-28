package com.marcfearby.controller

import com.marcfearby.model.CityModel
import com.marcfearby.model.Payload
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class ForecastController: Controller() {

    val selectedCity: CityModel by inject()
    var allWeather: ObservableList<Payload> = FXCollections.emptyObservableList()
    val api: Rest by inject()

    init {
        api.baseURI = "https://api.weatherbit.io/v2.0/forecast/daily"
    }

    fun getPayload(apiKey: String, cityName: String = selectedCity.name.value): Payload {
        println("Fetching forecast for '$cityName' using apiKey '$apiKey'")
        val response = api.get("?city=$cityName&key=$apiKey")
        val rootObject = response.one()
        val payload = rootObject.toModel<Payload>()
        return payload
    }

}
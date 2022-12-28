package com.marcfearby.controller

import com.marcfearby.model.CityModel
import com.marcfearby.model.Forecast
import com.marcfearby.model.Payload
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class ForecastController: Controller() {

    val selectedCity: CityModel by inject()
    var forecasts: ObservableList<Forecast> = FXCollections.emptyObservableList()
    private val api: Rest by inject()

    init {
        api.baseURI = "https://api.weatherbit.io/v2.0/forecast/daily"
    }

    fun getPayload(apiKey: String, cityName: String)
        = api.get("?city=$cityName&key=$apiKey")
            .one()
            .toModel<Payload>()

}
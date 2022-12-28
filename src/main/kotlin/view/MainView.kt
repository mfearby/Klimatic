package com.marcfearby.view

import com.marcfearby.controller.ForecastController
import javafx.scene.control.TextField
import tornadofx.*
import java.util.*

class MainView: View("Klimatic") {

    val forecastController: ForecastController by inject()
    var apiKey: TextField by singleAssign()
    var cityName: TextField by singleAssign()

    override val root = vbox {
        apiKey = textfield("Enter API Key")
        cityName = textfield("Enter City name")
        button("Get Forecast") {
            useMaxWidth = true
            action {
                val payload = forecastController.getPayload(apiKey.text, cityName.text)
                payload.forecasts.forEach {
                    val time = Date(it.ts * 1000L)
                    println("Forecast for ${time}: min ${it.minTemp}, max ${it.maxTemp}, ${it.weatherProperty.value.description}")
                }
            }
        }
    }
}
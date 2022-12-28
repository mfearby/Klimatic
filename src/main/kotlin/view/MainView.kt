package com.marcfearby.view

import com.marcfearby.controller.ForecastController
import javafx.scene.control.TextField
import tornadofx.*
import java.sql.Timestamp

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
                    val time = Timestamp(it.ts * 1000L)
                    println("Forecast for ${time.toLocalDateTime()}: ${it.weatherProperty.value.description}")
                }
            }
        }
    }
}
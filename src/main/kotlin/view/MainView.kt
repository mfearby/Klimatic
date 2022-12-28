package com.marcfearby.view

import com.marcfearby.controller.ForecastController
import javafx.geometry.Orientation.*
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
import tornadofx.*
import java.util.*

class MainView: View("Klimatic") {

    private val forecastController: ForecastController by inject()
    private var apiKey: TextField by singleAssign()

    override val root = borderpane {
        addClass(Styles.mainView)
        center = vbox {
            currentWeatherView()
        }
    }

    private fun VBox.currentWeatherView() = vbox {
        form {
            paddingAll = 20.0
            fieldset {

                field("Enter API Key", VERTICAL) {
                    apiKey = textfield("Enter API Key")
                }

                field("Enter City", VERTICAL) {
                    textfield(forecastController.selectedCity.name) {
                        validator {
                            if (it.isNullOrBlank()) error("City cannot be blank") else null
                        }
                    }
                    setOnKeyPressed {
                        if (it.code == KeyCode.ENTER) {
                            runAsync {
                                fetchPayload(forecastController.selectedCity.name.value, apiKey.text)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchPayload(city: String, key: String) {
        println("Fetching forecast for '$city' using apiKey '$key'")
        val payload = forecastController.getPayload(
            apiKey = apiKey.text,
            cityName = forecastController.selectedCity.name.value
        )
        payload.forecasts.forEach {
            val time = Date(it.ts * 1000L)
            println("Forecast for ${time}: min ${it.minTemp}, max ${it.maxTemp}, ${it.weatherProperty.value.description}")
        }
        forecastController.forecasts = payload.forecasts.toObservable()
    }
}
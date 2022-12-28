package com.marcfearby.view

import com.marcfearby.controller.ForecastController
import javafx.geometry.Orientation.*
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.VBox
import tornadofx.*
import java.text.SimpleDateFormat
import java.util.*

class MainView: View("Klimatic") {

    private val forecastController: ForecastController by inject()
    private var apiKey: TextField by singleAssign()
    var cityName: Label by singleAssign()
    var todayTemp: Label by singleAssign()

    override val root = borderpane {
        addClass(Styles.mainView)
        center = vbox {
            currentWeatherView()
            vbox {
                cityName = label()
                todayTemp = label()
            }
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
                            // will run only if the field is deemed valid (i.e., not empty!)
                            forecastController.selectedCity.commit {
                                val city = forecastController.selectedCity.name.value
                                runAsync {
                                    fetchPayload(city, apiKey.text)
                                } ui {
                                    val payload = forecastController.forecasts
                                    val today = payload.first()
                                    val date = SimpleDateFormat("EEE, d MMM yyyy").format(Date(today.ts * 1000L))
                                    cityName.text = "Forecast for $city on $date"
                                    todayTemp.text = "min: ${today.minTemp}°C, max: ${today.maxTemp}°C, ${today.weather.description}"
                                }
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
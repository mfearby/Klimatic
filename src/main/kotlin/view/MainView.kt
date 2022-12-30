package com.marcfearby.view

import com.marcfearby.controller.ForecastController
import com.marcfearby.model.Forecast
import javafx.geometry.Orientation.*
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color.GRAY
import javafx.scene.paint.Color.TRANSPARENT
import tornadofx.*
import java.text.SimpleDateFormat
import java.util.*

class MainView: View("Klimatic") {

    private val forecastController: ForecastController by inject()
    private var apiKey: TextField by singleAssign()
    private var cityName: Label by singleAssign()
    private var todayTemp: Label by singleAssign()
    private var todayIcon: Label by singleAssign()
    private var forecastView: DataGrid<Forecast> by singleAssign()
    private var sevenDayLabel: Label by singleAssign()
    private var divider: HBox by singleAssign()

    override val root = borderpane {
        addClass(Styles.mainView)
        center = vbox {
            currentWeatherView()
            vbox {
                addClass(Styles.contentWrapper)
                cityName = label {
                    addClass(Styles.forecastLabel)
                }
                todayTemp = label {
                    addClass(Styles.forecastLabel)
                }
                todayIcon = label()
                divider = hbox()
                sevenDayLabel = label()
//                forecastView = datagrid() // << Here be Dragons!
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
                                    updateUI(city)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(city: String) {
        val payload = forecastController.forecasts
        val today = payload.first()
        val date = SimpleDateFormat("EEE, d MMM yyyy").format(Date(today.ts * 1000L))
        cityName.text = "Forecast for $city on $date"
        todayTemp.text = "min: ${today.minTemp}°C, max: ${today.maxTemp}°C, ${today.weather.description}"

        val icon = getIcon(today.weather.code)
        todayIcon.graphic = imageview("/icons/$icon.png", lazyload = true) {
            fitHeight = 200.0
            fitWidth = 200.0
        }

        divider.style {
            borderColor += box(TRANSPARENT, TRANSPARENT, GRAY, TRANSPARENT)
        }

        sevenDayLabel.text = "7 Day Weather Forecast"
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

    private fun getIcon(code: Int): String = when(code) {
        in 200 .. 299 -> "tstorms"
        in 300 .. 399, 623 -> "flurries" // includes "drizzle" which has no icon
        in 500 .. 599 -> "rain"
        in 600 .. 622 -> "snow"
        in 741 .. 751 -> "fog"
        in 801 .. 802 -> "mostlysunny"
        in 803 .. 899 -> "clouds"
        else -> "clear"
    }
}
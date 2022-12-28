package com.marcfearby.view

import javafx.scene.paint.Color
import tornadofx.*

class Styles: Stylesheet() {
    companion object {
        val mainView by cssclass()
        val forecastLabel by cssclass()
    }

    init {
        mainView {
            backgroundColor += c("ccccff")
        }
        forecastLabel {
            fontSize = 20.px
            textFill = Color.BLUE
        }
    }
}
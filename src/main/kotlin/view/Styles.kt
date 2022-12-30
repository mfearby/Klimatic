package com.marcfearby.view

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class Styles: Stylesheet() {
    companion object {
        val mainView by cssclass()
        val forecastLabel by cssclass()
        val contentWrapper by cssclass()
    }

    init {
        mainView {
            backgroundColor += c("ccccff")
        }
        forecastLabel {
            fontSize = 20.px
            textFill = Color.BLUE
        }
        contentWrapper {
            alignment = Pos.CENTER
        }
    }
}
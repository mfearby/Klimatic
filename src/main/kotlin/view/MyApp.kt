package com.marcfearby.view

import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        with(stage) {
            width = 600.0
            height = 800.0
        }
        super.start(stage)
    }
}
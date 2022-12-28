package com.marcfearby.view

import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MainView::class) {
    override fun start(stage: Stage) {
        with(stage) {
            width = 250.0
            height = 300.0
        }
        super.start(stage)
    }
}
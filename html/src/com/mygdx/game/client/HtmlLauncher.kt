package com.mygdx.game.client

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.gwt.GwtApplication
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration
import com.mygdx.game.MyGdxGame

class HtmlLauncher : GwtApplication() {

    override fun getConfig(): GwtApplicationConfiguration {
        return GwtApplicationConfiguration(480, 320)
    }

    override fun createApplicationListener(): ApplicationListener {
        return MyGdxGame()
    }
}
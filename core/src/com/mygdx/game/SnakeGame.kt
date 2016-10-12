package com.mygdx.game

import com.badlogic.gdx.Game

class SnakeGame : Game() {
    override fun create() {
        setScreen(MyGdxGame(this))
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
    }
}

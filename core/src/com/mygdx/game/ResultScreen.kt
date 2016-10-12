package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.g2d.Sprite
import java.util.*

class ResultScreen(game: BallGame) : ScreenAdapter() {
    private var ball: Sprite
    internal val game: BallGame

    init {
        this.game = game

        // Ball
        val random = Random()
        val num = random.nextInt(game.assetManager.ballAtlas.size)
        ball = Sprite(game.assetManager.ballAtlas[num])
        ball.setSize(Ball.SIZE, Ball.SIZE)
        ball.setOrigin(Ball.SIZE / 2, Ball.SIZE / 2)
        ball.setPosition((Gdx.graphics.width - Ball.SIZE) / 2, (Gdx.graphics.height - Ball.SIZE) / 2)
    }

    override fun render(delta: Float) {
        game.batch.begin()
        ball.draw(game.batch)
        game.batch.end()
    }

    override fun dispose() {
        println("ResultScreen dispose")
    }
}
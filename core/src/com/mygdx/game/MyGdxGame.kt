package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

class MyGdxGame : ScreenAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var balls: List<TextureRegion>
    private lateinit var assetManager: AssetManager

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(balls[0], 100f, 100f)
        batch.end()
    }

    override fun show() {
        loadResource()
        batch = SpriteBatch()
        createBalls()
    }

    override fun dispose() {
        assetManager.dispose()
    }

    private fun loadResource() {
        assetManager = AssetManager()
        assetManager.load("balls.txt", TextureAtlas::class.java)
        assetManager.finishLoading()
    }

    private fun createBalls() {
        val atlas = assetManager.get("balls.txt", TextureAtlas::class.java)

        val ball_0000ff = atlas.findRegion("0000ff")
        val ball_00ff00 = atlas.findRegion("00ff00")
        val ball_00ffff = atlas.findRegion("00ffff")
        val ball_0300ff = atlas.findRegion("0300ff")
        val ball_3200ff = atlas.findRegion("3200ff")
        val ball_ff0000 = atlas.findRegion("ff0000")
        val ball_ff0064 = atlas.findRegion("ff0064")
        val ball_ffffff = atlas.findRegion("ffffff")

        balls = listOf<TextureRegion>(ball_0000ff,
                                      ball_00ff00,
                                      ball_00ffff,
                                      ball_0300ff,
                                      ball_3200ff,
                                      ball_ff0000,
                                      ball_ff0064,
                                      ball_ffffff)
    }
}

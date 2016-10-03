package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class MyGdxGame : ScreenAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var balls: List<TextureAtlas.AtlasRegion>
    private lateinit var assetManager: AssetManager
    private lateinit var ball: Sprite
    var screenWidth: Float = 0f
    var screenHeight: Float = 0f
    var ballWidth: Float = 0f
    var ballHeight: Float = 0f
    private lateinit var camera: OrthographicCamera

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        ball.draw(batch)
        batch.end()

        ball.translate(0.1f, 0f)
    }

    override fun show() {
        loadResource()

        screenWidth = Gdx.graphics.width.toFloat()
        screenHeight = Gdx.graphics.height.toFloat()
        camera = OrthographicCamera(screenWidth, screenHeight)
        camera.position.set(screenWidth / 2, screenHeight / 2, 0f)
        camera.update()

        batch = SpriteBatch()

        createBalls()

        ball = Sprite(balls[0])
        ball.setPosition(10f, 10f)
        ball.setScale(0.5f)

        ballWidth = ball.width
        ballHeight = ball.height
        ball.setBounds( camera.position.x - (ballWidth / 2), camera.position.y - (ballHeight / 2), ballWidth, ballHeight)

        Gdx.input.inputProcessor = object: InputAdapter() {
            override fun touchDown(screenX:Int, screenY:Int, pointer:Int, button:Int):Boolean {
                if (ball.boundingRectangle.contains(screenX.toFloat(), screenY.toFloat())) {
                    println("Ball Clicked")
                }
                return true
            }
        }
    }

    override fun dispose() {
        assetManager.dispose()
    }

    private fun loadResource() {
        assetManager = AssetManager()
        assetManager.load("balls.txt", TextureAtlas::class.java)
        assetManager.finishLoading()
    }

    private fun createBalls(): List<TextureAtlas.AtlasRegion> {
        val atlas = assetManager.get("balls.txt", TextureAtlas::class.java)

        val ball_0000ff = atlas.findRegion("0000ff")
        val ball_00ff00 = atlas.findRegion("00ff00")
        val ball_00ffff = atlas.findRegion("00ffff")
        val ball_0300ff = atlas.findRegion("0300ff")
        val ball_3200ff = atlas.findRegion("3200ff")
        val ball_ff0000 = atlas.findRegion("ff0000")
        val ball_ff0064 = atlas.findRegion("ff0064")
        val ball_ffffff = atlas.findRegion("ffffff")

        balls = listOf<TextureAtlas.AtlasRegion>(ball_0000ff,
                                                 ball_00ff00,
                                                 ball_00ffff,
                                                 ball_0300ff,
                                                 ball_3200ff,
                                                 ball_ff0000,
                                                 ball_ff0064,
                                                 ball_ffffff)
        return balls
    }
}

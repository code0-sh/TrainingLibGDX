package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.TimeUtils
import java.util.*

class MyGdxGame : ScreenAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var balls: List<Sprite>
    private lateinit var assetManager: AssetManager
    private lateinit var balldrops: ArrayList<Rectangle>
    private var lastDropTime: Long = 0

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        balls[0].draw(batch)
//        for (balldrop in balldrops)
//        {
//            balls[0].setPosition(balldrop.x, balldrop.y)
//            balls[0].setScale(0.5f)
//            balls[0].draw(batch)
//        }
        batch.end()

        balls[0].translate(0.1f, 0f)

        // check if we need to create a new raindrop
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnBallDrop();

    }

    override fun show() {
        loadResource()
        batch = SpriteBatch()
        createBalls()
        balls[0].setPosition(100f, 100f)
        balls[0].setScale(0.5f)

        // create the balldrops ArrayList and spawn the first balldrop
        balldrops = ArrayList<Rectangle>()
        spawnBallDrop()
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

        val ball_0000ff = Sprite(atlas.findRegion("0000ff"))
        val ball_00ff00 = Sprite(atlas.findRegion("00ff00"))
        val ball_00ffff = Sprite(atlas.findRegion("00ffff"))
        val ball_0300ff = Sprite(atlas.findRegion("0300ff"))
        val ball_3200ff = Sprite(atlas.findRegion("3200ff"))
        val ball_ff0000 = Sprite(atlas.findRegion("ff0000"))
        val ball_ff0064 = Sprite(atlas.findRegion("ff0064"))
        val ball_ffffff = Sprite(atlas.findRegion("ffffff"))

        balls = listOf<Sprite>(ball_0000ff,
                                      ball_00ff00,
                                      ball_00ffff,
                                      ball_0300ff,
                                      ball_3200ff,
                                      ball_ff0000,
                                      ball_ff0064,
                                      ball_ffffff)
    }

    private fun spawnBallDrop() {
        val balldrop = Rectangle()
        balldrop.x = MathUtils.random(0f, 800f - 64f)
        print("balldrop.x:${balldrop.x}")
        balldrop.y = 50f
        balldrops.add(balldrop)
        lastDropTime = TimeUtils.nanoTime()
    }
}

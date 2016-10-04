package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


class MyGdxGame : ScreenAdapter() {
    private lateinit var balls: List<TextureAtlas.AtlasRegion>
    private lateinit var assetManager: AssetManager
    private lateinit var ball: Image
    var screenWidth: Float = 0f
    var screenHeight: Float = 0f
    var ballWidth: Float = 0f
    var ballHeight: Float = 0f
    private lateinit var camera: OrthographicCamera
    private lateinit var world: World
    private lateinit var body: Body
    private val scale = 0.5f
    private val gravity = -9.8f
    private lateinit var stage: Stage


    override fun render(delta: Float) {
        // tell the camera to update its matrices.
        camera.update()

        world.step(Gdx.graphics.deltaTime, 6, 2)

        ball.setPosition(body.position.x, body.position.y)

        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        ball.setBounds(body.position.x - (ballWidth / 2), body.position.y - (ballHeight / 2), ballWidth, ballHeight)

        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun show() {
        // Resource
        loadResource()

        // Screen
        screenWidth = Gdx.graphics.width.toFloat()
        screenHeight = Gdx.graphics.height.toFloat()

        // Camera
        camera = OrthographicCamera(screenWidth, screenHeight)
        camera.position.set(screenWidth / 2, screenHeight / 2, 0f)
        camera.update()

        // Ball
        createBalls()

        ball = Image(balls[0])
        ball.setPosition(10f, 10f)
        ball.setScale(scale)

        ballWidth = ball.width
        ballHeight = ball.height
        ball.setBounds( camera.position.x - (ballWidth / 2), camera.position.y - (ballHeight / 2), ballWidth, ballHeight)

        // Event
        val listener = object: ClickListener() {
            override fun clicked(event: InputEvent, x:Float, y:Float) {
                println("001がクリックされた！")
            }
        }
        ball.addListener(listener)

        // Stage
        stage = Stage()
        Gdx.input.setInputProcessor(stage)
        stage.addActor(ball)

        // World
        world = World(Vector2(0f, gravity), true)
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(ball.x, ball.y)
        body = world.createBody(bodyDef)
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

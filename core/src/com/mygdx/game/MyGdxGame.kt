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
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World


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
    private lateinit var world: World
    private lateinit var body: Body
    private val scale = 0.5f
    private val gravity = -98f


    override fun render(delta: Float) {
        // tell the camera to update its matrices.
        camera.update()

        world.step(Gdx.graphics.deltaTime, 6, 2)

        ball.setPosition(body.position.x, body.position.y)

        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.projectionMatrix = camera.combined

        ball.setBounds(body.position.x - (ballWidth / 2), body.position.y - (ballHeight / 2), ballWidth, ballHeight)

        batch.begin()
        batch.draw(ball, ball.x, ball.y, ball.originX, ball.originY)
        batch.end()
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
        ball.setScale(scale)

        ballWidth = ball.width
        ballHeight = ball.height
        ball.setBounds( camera.position.x - (ballWidth / 2), camera.position.y - (ballHeight / 2), ballWidth, ballHeight)


        world = World(Vector2(0f, gravity), true)
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(ball.x, ball.y)
        body = world.createBody(bodyDef)


        Gdx.input.inputProcessor = object: InputAdapter() {
            override fun touchDown(screenX:Int, screenY:Int, pointer:Int, button:Int):Boolean {
                if (ball.boundingRectangle.contains(screenX.toFloat() + (ball.width/2) * scale, screenHeight - screenY.toFloat() + (ball.height/2) * scale)) {
                    //println("screenX ${screenX.toFloat()} /")
                    //println("screenHeight - screenY.toFloat() ${screenHeight - screenY.toFloat()} /")
                    //println("ball.x ${ball.x} /")
                    //println("ball.y ${ball.y} /")
                    //println("ball.originX ${ball.originX} /")
                    //println("ball.originY ${ball.originY} /")
                    //println("camera.position.x ${camera.position.x} /")
                    //println("camera.position.y ${camera.position.y}")
                    //println("body.position.x ${body.position.x} /")
                    //println("body.position.y ${body.position.y} /")
                    //println("ball.boundingRectangle.x ${ball.boundingRectangle.x} /")
                    //println("ball.boundingRectangle.y ${ball.boundingRectangle.y} /")
                    //println("ball.width / 2 ${ball.width / 2} /")
                    println("Ball Clicked!!")
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

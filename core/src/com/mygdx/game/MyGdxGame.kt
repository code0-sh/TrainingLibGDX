package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import java.util.*


class MyGdxGame : ScreenAdapter() {
    private lateinit var balls: List<TextureAtlas.AtlasRegion>
    private lateinit var assetManager: AssetManager
    private lateinit var ball: Ball
    private lateinit var camera: OrthographicCamera
    private lateinit var world: World
    private lateinit var body: Body
//    private val scale = 0.5f
    private val gravity = -9.8f
    private lateinit var stage: Stage
    private lateinit var viewport: Viewport

    private lateinit var bgImg: Texture
    private lateinit var bg: Sprite
    private lateinit var batch: SpriteBatch
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var font: BitmapFont
    private lateinit var uiCamera: OrthographicCamera
    private val random = Random()


    override fun render(delta: Float) {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= 2
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += 2
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += 2
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= 2
        }

        // カメラの座標の文字列を作って
        val info = String.format("cam pos(%f,%f)", camera.position.x, camera.position.y)


        // tell the camera to update its matrices.
        camera.update()
        batch.projectionMatrix = camera.combined

        uiCamera.update()
        batch.projectionMatrix = uiCamera.combined


        // Advance the world, by the amount of time that has elapsed since the last frame
        // Generally in a real game, dont do this in the render loop, as you are tying the physics
        // update rate to the frame rate, and vice versa
        world.step(Gdx.graphics.deltaTime, 6, 2)


        ball.timeStraightDirectionX += delta
        if (ball.timeStraightDirectionX > Ball.Time_Change_Direction) {
            ball.changeDirectionX()
            ball.timeStraightDirectionX = 0f
        }


        // Now update the ball position accordingly to it's now updated Physics body
        ball.move(body.position.y)

//        if (isCollisionBottom()) {
//            println("枠に接触")
//        }

        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //ball.setBounds(body.position.x - (ballWidth / 2), body.position.y - (ballHeight / 2), ballWidth, ballHeight)

        //println("ball.x ${ball.x}")
        //println("ball.y ${ball.y}")
        //println("isCollisionBottom ${isCollisionBottom()}")

        batch.begin()
        bg.draw(batch)
        font.draw(batch, info, 0f, 20f)
        batch.end()

        // ワールド座標軸を描画する。
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(1f, 0f, 0f, 1f)
        shapeRenderer.line(-1024f, 0f, 1024f, 0f)
        shapeRenderer.setColor(0f, 1f, 0f, 1f)
        shapeRenderer.line(0f, -1024f, 0f, 1024f)
        shapeRenderer.end()

        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun show() {
        // Resource
        loadResource()


        batch = SpriteBatch()
        bgImg = Texture("bg.png")
        bg = Sprite(bgImg)
        bg.setScale(2.0f, 2.0f)
        bg.setPosition(0f, 0f)

        shapeRenderer = ShapeRenderer()

        font = BitmapFont()


        // Camera
        camera = OrthographicCamera(800f, 480f)
        camera.setToOrtho(false, 800f, 480f)

        uiCamera = OrthographicCamera()
        uiCamera.setToOrtho(false, 800f, 480f)

        // Viewport
        viewport = FitViewport(800f, 480f, camera)

        // BallAtlas
        createBallAtlas()


//        ballWidth = ball.width
//        ballHeight = ball.height
//        ball.setBounds( camera.position.x - (ballWidth / 2), camera.position.y - (ballHeight / 2), ballWidth, ballHeight)


        // Wall


        // Stage
        stage = Stage()
        Gdx.input.inputProcessor = stage
        addBallToStage(stage)


        // World
        world = World(Vector2(0f, gravity), true)
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(ball.x, ball.y)
        body = world.createBody(bodyDef)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        assetManager.dispose()
        bgImg.dispose()
        font.dispose()
    }

    private fun loadResource() {
        assetManager = AssetManager()
        assetManager.load("balls.txt", TextureAtlas::class.java)
        assetManager.finishLoading()
    }

    /**
     * AssetManagerからボール(TextureAtlas)のリストを生成する
     */
    private fun createBallAtlas() {
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
    }

    /**
     * ボールを生成する
     * @param num ボールの番号
     * @param stage Stage
     * @param x ボールの初期位置のX座標
     */
    private fun addBallToStage(stage:Stage) {
        val num = random.nextInt(balls.size)
        val x = Ball.SIZE / 2 + random.nextFloat() * (Gdx.graphics.width - Ball.SIZE)
        val ballImage = Image(balls[num])
        //ballImage.setScale(scale)

        ball = Ball(x, Gdx.graphics.height.toFloat() - Ball.SIZE, ballImage)
        ball.setSize(Ball.SIZE, Ball.SIZE)
        ball.setOrigin(Ball.SIZE / 2, Ball.SIZE / 2)

        // Event
        val listener = object: ClickListener() {
            override fun clicked(event: InputEvent, x:Float, y:Float) {
                println("Ballがクリックされた！")

                // Action
                val actionSequence = Actions.sequence()
                val rotationAction = Actions.rotateBy(180 * 8 / Math.PI.toFloat(), 0.3f)
                val moveByYAction = Actions.moveTo(ball.x, 1500f, 0.25f)
                val removeActorAction = Actions.removeActor()

                actionSequence.addAction(rotationAction)
                actionSequence.addAction(moveByYAction)
                actionSequence.addAction(removeActorAction)

                ballImage.addAction(actionSequence)
            }
        }
        ballImage.addListener(listener)
        stage.addActor(ballImage)
    }

    private fun isCollisionBottom(): Boolean {
        return ball.y < 0
    }
}

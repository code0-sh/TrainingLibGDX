package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import java.util.*

class MyGdxGame : ScreenAdapter() {
    private lateinit var assetManager: Assets
    private var balls: MutableList<Ball> = mutableListOf()
    private lateinit var camera: OrthographicCamera
    private lateinit var world: World
    private lateinit var body: Body
    private var bodys: MutableList<Body> = mutableListOf()
//    private val scale = 0.5f
    private lateinit var stage: Stage
    private lateinit var viewport: Viewport

    private lateinit var bgImg: Texture
    private lateinit var bg: Sprite
    private lateinit var batch: SpriteBatch
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var font: BitmapFont
    private lateinit var uiCamera: OrthographicCamera
    private lateinit var freeTypeFontTimer: FreeTypeFont
    private lateinit var labelGroup: Group
    private lateinit var ballGroup: Group

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
        //val info = "アイウエオ"

        // tell the camera to update its matrices.
        camera.update()
        batch.projectionMatrix = camera.combined

        uiCamera.update()
        batch.projectionMatrix = uiCamera.combined

        // Advance the world, by the amount of time that has elapsed since the last frame
        // Generally in a real game, dont do this in the render loop, as you are tying the physics
        // update rate to the frame rate, and vice versa
        world.step(Gdx.graphics.deltaTime, 6, 2)

        update(delta)

        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

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
        assetManager = Assets()

        // World
        world = World(Vector2(0f, GameState.gravity), true)

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

        // Stage
        stage = Stage()
        Gdx.input.inputProcessor = stage

        // Timer
        setupTimer()

        // Label
        createLabel()

        // GameState
        GameState.initGameSate()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        Assets.dispose()
        bgImg.dispose()
        font.dispose()
    }

    /**
     * ラベルの生成
     */
    private fun createLabel() {
        // Ball Group
        ballGroup = Group()
        ballGroup.setPosition(0f, 0f)
        stage.addActor(ballGroup)

        // Label Group
        labelGroup = Group()
        labelGroup.setPosition(0f, 0f)
        stage.addActor(labelGroup)


        // Start Label
        val freeTypeFontStart = FreeTypeFont("Start !!")
        freeTypeFontStart.setColor(Color.ORANGE)
        freeTypeFontStart.setPosition(Gdx.graphics.width * 0.3f, Gdx.graphics.height * 0.9f)
        labelGroup.addActor(freeTypeFontStart.label)

        // Major Code Label
        val freeTypeFontMajorCode = FreeTypeFont("現在のライトアップエリアは" + GameState.major + "です!")
        freeTypeFontMajorCode.setColor(Color.RED)
        freeTypeFontMajorCode.setFontSize(20)
        freeTypeFontMajorCode.setCenterBottom()
        labelGroup.addActor(freeTypeFontMajorCode.label)

        // Timer Label
        freeTypeFontTimer = FreeTypeFont("TIME : ${GameState.time}")
        freeTypeFontTimer.setPosition(Gdx.graphics.width * 0.55f, Gdx.graphics.height * 0.9f)
        labelGroup.addActor(freeTypeFontTimer.label)
    }

    /**
     * Timerの設定
     */
    private fun setupTimer() {
        if (!GameState.isOnTimer) {
            GameState.isOnTimer = true
            // Timer
            Timer.schedule(object: Timer.Task() {
                override fun run() {
                    // Ball
                    setupBall()

                    // Body
                    setupBody()
                }
            }, 0f, 0.45f)

            Timer.schedule(object: Timer.Task() {
                override fun run() {
                    GameState.time -= 1
                    if (GameState.time < 0 && GameState.score == 0) {
                        GameState.time = 8
                    }
                    if (GameState.time < 0 && GameState.score != 0) {
                        println("End")
                        Timer.instance().clear()
                        return
                    }
                    freeTypeFontTimer.setText("TIME : ${GameState.time}")
                }
            }, 0f, 1f)
        }
    }

    private fun setupBall() {
        val random = Random()
        val num = random.nextInt(assetManager.ballAtlas.size)

        val x = random.nextFloat() * (Gdx.graphics.width - Ball.SIZE)
        val y = Gdx.graphics.height.toFloat() - Ball.SIZE
        val image = Image(assetManager.ballAtlas[num])
        val name = num.toString()

        val ball = Ball(x, y, image, name)

        ball.setup(ballGroup)
        balls.add(ball)
    }

    private fun setupBody() {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        body = world.createBody(bodyDef)
        body.isActive = true
        bodys.add(body)
    }

    /**
     * 更新
     * @param delta 更新間隔
     */
    private fun update(delta:Float) {
        for ( index in balls.indices) {
            // 地面と衝突
            if (balls[index].y + Ball.SIZE < 0 ) {
                balls[index].isOffScreen = true
                bodys[index].isActive = false
            }
            // 壁と衝突
            if (balls[index].x < 0 || Gdx.graphics.width - Ball.SIZE < balls[index].x) {
                //println("壁と衝突")
                balls[index].isWallCollided = true
            }
        }


        balls.toList().forEach {
            if (it.isOffScreen) {
                balls.remove(it)
                it.image.remove()
            }
        }

        bodys.toList().forEach {
            if (!it.isActive) {
                bodys.remove(it)
            }
        }

        for (index in balls.indices) {
            balls[index].update(delta, bodys[index].position.y)
        }
    }
}

package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Timer
import java.util.*

class MainScreen(game: BallGame) : ScreenAdapter() {
    private var balls: MutableList<Ball> = mutableListOf()
    private var world: World
    private lateinit var body: Body
    private var bodys: MutableList<Body> = mutableListOf()
    private var stage: Stage
    private lateinit var freeTypeFontTimer: FreeTypeFont
    private lateinit var labelGroup: Group
    private lateinit var ballGroup: Group
    internal val game: BallGame

    init {
        this.game = game

        // World
        world = World(Vector2(0f, GameState.gravity), true)

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

    override fun render(delta: Float) {
        world.step(Gdx.graphics.deltaTime, 6, 2)

        update(delta)

        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun dispose() {
        println("MainScreen dispose")
        stage.dispose()
        world.dispose()
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

            val self = this
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
                        // 結果画面に遷移する
                        game.screen = ResultScreen(game)
                        self.dispose()
                    }
                    freeTypeFontTimer.setText("TIME : ${GameState.time}")
                }
            }, 0f, 1f)
        }
    }

    /**
     * ボールの設定
     */
    private fun setupBall() {
        val random = Random()
        val num = random.nextInt(game.assetManager.ballAtlas.size)

        val x = random.nextFloat() * (Gdx.graphics.width - Ball.SIZE)
        val y = Gdx.graphics.height.toFloat() - Ball.SIZE
        val image = Image(game.assetManager.ballAtlas[num])
        val name = num.toString()

        val ball = Ball(x, y, image, name)

        ball.setup(ballGroup)
        balls.add(ball)
    }

    /**
     * Bodyの設定
     */
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
            // ボールの位置の更新
            balls[index].update(delta, bodys[index].position.y)
        }
    }
}

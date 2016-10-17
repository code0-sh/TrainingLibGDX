package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Timer
import java.util.*

class MainScreen(game: BallGame) : ScreenAdapter() {
    private var balls: MutableList<Ball> = mutableListOf()
    private var stage: Stage
    private lateinit var freeTypeFontTimer: FreeTypeFont
    private lateinit var labelGroup: Group
    private lateinit var ballGroup: Group
    internal val game: BallGame

    init {
        println("MainScreen init")

        this.game = game

        // Stage
        stage = Stage()
        Gdx.input.inputProcessor = stage

        // GameState
        GameState.initGameSate()

        // Timer
        createTimer()

        // Label
        createLabel()
    }

    override fun render(delta: Float) {
        update(delta)

        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun show() {
        println("MainScreen show")
    }

    override fun dispose() {
        println("MainScreen dispose")
        stage.dispose()
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
        freeTypeFontStart.setPosition(Gdx.graphics.width * 0.1f, Gdx.graphics.height * 0.9f)
        labelGroup.addActor(freeTypeFontStart.label)

        // Major Code Label
        val freeTypeFontMajorCode = FreeTypeFont("現在のライトアップエリアは" + GameState.major + "です!")
        freeTypeFontMajorCode.setColor(Color.RED)
        freeTypeFontMajorCode.setFontSize(35)
        freeTypeFontMajorCode.setCenterPosition(Gdx.graphics.height * 0.05f)
        labelGroup.addActor(freeTypeFontMajorCode.label)

        // Timer Label
        freeTypeFontTimer = FreeTypeFont("TIME : ${GameState.time}")
        freeTypeFontTimer.setPosition(Gdx.graphics.width * 0.65f, Gdx.graphics.height * 0.9f)
        labelGroup.addActor(freeTypeFontTimer.label)
    }

    /**
     * Timerの生成
     */
    private fun createTimer() {
        if (!GameState.isOnTimer) {
            GameState.isOnTimer = true

            val self = this
            // Timer
            Timer.schedule(object: Timer.Task() {
                override fun run() {
                    // Ball
                    createBall()
                }
            }, 1f, 0.45f)

            Timer.schedule(object: Timer.Task() {
                override fun run() {
                    GameState.time -= 1
                    if (GameState.time < 0 && GameState.score == 0) {
                        GameState.time = 8
                    }
                    if (GameState.time < 0 && GameState.score != 0) {
                        Timer.instance().clear()
                        // 結果画面に遷移する
                        game.screen = ResultScreen(game)
                        self.dispose()
                    }
                    freeTypeFontTimer.setText("TIME : ${GameState.time}")
                }
            }, 1f, 1f)
        }
    }

    /**
     * ボールの生成
     */
    private fun createBall() {
        val random = Random()
        val num = random.nextInt(game.assets.ballAtlas.size)

        val x = random.nextFloat() * (Gdx.graphics.width - Ball.SIZE)
        val y = Gdx.graphics.height.toFloat()
        val image = Image(game.assets.ballAtlas[num])
        val name = num.toString()

        val ball = Ball(x, y, image, name)
        ball.setSize(Ball.SIZE, Ball.SIZE)
        ball.setOrigin(Ball.SIZE / 2, Ball.SIZE / 2)
        ball.velocity.add(0f, -250f)

        val listener = object: ClickListener() {
            override fun clicked(event: InputEvent, x:Float, y:Float) {
                println("Ball:No.${ball.name}がクリックされた！")
                GameState.update(ball.name.toInt())
                println("GameState.number:" + GameState.number)
                println("GameState.score:" + GameState.score)

                ballAction(ball)
            }
        }

        ball.image.addListener(listener)
        ballGroup.addActor(image)
        balls.add(ball)
    }

    /**
     * ボールのアクション
     * @param ball ボール
     */
    private fun ballAction(ball: Ball) {
        val actionSequence = Actions.sequence()
        val rotationAction = Actions.rotateBy(Ball.ROTATE, Ball.ROTATE_TIME)
        val moveByYAction = Actions.moveTo(ball.position.x, Ball.RISE_DISTANCE, Ball.RISE_TIME)
        val removeActorAction = Actions.removeActor()

        actionSequence.addAction(rotationAction)
        actionSequence.addAction(moveByYAction)
        actionSequence.addAction(removeActorAction)

        ball.image.addAction(actionSequence)
    }

    /**
     * 更新
     * @param delta 更新間隔
     */
    private fun update(delta:Float) {
        for ( index in balls.indices) {
            // 地面と衝突
            if (balls[index].position.y + Ball.SIZE < 0 ) {
                balls[index].isOffScreen = true
            }
            // 壁と衝突
            if (balls[index].position.x < 0 || Gdx.graphics.width - Ball.SIZE < balls[index].position.x) {
                balls[index].isWallCollided = true
            }
        }

        balls.toList().forEach {
            if (it.isOffScreen) {
                balls.remove(it)
                it.image.remove()
            }
        }

        for (index in balls.indices) {
            // ボールの位置の更新
            balls[index].update(delta)
        }
    }
}

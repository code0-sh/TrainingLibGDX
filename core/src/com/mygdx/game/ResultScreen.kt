package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import java.lang.ref.WeakReference

class ResultScreen(game: WeakReference<BallGame>) : ScreenAdapter() {
    private var game: BallGame
    private var stage: Stage
    private var resultGroup: Group
    private var endGroup: Group

    init {
        println("ResultScreen init")
        this.game = game.get()

        // Stage
        stage = Stage()
        Gdx.input.inputProcessor = stage

        // Result Group
        resultGroup = Group()
        resultGroup.setPosition(0f, 0f)
        stage.addActor(resultGroup)

        // End Group
        endGroup = Group()
        endGroup.setPosition(0f, -Gdx.graphics.height.toFloat())
        stage.addActor(endGroup)

        // Result Ball
        createResultBall()

        // Result Label
        createResultLabel()

        // End Game Button
        createEndGameButton()

        // End Retry Button
        createEndRetryButton()
    }

    override fun render(delta: Float) {
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun show() {
        println("ResultScreen show")
    }

    override fun dispose() {
        println("ResultScreen dispose")
        stage.dispose()
        resultGroup.clear()
        endGroup.clear()
    }

    /**
     * 「GAMEを終了」ボタンの生成
     */
    private fun createEndGameButton() {
        val self = this
        val button = Image(game.assets.finishTexture)
        button.setSize(420f, 206f)
        val x = (Gdx.graphics.width - button.width) / 2
        val y = Gdx.graphics.height * 0.05f
        button.setPosition(x, y)

        val listener = object: ClickListener() {
            override fun clicked(event: InputEvent, x:Float, y:Float) {
                // Top画面に遷移する
                println("GAMEを終了")
                game.endGame_.get().returnToTop()
                self.dispose()
            }
        }
        button.addListener(listener)

        endGroup.addActor(button)
    }

    /**
     * 「もう一度遊ぶ」ボタンの生成
     */
    private fun createEndRetryButton() {
        val self = this
        val button = Image(game.assets.retryTexture)
        button.setSize(390f, 390f)
        val x = (Gdx.graphics.width - button.width) / 2
        val y = (Gdx.graphics.height - button.height) / 2
        button.setPosition(x, y)

        val listener = object: ClickListener() {
            override fun clicked(event: InputEvent, x:Float, y:Float) {
                // Main画面に遷移する
                game.screen = MainScreen(WeakReference<BallGame>(game))
                self.dispose()
                println("Main画面に遷移する")
            }
        }
        button.addListener(listener)

        endGroup.addActor(button)
    }

    /**
     * ボールの生成
     */
    private fun createResultBall() {
        val x = (Gdx.graphics.width - Ball.SIZE) / 2
        val y = (Gdx.graphics.height - Ball.SIZE) / 2
        val number = GameState.number
        val image = Image(game.assets.ballAtlas[number])
        val ball = Ball(x, y, image, number)
        ball.setSize(Ball.SIZE, Ball.SIZE)
        ball.setOrigin(Ball.SIZE / 2, Ball.SIZE / 2)
        println("ball.position.x:${ball.position.x}")
        println("ball.position.y:${ball.position.y}")

        val listener = object: ClickListener() {
            override fun clicked(event: InputEvent, x:Float, y:Float) {
                resultBallAction(ball)
            }
        }

        ball.image.addListener(listener)
        resultGroup.addActor(image)
    }

    /**
     * ボールのアクション
     * @param ball ボール
     */
    private fun resultBallAction(ball: Ball) {
        val actionSequence = Actions.sequence()
        val scaleAction = Actions.scaleTo(Ball.MAGNIFICATION, Ball.MAGNIFICATION, Ball.MAGNIFICATION_TIME)
        val moveByYAction = Actions.moveTo(ball.position.x, Ball.RISE_DISTANCE, Ball.RISE_TIME)
        val removeActorAction = Actions.removeActor()

        actionSequence.addAction(scaleAction)
        actionSequence.addAction(moveByYAction)
        actionSequence.addAction(removeActorAction)
        actionSequence.run {
            transmissionColor()
            resultGroupAction()
        }

        ball.image.addAction(actionSequence)
    }

    /**
     * 結果Groupのアクション
     */
    private fun resultGroupAction() {
        val actionSequence = Actions.sequence()
        val delayAction = Actions.delay(Ball.MAGNIFICATION_TIME + Ball.RISE_TIME)
        val moveByYAction = Actions.moveTo(resultGroup.x, Ball.RISE_DISTANCE, Ball.RISE_TIME)
        val removeActorAction = Actions.removeActor()
        actionSequence.addAction(delayAction)
        actionSequence.addAction(moveByYAction)
        actionSequence.addAction(removeActorAction)
        actionSequence.run {
            endGroupAction()
        }
        resultGroup.addAction(actionSequence)
    }

    /**
     * 終了Groupのアクション
     */
    private fun endGroupAction() {
        val actionSequence = Actions.sequence()
        val delayAction = Actions.delay(Ball.MAGNIFICATION_TIME + Ball.RISE_TIME)
        val moveByYAction = Actions.moveTo(endGroup.x, 0f, Ball.RISE_TIME)
        actionSequence.addAction(delayAction)
        actionSequence.addAction(moveByYAction)
        endGroup.addAction(actionSequence)
    }

    /**
     * 結果ラベルの生成
     */
    private fun createResultLabel() {
        // Major Code Label
        val freeTypeFontMajorCode = FreeTypeFont("現在のライトアップエリアは" + GameState.major + "です!! " + GameState.flashNumber + "です")
        freeTypeFontMajorCode.setColor(Color.RED)
        freeTypeFontMajorCode.setFontSize(35)
        freeTypeFontMajorCode.setCenterPosition(Gdx.graphics.height * 0.8f)
        resultGroup.addActor(freeTypeFontMajorCode.label)

        // First Attention Label
        val freeTypeFontFirstAttention = FreeTypeFont("ボールをタップして")
        freeTypeFontFirstAttention.setFontSize(35)
        freeTypeFontFirstAttention.setCenterPosition(Gdx.graphics.height * 0.7f)
        resultGroup.addActor(freeTypeFontFirstAttention.label)

        // Second Attention Label
        val freeTypeFontSecondAttention = FreeTypeFont("照明の色を変化させろ!︎!")
        freeTypeFontSecondAttention.setFontSize(35)
        freeTypeFontSecondAttention.setCenterPosition(Gdx.graphics.height * 0.65f)
        resultGroup.addActor(freeTypeFontSecondAttention.label)
    }

    /**
     * 色の送信処理
     */
    fun transmissionColor() {
        val httpGet = Net.HttpRequest(Net.HttpMethods.GET)
        httpGet.url = "http://153.126.138.57:8090/" + GameState.major + "/" + GameState.colorCode + "/" + GameState.flashNumber
        println("httpGet.url:" + httpGet.url)

        Gdx.net.sendHttpRequest(httpGet, object: Net.HttpResponseListener {
            override fun handleHttpResponse(httpResponse: Net.HttpResponse) {
                val status = httpResponse.resultAsString
                println("status: success" + status)
                //do stuff here based on response
            }
            override fun failed(t:Throwable) {
                val status = "failed"
                println("status: failed" + status)
                //do stuff here based on the failed attempt
            }
            override fun cancelled() {
                val status = "cancelled"
                println("status: cancelled" + status)
            }
        })
    }
}

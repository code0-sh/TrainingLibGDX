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

class ResultScreen(game: BallGame) : ScreenAdapter() {
    internal val game: BallGame
    private var stage: Stage
    private var resultGroup: Group
    private var endGroup: Group

    init {
        this.game = game

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

    override fun dispose() {
        println("ResultScreen dispose")
    }

    /**
     * 「GAMEを終了」ボタンの生成
     */
    private fun createEndGameButton() {
        val button = Image(game.assets.finishTexture)
        button.setSize(210f, 103f)
        val x = (Gdx.graphics.width - button.width) / 2
        val y = Gdx.graphics.height * 0.05f
        button.setPosition(x, y)
        endGroup.addActor(button)
    }

    /**
     * 「もう一度遊ぶ」ボタンの生成
     */
    private fun createEndRetryButton() {
        val button = Image(game.assets.retryTexture)
        button.setSize(195f, 195f)
        val x = (Gdx.graphics.width - button.width) / 2
        val y = (Gdx.graphics.height - button.height) / 2
        button.setPosition(x, y)
        endGroup.addActor(button)
    }

    /**
     * ボールの生成
     */
    private fun createResultBall() {
        val x = (Gdx.graphics.width - Ball.SIZE) / 2
        val y = (Gdx.graphics.height - Ball.SIZE) / 2
        val image = Image(game.assets.ballAtlas[GameState.number])
        val name = GameState.number.toString()

        val ball = Ball(x, y, image, name)
        ball.setSize(Ball.SIZE, Ball.SIZE)
        ball.setOrigin(Ball.SIZE / 2, Ball.SIZE / 2)

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
        val moveByYAction = Actions.moveTo(ball.x, Ball.RISE_DISTANCE, Ball.RISE_TIME)
        val removeActorAction = Actions.removeActor()

        actionSequence.addAction(scaleAction)
        actionSequence.addAction(moveByYAction)
        actionSequence.addAction(removeActorAction)
        actionSequence.run {
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
        val freeTypeFontMajorCode = FreeTypeFont("現在のライトアップエリアは" + GameState.major + "です!")
        freeTypeFontMajorCode.setColor(Color.RED)
        freeTypeFontMajorCode.setFontSize(20)
        freeTypeFontMajorCode.setCenterPosition(Gdx.graphics.height * 0.8f)
        resultGroup.addActor(freeTypeFontMajorCode.label)

        // First Attention Label
        val freeTypeFontFirstAttention = FreeTypeFont("ボールをタップして")
        freeTypeFontFirstAttention.setFontSize(20)
        freeTypeFontFirstAttention.setCenterPosition(Gdx.graphics.height * 0.7f)
        resultGroup.addActor(freeTypeFontFirstAttention.label)

        // Second Attention Label
        val freeTypeFontSecondAttention = FreeTypeFont("照明の色を変化させろ!︎!")
        freeTypeFontSecondAttention.setFontSize(20)
        freeTypeFontSecondAttention.setCenterPosition(Gdx.graphics.height * 0.65f)
        resultGroup.addActor(freeTypeFontSecondAttention.label)
    }
}
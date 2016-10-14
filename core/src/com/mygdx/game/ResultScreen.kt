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

    init {
        this.game = game

        // Stage
        stage = Stage()
        Gdx.input.inputProcessor = stage

        // Result Group
        resultGroup = Group()
        resultGroup.setPosition(0f, 0f)
        stage.addActor(resultGroup)

        // Ball
        createBall()

        // Label
        createResultLabel()
    }

    override fun render(delta: Float) {
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun dispose() {
        println("ResultScreen dispose")
    }

    /**
     * ボールの生成
     */
    private fun createBall() {
        val x = (Gdx.graphics.width - Ball.SIZE) / 2
        val y = (Gdx.graphics.height - Ball.SIZE) / 2
        val image = Image(game.assetManager.ballAtlas[GameState.number])
        val name = GameState.number.toString()

        val ball = Ball(x, y, image, name)
        ball.setSize(Ball.SIZE, Ball.SIZE)
        ball.setOrigin(Ball.SIZE / 2, Ball.SIZE / 2)

        val listener = object: ClickListener() {
            override fun clicked(event: InputEvent, x:Float, y:Float) {
                ballAction(ball)
            }
        }

        ball.image.addListener(listener)
        resultGroup.addActor(image)
    }

    /**
     * ボールのアクション
     * @param ball ボール
     */
    private fun ballAction(ball: Ball) {
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
     * resultGroupのアクション
     */
    private fun resultGroupAction() {
        val actionSequence = Actions.sequence()
        val delayAction = Actions.delay(Ball.MAGNIFICATION_TIME + Ball.RISE_TIME)
        val moveByYAction = Actions.moveTo(resultGroup.x, Ball.RISE_DISTANCE, Ball.RISE_TIME)
        val removeActorAction = Actions.removeActor()
        actionSequence.addAction(delayAction)
        actionSequence.addAction(moveByYAction)
        actionSequence.addAction(removeActorAction)
        resultGroup.addAction(actionSequence)
    }

    /**
     * ラベルの生成
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
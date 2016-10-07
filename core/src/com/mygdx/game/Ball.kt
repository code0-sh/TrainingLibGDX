package com.mygdx.game

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

/**
 * ボールのイメージ、初期位置を設定
 * @param x ボールの初期位置(X座標)
 * @param y ボールの初期位置(Y座標)
 * @param image ボールのイメージ
 */

class Ball(x:Float, y:Float, image: Image, name: String) : GameObject(x, y, image) {

    companion object {
        /**
         * ボールのサイズ
         */
        const val SIZE = 50f

        /**
         * ボールが反転するまでの時間
         */
        const val Time_Change_Direction = 0.7f

        /**
         * アニメーション
         * ボールが回転する角度(ラジアン)
         */
        private const val ROTATE = (180 * 8 / Math.PI).toFloat()

        /**
         * アニメーション
         * ボールの回転時間
         */
        private const val ROTATE_TIME = 0.3f

        /**
         * アニメーション
         * ボールの上昇距離
         */
        private const val RISE_DISTANCE = 1500f

        /**
         * アニメーション
         * ボールの上昇時間
         */
        private const val RISE_TIME = 0.25f
    }

    /**
     * ボールの名前
     */
    var name: String = ""

    /**
     * ボールが面外にあるかどうか
     */
    var isOffScreen = false

    /**
     * ボールが壁と衝突したかどうか
     */
    var isWallCollided = false

    init {
        this.x = x
        this.y = y
        this.image = image
        this.name = name
    }

    /**
     * ボールがX軸方に移動している時間
     */
    private var timeStraightDirectionX = 0f

    /**
     * 移動値X
     */
    private var directionX = 2f

    /**
     * 移動方向反転(X座標)
     */
    private fun changeDirectionX() {
        directionX *= -1
    }

    /**
     * ボールをStageに設定する
     * @param stage Stage
     */
    private fun addStage(stage: Stage) {
        stage.addActor(this.image)
    }

    /**
     * ボールのイベントを設定する
     */
    private fun addListener() {
        val ball = this
        val listener = object: ClickListener() {
            override fun clicked(event: InputEvent, x:Float, y:Float) {
                println("Ball:No.${ball.name}がクリックされた！")

                // Action
                val actionSequence = Actions.sequence()
                val rotationAction = Actions.rotateBy(ROTATE, ROTATE_TIME)
                val moveByYAction = Actions.moveTo(ball.x, RISE_DISTANCE, RISE_TIME)
                val removeActorAction = Actions.removeActor()

                actionSequence.addAction(rotationAction)
                actionSequence.addAction(moveByYAction)
                actionSequence.addAction(removeActorAction)

                ball.image.addAction(actionSequence)
            }
        }
        ball.image.addListener(listener)
    }

    /**
     * ボールの位置の更新
     * @param deltaTime 更新間隔
     * @param bodyPositionY 重力加速度による移動距離
     */
    fun update(deltaTime:Float, bodyPositionY: Float) {
        this.timeStraightDirectionX += deltaTime
        if (this.timeStraightDirectionX > Ball.Time_Change_Direction) {
            this.changeDirectionX()
            this.timeStraightDirectionX = 0f
        }
        if (isWallCollided) {
            this.changeDirectionX()
            this.isWallCollided = false
        }
        this.updatePosition(x + directionX, bodyPositionY)
    }

    /**
     * ボールの設定
     * @param stage Stage
     */
    fun setup(stage: Stage) {
        this.setSize(Ball.SIZE, Ball.SIZE)
        this.setOrigin(Ball.SIZE / 2, Ball.SIZE / 2)
        this.addListener()
        this.addStage(stage)
    }
}
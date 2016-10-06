package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import java.util.*

/**
 * ボールのイメージ、初期位置を設定
 * @param x ボールの初期位置(X座標)
 * @param y ボールの初期位置(Y座標)
 * @param image ボールのイメージ
 */

class Ball(x:Float, y:Float, image: Image) : GameObject(x, y, image) {

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

        /**
         * ボールを生成してイベントを設定しStageに設定する
         * @param stage Stage
         * @param ballAtlas List<TextureAtlas.AtlasRegion>
         * @return Ball
         */
        fun create(stage:Stage, ballAtlas:List<TextureAtlas.AtlasRegion>): Ball {
            val random = Random()
            val num = random.nextInt(ballAtlas.size)
            val x = SIZE / 2 + random.nextFloat() * (Gdx.graphics.width - SIZE)
            val ballImage = Image(ballAtlas[num])
            //ballImage.setScale(scale)

            val ball = Ball(x, Gdx.graphics.height.toFloat() - SIZE, ballImage)
            ball.setSize(SIZE, SIZE)
            ball.setOrigin(SIZE / 2, SIZE / 2)
            ball.name = num.toString()

            // Event
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

                    ballImage.addAction(actionSequence)
                }
            }
            ballImage.addListener(listener)
            stage.addActor(ballImage)
            return ball
        }
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
     * 移動値Y
     */
//    private var directionY = 2f

    /**
     * 移動方向反転
     */
//    private fun changeDirection() {
//        changeDirectionX()
//        changeDirectionY()
//    }

    /**
     * 移動方向反転(X座標)
     */
    private fun changeDirectionX() {
        directionX *= -1
    }

    /**
     * 移動方向反転(Y座標)
     */
//    private fun changeDirectionY() {
//        directionY *= -1
//    }

    /**
     * ボールの名前
     */
    lateinit var name: String

    /**
     * ボールが面外にあるかどうか
     */
    var isOffScreen = false

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
        this.updatePosition(x + directionX, bodyPositionY)
    }
}
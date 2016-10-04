package com.mygdx.game

import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * ボールのイメージ、初期位置を設定
 * @param x ボールの初期位置(X座標)
 * @param y ボールの初期位置(Y座標)
 * @param image ボールのイメージ
 */

class Ball(x:Float, y:Float, image: Image) : GameObject(x, y, image) {

    companion object {
        const val SIZE = 50f
        /**
         * ボールが反転するまでの時間
         */
        const val Time_Change_Direction = 0.7f
    }

    /**
     * ボールがX軸方に移動している時間
     */
    var timeStraightDirectionX = 0f

    /**
     * 移動値Xを返却
     * @return 移動値X
     */
    private var directionX = 2f
    /**
     * 移動値Yを返却
     * @return 移動値Y
     */
    private var directionY = 2f
    /**
     * ボールの移動処理
     */
    fun move(bodyPositionY:Float) {
        super.updatePosition(x + directionX, bodyPositionY)
    }
    /**
     * 移動方向反転
     */
    fun changeDirection() {
        changeDirectionX()
        changeDirectionY()
    }
    /**
     * 移動方向反転(X座標)
     */
    fun changeDirectionX() {
        directionX *= -1
    }
    /**
     * 移動方向反転(Y座標)
     */
    fun changeDirectionY() {
        directionY *= -1
    }
}
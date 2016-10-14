package com.mygdx.game

import com.badlogic.gdx.scenes.scene2d.ui.Image

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
        internal const val SIZE = 175f

        /**
         * ボールが反転するまでの時間
         */
        private const val Time_Change_Direction = 0.7f

        /**
         * アニメーション
         * ボールが回転する角度(ラジアン)
         */
        internal const val ROTATE = (180 * 8 / Math.PI).toFloat()

        /**
         * アニメーション
         * ボールの回転時間
         */
        internal const val ROTATE_TIME = 0.3f

        /**
         * アニメーション
         * ボールの上昇距離
         */
        internal const val RISE_DISTANCE = 1500f

        /**
         * アニメーション
         * ボールの上昇時間
         */
        internal const val RISE_TIME = 0.25f

        /**
         * アニメーション
         * ボールの拡大倍率
         */
        internal const val MAGNIFICATION = 2f

        /**
         * アニメーション
         * ボールの拡大時間
         */
        internal const val MAGNIFICATION_TIME = 1f
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
     * ボールの位置の更新
     * @param deltaTime 更新間隔
     * @param bodyPositionY 重力加速度による移動距離
     */
    fun update(deltaTime:Float, bodyPositionY: Float) {
        timeStraightDirectionX += deltaTime
        if (timeStraightDirectionX > Time_Change_Direction) {
            changeDirectionX()
            timeStraightDirectionX = 0f
        }
        if (isWallCollided) {
            changeDirectionX()
            isWallCollided = false
        }
        updatePosition(x + directionX, bodyPositionY)
    }
}
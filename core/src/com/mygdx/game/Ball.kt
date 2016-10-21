package com.mygdx.game

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.mygdx.game.Ball.FlashNumber.ONE
import com.mygdx.game.Ball.FlashNumber.ZERO

/**
 * ボールのイメージ、初期位置を設定
 * @param x ボールの初期位置(X座標)
 * @param y ボールの初期位置(Y座標)
 * @param image ボールのイメージ
 */

class Ball(x:Float, y:Float, image: Image, number: Int) : DynamicGameObject(x, y, image) {

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
     * ボールのフラッシュ
     */
    enum class FlashNumber(val number: Int) {
        ZERO(0),
        ONE(1)
    }

    /**
     * ボールの名前
     */
    lateinit var name: String

    /**
     * ボールの番号
     */
    var number: Int = 0

    /**
     * ボールのフラッシュ
     */
    lateinit var flash: FlashNumber

    /**
     * ボールが面外にあるかどうか
     */
    var isOffScreen = false

    /**
     * ボールが壁と衝突したかどうか
     */
    var isWallCollided = false

    init {
        this.position = Vector2(x ,y)
        this.image = image
        this.image.setPosition(x, y)
        this.number = number
        this.name = when (number) {
            0 -> "blue"
            1 -> "lime"
            2 -> "cyan"
            3 -> "purple"
            4 -> "yellow"
            5 -> "red"
            6 -> "pink"
            7 -> "white"
            else -> "unknown"
        }
        this.flash = when (number) {
            0 -> ZERO
            1 -> ONE
            2 -> ZERO
            3 -> ZERO
            4 -> ZERO
            5 -> ONE
            6 -> ZERO
            7 -> ONE
            else -> ZERO
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
     * 移動方向反転(X座標)
     */
    private fun changeDirectionX() {
        directionX *= -1
    }

    /**
     * ボールの位置の更新
     * @param deltaTime 更新間隔
     */
    fun update(deltaTime:Float) {
        timeStraightDirectionX += deltaTime
        if (timeStraightDirectionX > Time_Change_Direction) {
            changeDirectionX()
            timeStraightDirectionX = 0f
        }
        if (isWallCollided) {
            changeDirectionX()
            isWallCollided = false
        }

        velocity.add(0f, GameState.gravity.y * deltaTime)
        position.add(directionX, velocity.y * deltaTime)
        this.image.setPosition(position.x, position.y)
    }

    /**
     * ボールのカラーコードを取得する
     * @param number ボールの番号
     */
    fun getColorCode(number: Int): String {
        val colorCode = when (number) {
            0 -> "0000ff"
            1 -> "00ff00"
            2 -> "00ffff"
            3 -> "3200ff"
            4 -> "fcee21"
            5 -> "ff0000"
            6 -> "ff0064"
            7 -> "ffffff"
            else -> "unknown"
        }
        return colorCode
    }
}
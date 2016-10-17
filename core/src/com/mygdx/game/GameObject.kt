package com.mygdx.game

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * このオブジェクトのイメージ、初期位置を設定
 * @param x このオブジェクトの初期位置(X座標)
 * @param y このオブジェクトの初期位置(Y座標)
 * @param image このオブジェクトのイメージ
 */
open class GameObject(x:Float, y:Float, image:Image) {
    var position: Vector2
    protected set

    var image: Image
    protected set

    init {
        this.position = Vector2(x, y)
        this.image = image
    }

    /**
     * オブジェクトの大きさを設定
     */
    fun setSize(x:Float, y:Float) {
        image.setSize(x, y)
    }

    /**
     * オブジェクトが拡大・回転する際の中心を設定
     */
    fun setOrigin(x:Float, y:Float) {
        image.setOrigin(x, y)
    }
}
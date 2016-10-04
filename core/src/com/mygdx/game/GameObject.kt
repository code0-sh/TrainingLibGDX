package com.mygdx.game

import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * このオブジェクトのイメージ、初期位置を設定
 * @param x このオブジェクトの初期位置(X座標)
 * @param y このオブジェクトの初期位置(Y座標)
 * @param image このオブジェクトのイメージ
 */
open class GameObject(x:Float, y:Float, image:Image) {
    var image: Image
    protected set

    var x = 0f
    protected set

    var y = 0f
    protected set

    init{
        this.image = image
        updatePosition(x, y)
    }

    /**
     * このオブジェクトの左頂点値を返却
     * @return 左頂点の値
     */
    fun getLeft(): Float {
        return x
    }

    /**
     * このオブジェクトの右頂点値を返却
     * @return 右頂点の値
     */
    fun getRight(): Float {
        return x + image.width
    }

    /**
     * このオブジェクトの上頂点値を返却
     * @return 上頂点の値
     */
    fun getUp(): Float {
        return y + image.height
    }

    /**
     * このオブジェクトの下頂点値を返却
     * @return 下頂点の値
     */
    fun getDown(): Float {
        return y
    }

    fun setSize(x:Float, y:Float) {
        image.setSize(x, y)
    }

    fun setOrigin(x:Float, y:Float) {
        image.setOrigin(x, y)
    }
    fun updatePosition(x:Float, y:Float) {
        this.x = x
        this.y = y
        image.setPosition(this.x, this.y)
    }
}
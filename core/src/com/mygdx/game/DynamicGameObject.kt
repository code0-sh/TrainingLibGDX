package com.mygdx.game

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image

open class DynamicGameObject(x:Float, y:Float, image: Image) : GameObject(x, y, image) {
    val velocity: Vector2
    val acceleration: Vector2

    init {
        velocity = Vector2()
        acceleration = Vector2()
    }
}
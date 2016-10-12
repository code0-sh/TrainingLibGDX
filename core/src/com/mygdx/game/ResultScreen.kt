package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class ResultScreen(game: SnakeGame) : Screen {
    private lateinit var stage: Stage
    private lateinit var camera: OrthographicCamera
    private lateinit var uiCamera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var bgImg: Texture
    private lateinit var bg: Sprite
    private lateinit var batch: SpriteBatch

    internal val game: SnakeGame

    init {
        this.game = game
    }

    override fun pause() {

    }
    override fun hide() {

    }

    override fun resume() {

    }

    override fun render(delta: Float) {

        // tell the camera to update its matrices.
        camera.update()
        batch.projectionMatrix = camera.combined

        uiCamera.update()
        batch.projectionMatrix = uiCamera.combined

        batch.begin()
        bg.draw(batch)
        batch.end()

    }

    override fun show() {
        batch = SpriteBatch()
        bgImg = Texture("bg.png")
        bg = Sprite(bgImg)
        bg.setScale(2.0f, 2.0f)
        bg.setPosition(0f, 0f)

        // Camera
        camera = OrthographicCamera(800f, 480f)
        camera.setToOrtho(false, 800f, 480f)

        uiCamera = OrthographicCamera()
        uiCamera.setToOrtho(false, 800f, 480f)

        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Viewport
        viewport = FitViewport(800f, 480f, camera)

        // Stage
        stage = Stage()
        Gdx.input.inputProcessor = stage
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
    }
}
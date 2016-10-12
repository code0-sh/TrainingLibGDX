package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class BallGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var assetManager: Assets
    lateinit var camera: OrthographicCamera
    lateinit var uiCamera: OrthographicCamera
    lateinit var viewport: Viewport

    override fun create() {
        println("BallGame create")

        // Camera
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)

        uiCamera = OrthographicCamera()
        uiCamera.setToOrtho(false, 800f, 480f)

        // Viewport
        viewport = FitViewport(800f, 480f, camera)

        assetManager = Assets()
        batch = SpriteBatch()
        setScreen(MainScreen(this))
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // tell the camera to update its matrices.
        camera.update()
        batch.projectionMatrix = camera.combined

        uiCamera.update()
        batch.projectionMatrix = uiCamera.combined

        super.render()
    }

    override fun resize(width: Int, height: Int) {
        println("BallGame resize")
        viewport.update(width, height)
    }

    override fun dispose() {
        println("BallGame dispose")
        batch.dispose()
        assetManager.dispose()
    }
}

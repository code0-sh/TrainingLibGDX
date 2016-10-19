package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import java.lang.ref.WeakReference

class BallGame(endGame: WeakReference<EndGame>) : Game() {
    lateinit var batch: SpriteBatch
    lateinit var assets: Assets
    lateinit var camera: OrthographicCamera
    lateinit var uiCamera: OrthographicCamera
    lateinit var viewport: Viewport

    internal var endGame_: WeakReference<EndGame>

    init {
        this.endGame_ = endGame
    }

    override fun create() {
        println("BallGame create")

        // Camera
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        uiCamera = OrthographicCamera()
        uiCamera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        // Viewport
        viewport = FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera)

        assets = Assets()
        batch = SpriteBatch()

        setScreen(MainScreen(WeakReference<BallGame>(this)))
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
        assets.dispose()
        endGame_.clear()
    }
}

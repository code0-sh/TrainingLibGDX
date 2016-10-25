package com.mygdx.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas


class Assets {
    var assetManager: AssetManager
    private set

    lateinit var ballAtlas: List<TextureAtlas.AtlasRegion>
    private set

    var finishTexture: Texture
    private set

    var retryTexture: Texture
    private set

    companion object {
        /**
         * ボールのテクスチャアトラスファイル
         */
        private const val BALLS_RESOURCE = "balls.txt"

        /**
         * 「GAMEを終了」画像
         */
        private const val Finish_RESOURCE = "finish.png"

        /**
         * 「もう一度遊ぶ」画像
         */
        private const val Retry_RESOURCE = "retry.png"
    }

    init {
        assetManager = AssetManager()
        loadResource()
        createBallAtlas()
        finishTexture = assetManager.get(Finish_RESOURCE, Texture::class.java)
        retryTexture = assetManager.get(Retry_RESOURCE, Texture::class.java)
    }

    /**
     * AssetManagerを破棄する
     */
    fun dispose() {
        assetManager.dispose()
        finishTexture.dispose()
        retryTexture.dispose()
    }

    /**
     * テクスチャアトラスファイルをロードする
     */
    private fun loadResource() {
        assetManager.load(BALLS_RESOURCE, TextureAtlas::class.java)
        assetManager.load(Finish_RESOURCE, Texture::class.java)
        assetManager.load(Retry_RESOURCE, Texture::class.java)
        assetManager.finishLoading()
    }

    /**
     * AssetManagerからボール(TextureAtlas)のリストを生成する
     */
    private fun createBallAtlas() {
        val atlas = assetManager.get(BALLS_RESOURCE, TextureAtlas::class.java)

        val ball_blue = atlas.findRegion("blue")
        val ball_lime = atlas.findRegion("lime")
        val ball_cyan = atlas.findRegion("cyan")
        val ball_purple = atlas.findRegion("purple")
        val ball_yellow = atlas.findRegion("yellow")
        val ball_red = atlas.findRegion("red")
        val ball_pink = atlas.findRegion("pink")
        val ball_white = atlas.findRegion("white")

        ballAtlas = listOf<TextureAtlas.AtlasRegion>(ball_blue,
                ball_lime,
                ball_cyan,
                ball_purple,
                ball_yellow,
                ball_red,
                ball_pink,
                ball_white)
    }
}
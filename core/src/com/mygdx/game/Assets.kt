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

        val ball_0000ff = atlas.findRegion("0000ff")
        val ball_00ff00 = atlas.findRegion("00ff00")
        val ball_00ffff = atlas.findRegion("00ffff")
        val ball_0300ff = atlas.findRegion("0300ff")
        val ball_3200ff = atlas.findRegion("3200ff")
        val ball_ff0000 = atlas.findRegion("ff0000")
        val ball_ff0064 = atlas.findRegion("ff0064")
        val ball_ffffff = atlas.findRegion("ffffff")

        ballAtlas = listOf<TextureAtlas.AtlasRegion>(ball_0000ff,
                ball_00ff00,
                ball_00ffff,
                ball_0300ff,
                ball_3200ff,
                ball_ff0000,
                ball_ff0064,
                ball_ffffff)
    }
}
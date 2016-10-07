package com.mygdx.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas


class Assets {
    var assetManager: AssetManager
    private set

    lateinit var ballAtlas: List<TextureAtlas.AtlasRegion>
    private set

    companion object {
        /**
         * ボールのテクスチャアトラスファイル
         */
        private const val BALLS_RESOURCE = "balls.txt"

        /**
         * AssetManagerを破棄する
         */
        fun dispose() {
            this.dispose()
        }
    }

    init {
        assetManager = AssetManager()
        this.loadResource()
        this.createBallAtlas()
    }

    /**
     * テクスチャアトラスファイルをロードする
     */
    private fun loadResource() {
        assetManager.load(BALLS_RESOURCE, TextureAtlas::class.java)
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

        this.ballAtlas = listOf<TextureAtlas.AtlasRegion>(ball_0000ff,
                ball_00ff00,
                ball_00ffff,
                ball_0300ff,
                ball_3200ff,
                ball_ff0000,
                ball_ff0064,
                ball_ffffff)
    }
}
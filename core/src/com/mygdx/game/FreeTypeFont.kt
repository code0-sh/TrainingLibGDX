package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label


class FreeTypeFont(text: String) {

    private var x: Float = 0f
    private var y: Float = 0f
    private var fontSize = 45
    private var fontColor = Color.WHITE
    private lateinit var labelStyle: Label.LabelStyle
    internal var label: Label

    init {
        setLabelStyle()
        label = Label(text, labelStyle)
    }

    /**
     * labelの位置を設定
     * @param x x座標
     * @param y y座標
     */
    fun setPosition(x:Float, y:Float) {
        label.setPosition(x, y)
    }

    /**
     * labelのテキストを設定
     * @param text テキスト
     */
    fun setText(text: String) {
        label.setText(text)
    }

    /**
     * labelのフォントサイズを設定
     * @param fontSize フォントサイズ
     */
    fun setFontSize(fontSize: Int) {
        this.fontSize = fontSize
        setLabelStyle()
        label = Label(label.text, labelStyle)
        label.setPosition(x, y)
        label.color = fontColor
    }

    /**
     * labelの位置を画面下中央に設定
     * @param bottom 画面下部からの距離
     */
    fun setCenterPosition(bottom: Float) {
        val font = createFont(fontSize)
        val layout = GlyphLayout(font, label.text)
        val fontX = (Gdx.graphics.width - layout.width) / 2
        val fontY = bottom
        label.setPosition(fontX, fontY)
    }

    /**
     * labelの色を設定
     * @param fontColor フォントカラー
     */
    fun setColor(fontColor: Color) {
        this.fontColor = fontColor
        label.color = fontColor
    }

    /**
     * FreeTypeFontを生成
     * @param fontSize フォントサイズ
     */
    private fun createFont(fontSize: Int): BitmapFont? {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font/" + "GenEiGothicM-Regular.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = fontSize
        parameter.characters = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをんABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!-1234567890現在ライトアップエリアでボールタップ照明色変化: "
        return generator.generateFont(parameter)
    }

    /**
     * LabelStyleを設定
     */
    private fun setLabelStyle() {
        val font = createFont(fontSize)
        labelStyle = Label.LabelStyle(font, fontColor)

    }
}
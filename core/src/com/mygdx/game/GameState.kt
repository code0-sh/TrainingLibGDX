package com.mygdx.game

import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Ball.FlashNumber

object GameState {
    private var scores: MutableList<Int> = mutableListOf()
    // 一番多く選択されたボールの選択回数
    internal var score = 0
    // 一番多く選択されたボールの番号
    internal var number = 0
    // 一番多く選択されたボールの色のカラーコード
    internal var colorCode = ""
    // 認識されたBeaconのメジャー番号
    internal var major = -1
    // タイマーの時間
    internal var time = 8
    // 重力加速度
    internal val gravity = Vector2(0f, -98f)
    // フラッシュ
    internal var flashNumber: Int = 0

    /**
     * ゲーム状態の初期化
     */
    fun initGameSate() {
        scores = mutableListOf()
        for (num in 0..7) {
            scores.add(num, 0)
        }
        score = 0
        number = 0
        major = -1
        time = 8
        colorCode = ""
    }

    /**
     * 以下の値の更新
     * 一番多く選択されたボールの選択回数
     * 一番多く選択されたボールの番号
     * フラッシュ対象のボール判定
     * @param ball 選択されたボール
     */
    fun update(ball: Ball) {
        scores[ball.number] += 1
        for (num in scores.indices) {
            if (score < scores[num]) {
                score = scores[num]
                number = num
                colorCode = ball.getColorCode(num)
            }
        }
        if (ball.flash == FlashNumber.ONE) {
            flashNumber = FlashNumber.ONE.number
        }
    }
}
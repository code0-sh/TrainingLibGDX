package com.mygdx.game

object GameState {
    private var scores: MutableList<Int> = mutableListOf()
    internal var score = 0
    internal var number = 0
    internal var major = -1
    internal var time = 8
    internal var isOnTimer = false
    internal const val gravity = -98f

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
        isOnTimer = false
    }

    /**
     * スコアと名前の更新
     * @param index
     */
    fun update(index: Int) {
        scores[index] += 1
        for (num in scores.indices) {
            if (score < scores[num]) {
                score = scores[num]
                number = num
            }
        }
    }
}
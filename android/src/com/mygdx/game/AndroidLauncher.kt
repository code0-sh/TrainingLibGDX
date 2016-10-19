package com.mygdx.game

import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import com.badlogic.gdx.backends.android.AndroidFragmentApplication

class AndroidLauncher : FragmentActivity(), AndroidFragmentApplication.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()

        val fragment = GameFragment()
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
    }

    /**
     * 指定したフラグメントに遷移する（置き換え）
     *
     * @param fragment 遷移先のフラグメント
     */
    fun replaceFragment(fragment: Fragment) {
        fragmentManager
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit()
    }

    /**
     * トップ画面を表示する
     */
    fun showTop() {
        this.runOnUiThread {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            replaceFragment(FirstFragment())
        }
    }

    override fun exit() {
    }
}
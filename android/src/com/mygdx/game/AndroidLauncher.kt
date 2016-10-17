package com.mygdx.game

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.backends.android.AndroidFragmentApplication

class AndroidLauncher : FragmentActivity(), AndroidFragmentApplication.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = FirstFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(android.R.id.content, fragment)
        transaction.commit()
    }

    class GameFragment : AndroidFragmentApplication() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
            return initializeForView(BallGame())
        }
    }

    override fun exit() {
    }
}
package com.mygdx.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import java.lang.ref.WeakReference

/**
 * ゲーム画面のFragment
 */
class GameFragment : AndroidFragmentApplication(), EndGame {

    private lateinit var activity: WeakReference<AndroidLauncher>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        activity = WeakReference<AndroidLauncher>(getActivity() as? AndroidLauncher)
        return initializeForView(BallGame(WeakReference<EndGame>(this)))
    }
    override fun onDestroy() {
        super.onDestroy()
        activity.clear()
    }

    /**
     * トップ画面に戻る
     */
    override fun returnToTop() {
        val activity = activity.get() ?: return
        activity.showTop()
    }
}

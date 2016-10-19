package com.mygdx.game

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.ref.WeakReference

class FirstFragment : Fragment() {

    var activity: WeakReference<AndroidLauncher>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = WeakReference<AndroidLauncher>(getActivity() as? AndroidLauncher)
        val view = inflater!!.inflate(R.layout.fragment_first, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.clear()
        activity = null
    }
}


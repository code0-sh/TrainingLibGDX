package com.mygdx.game

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import java.lang.ref.WeakReference

class FirstFragment : Fragment() {

    private lateinit var activity: WeakReference<AndroidLauncher>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = WeakReference<AndroidLauncher>(getActivity() as? AndroidLauncher)
        val view = inflater!!.inflate(R.layout.fragment_first, container, false)

        val button = view.findViewById(R.id.button) as Button
        button.setOnClickListener {
            // Game 再スタート
            val androidLauncher = Intent(getActivity(), AndroidLauncher::class.java)
            getActivity().startActivity(androidLauncher)
        }

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
        activity.clear()
    }
}
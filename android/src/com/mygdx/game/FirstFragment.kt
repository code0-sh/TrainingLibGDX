package com.mygdx.game

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button

class FirstFragment : Fragment(), OnClickListener {
    override fun onClick(v: View?) {
        val transaction = fragmentManager.beginTransaction()
        val fragment = AndroidLauncher.GameFragment()
        transaction.replace(android.R.id.content, fragment)
        transaction.commit()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_first, container, false)
        // ボタンを取得して、ClickListenerをセット
        val btn = view.findViewById(R.id.button) as Button
        btn.setOnClickListener(this)
        return view
    }
}
package com.idapgroup.artemhuminkiy.skillincreaseapp.introScreen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants


class WelcomeSlideFragment : Fragment() {

    companion object {

        fun newInstance(resource: Int) : WelcomeSlideFragment{
            val frag = WelcomeSlideFragment()
            val arg = Bundle()
            arg.putInt(Constants.LAYOUT,resource)
            frag.arguments = arg
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View{
        val bundle = arguments!!.getInt(Constants.LAYOUT)
        return inflater.inflate(bundle,container,false)
    }
}
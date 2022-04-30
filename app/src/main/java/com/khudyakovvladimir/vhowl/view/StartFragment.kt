package com.khudyakovvladimir.vhowl.view

import androidx.navigation.fragment.findNavController
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.khudyakovvladimir.vhowl.app.appComponent
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModel
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModelFactory
import com.khudyakovvladimir.vhowl.R
import com.khudyakovvladimir.vhowl.app.data
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import kotlinx.android.synthetic.main.start_fragment_layout.*
import javax.inject.Inject

class StartFragment: Fragment() {

    @Inject
    lateinit var factory: GameViewModelFactory.Factory
    lateinit var gameViewModel: GameViewModel
    lateinit var gameViewModelFactory: GameViewModelFactory

    @Inject
    lateinit var systemHelper: SystemHelper

    @Inject
    lateinit var soundHelper: SoundHelper

    lateinit var constraintLayout: ConstraintLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectStartFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.start_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constraintLayout = view.findViewById(R.id.constraintLayout)
        systemHelper.fadeInView(constraintLayout, 1000)

        owlSleep.setOnClickListener {
            soundHelper.playSoundMouseWin(false)
            activity?.finishAndRemoveTask()
        }
        owlReady.setOnClickListener {
            soundHelper.playSoundOwl(false)
            findNavController().navigate(R.id.gameFragment)
        }

        gameViewModelFactory = factory.createGameViewModelFactory(activity!!.application)
        gameViewModel = ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)

        textViewTime.text = context!!.data.time
        textViewCount.text = "${context!!.data.countOfMouse}"

        context!!.data.countOfMouse = 0

        Log.d("TAG", "app mouse = ${context!!.data.countOfMouse}")
        Log.d("TAG", "app time = ${context!!.data.time}")
    }
}
package com.khudyakovvladimir.vhowl.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.khudyakovvladimir.vhowl.game.GameView
import com.khudyakovvladimir.vhowl.app.appComponent
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModel
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModelFactory
import com.khudyakovvladimir.vhowl.R
import javax.inject.Inject

class GameFragment: Fragment() {

    @Inject
    lateinit var factory: GameViewModelFactory.Factory
    lateinit var gameViewModel: GameViewModel
    lateinit var gameViewModelFactory: GameViewModelFactory

//    lateinit var buttonPlay: Button
//    lateinit var buttonPause: Button
//    lateinit var buttonStop: Button
//    lateinit var buttonClear: Button

    lateinit var linearLayout: LinearLayout

    @Inject
    lateinit var systemHelper: SystemHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectGameFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.canvas_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initViews()
       // setListeners()

        linearLayout = view.findViewById(R.id.linearLayout)
        linearLayout.addView(GameView(activity!!.applicationContext))

        gameViewModelFactory = factory.createGameViewModelFactory(activity!!.application)
        gameViewModel = ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)
    }

//    private fun initViews() {
//        buttonPlay = view!!.findViewById(R.id.buttonPlay)
//        buttonPause = view!!.findViewById(R.id.buttonPause)
//        buttonStop = view!!.findViewById(R.id.buttonStop)
//        buttonClear = view!!.findViewById(R.id.buttonClear)
//        //surfaceDrawView = view!!.findViewById(R.id.surfaceDrawView)
//    }
//
//    private fun setListeners() {
//        buttonPlay.setOnClickListener {  }
//        buttonPause.setOnClickListener {  }
//        buttonStop.setOnClickListener {
//            Log.d("TAG", "buttonStop is pressed")
//            //            Game.turnSnake(Direction.UP)
//            //surfaceDrawView.drawThread?.isDrawThreadRunning = false
//        }
//        buttonClear.setOnClickListener {  }
//    }
}
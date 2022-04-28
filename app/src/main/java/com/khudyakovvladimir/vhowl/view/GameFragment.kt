package com.khudyakovvladimir.vhowl.view

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import javax.inject.Inject

class GameFragment: Fragment() {

    @Inject
    lateinit var factory: GameViewModelFactory.Factory
    lateinit var gameViewModel: GameViewModel
    lateinit var gameViewModelFactory: GameViewModelFactory

    lateinit var linearLayout: LinearLayout

    var startTime = 0
    var endTime = 0

    @Inject
    lateinit var systemHelper: SystemHelper

    @Inject
    lateinit var soundHelper: SoundHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectGameFragment(this)

        startTime = System.currentTimeMillis().toInt()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayout = view.findViewById(R.id.linearLayout)
        linearLayout.addView(GameView(activity!!.applicationContext))

        gameViewModelFactory = factory.createGameViewModelFactory(activity!!.application)
        gameViewModel = ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)

        //soundHelper.playSoundWind(false)
    }

    override fun onStop() {
        super.onStop()
        var word = ""
        endTime = (System.currentTimeMillis().toInt() - startTime) / 1000
        if(endTime == 1) {
            word = "Вы продержались $endTime секунду"
        }
        if(endTime in 2..4) {
            word = "Вы продержались $endTime секунды"
        }
        if(endTime in 5..59) {
            word = "Вы продержались $endTime секунд"
        }
        if(endTime > 60) {
            word = "Вы продержались ${endTime.toDouble() / 60} минут"
        }
        Log.d("TAG", "endTime = $word")
        systemHelper.time = word
    }
}
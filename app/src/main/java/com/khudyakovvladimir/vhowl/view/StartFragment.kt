package com.khudyakovvladimir.vhowl.view

import androidx.navigation.fragment.findNavController
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.khudyakovvladimir.vhowl.app.appComponent
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModel
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModelFactory
import com.khudyakovvladimir.vhowl.R
import com.khudyakovvladimir.vhowl.app.data
import com.khudyakovvladimir.vhowl.database.HighScore
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import kotlinx.android.synthetic.main.start_fragment_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectStartFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.start_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        systemHelper.fadeInView(constraintLayout, 1000)

        owlSleep.setOnClickListener {
            soundHelper.playSoundMouseWin(false)
            activity?.finishAndRemoveTask()
        }
        owlReady.setOnClickListener {
            soundHelper.playSoundOwl(false)
            findNavController().navigate(R.id.gameFragment)
        }

        imageView.setOnClickListener {
            findNavController().navigate(R.id.highScoreFragment)
        }

        gameViewModelFactory = factory.createGameViewModelFactory(activity!!.application)
        gameViewModel = ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)

        textViewTime.text = context!!.data.time
        textViewCount.text = "${context!!.data.countOfMouse}"

        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {

                val currentScore = context!!.data.countOfMouse
                val list = gameViewModel.highScoreDao.getAllHighScore()
                val leader = list[0]
                if(currentScore > leader.score) {
                    gameViewModel.highScoreDao.insertHighScore(HighScore(0, "New Hero", currentScore))
                    imageView.setImageResource(R.drawable.cup)
                    systemHelper.pulse(imageView, context!!)
                }
            }
            job.join()
            context!!.data.countOfMouse = 0
        }

    }
}
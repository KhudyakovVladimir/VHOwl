package com.khudyakovvladimir.vhowl.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.khudyakovvladimir.vhowl.app.appComponent
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModel
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModelFactory
import com.khudyakovvladimir.vhowl.R
import com.khudyakovvladimir.vhowl.app.data
import com.khudyakovvladimir.vhowl.database.DBHelper
import com.khudyakovvladimir.vhowl.database.HighScore
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import kotlinx.android.synthetic.main.game_fragment_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameFragment: Fragment() {

    @Inject
    lateinit var factory: GameViewModelFactory.Factory
    lateinit var gameViewModel: GameViewModel
    lateinit var gameViewModelFactory: GameViewModelFactory

    private var startTime = 0
    private var endTime = 0

    var count = 0
    private var isDatabaseCreated = false

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
        gameViewModelFactory = factory.createGameViewModelFactory(activity!!.application)
        gameViewModel = ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)

        val sharedPreferences = activity?.applicationContext!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)

        if (sharedPreferences.contains("database")) {
            isDatabaseCreated = sharedPreferences.getBoolean("database", false)
        }

        if (!isDatabaseCreated) {
            val dbHelper = activity?.let { DBHelper(it) }
            dbHelper?.createDatabase()

            CoroutineScope(Dispatchers.IO).launch {
                gameViewModel.highScoreDao.insertHighScore(HighScore(0, "Owl", context!!.data.countOfMouse))
            }

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("database", true)
            editor.apply()
        }

        imageViewPause.setOnClickListener {
            if((count % 2) == 0) {
                context!!.data.isPause = true
                imageViewPause.setBackgroundResource(R.drawable.ready_owl)
                count++
            }else {
                context!!.data.isPause = false
                imageViewPause.setBackgroundResource(R.drawable.sleeping_owl)
                count++
            }
        }
    }

    override fun onStop() {
        super.onStop()
        endTime = System.currentTimeMillis().toInt()
        systemHelper.getGameSessionTime(startTime, endTime, context!!)
    }
}
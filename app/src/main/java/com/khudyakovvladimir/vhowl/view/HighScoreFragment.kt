package com.khudyakovvladimir.vhowl.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.khudyakovvladimir.vhowl.R
import com.khudyakovvladimir.vhowl.app.appComponent
import com.khudyakovvladimir.vhowl.app.data
import com.khudyakovvladimir.vhowl.database.DBHelper
import com.khudyakovvladimir.vhowl.database.HighScore
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModel
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModelFactory
import kotlinx.android.synthetic.main.high_score_fragment_layout.*
import kotlinx.android.synthetic.main.start_fragment_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HighScoreFragment: Fragment() {
    @Inject
    lateinit var factory: GameViewModelFactory.Factory
    lateinit var gameViewModel: GameViewModel
    lateinit var gameViewModelFactory: GameViewModelFactory

    @Inject
    lateinit var systemHelper: SystemHelper

    @Inject
    lateinit var soundHelper: SoundHelper

    private var isDatabaseCreated = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectHighScoreFragment(this)

        val sharedPreferences = activity?.applicationContext!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)

        if (sharedPreferences.contains("database")) {
            Log.d("TAG", "DB is created")
            isDatabaseCreated = sharedPreferences.getBoolean("database", false)
        }

        if (!isDatabaseCreated) {
            Log.d("TAG", "DB is NOT created")
            val dbHelper = activity?.let { DBHelper(it) }
            dbHelper?.createDatabase()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("database", true)
            editor.apply()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.high_score_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModelFactory = factory.createGameViewModelFactory(activity!!.application)
        gameViewModel =ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)

        gameViewModel.getListOfHighScore()?.observe(this) {
            Log.d("TAG", "list = $it")
            textView.text = it.toString()
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //do something what you need
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }
}
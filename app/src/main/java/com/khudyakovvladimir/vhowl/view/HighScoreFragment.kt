package com.khudyakovvladimir.vhowl.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khudyakovvladimir.vhowl.recyclerview.HighScoreAdapter
import com.khudyakovvladimir.vhowl.R
import com.khudyakovvladimir.vhowl.app.appComponent
import com.khudyakovvladimir.vhowl.database.HighScore
import com.khudyakovvladimir.vhowl.utils.SoundHelper
import com.khudyakovvladimir.vhowl.utils.SystemHelper
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModel
import com.khudyakovvladimir.vhowl.viewmodel.GameViewModelFactory
//import kotlinx.android.synthetic.main.high_score_fragment_layout.*
import javax.inject.Inject

class HighScoreFragment: Fragment() {
    @Inject
    lateinit var factory: GameViewModelFactory.Factory
    lateinit var gameViewModel: GameViewModel
    lateinit var gameViewModelFactory: GameViewModelFactory

    private lateinit var recyclerView: RecyclerView
    private lateinit var highScoreAdapter: HighScoreAdapter

    @Inject
    lateinit var systemHelper: SystemHelper

    @Inject
    lateinit var soundHelper: SoundHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectHighScoreFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.high_score_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModelFactory = factory.createGameViewModelFactory(activity!!.application)
        gameViewModel =ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)

        val list = listOf(HighScore(0, "Owl", 0))

        highScoreAdapter =
            HighScoreAdapter(
                activity!!.applicationContext,
                list,
                )
        recyclerView.adapter = highScoreAdapter

        gameViewModel.getListOfHighScore()?.observe(this) {
            Log.d("TAG", "list = $it")
            highScoreAdapter.list = it
            highScoreAdapter.notifyDataSetChanged()
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //do something what you need
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }
}
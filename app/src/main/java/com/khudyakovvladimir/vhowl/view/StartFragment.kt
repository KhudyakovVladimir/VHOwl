package com.khudyakovvladimir.vhowl.view

import androidx.navigation.fragment.findNavController
import android.content.Context
import android.os.Bundle
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
import com.khudyakovvladimir.vhowl.game.OwlReady
import com.khudyakovvladimir.vhowl.game.OwlSleep
import javax.inject.Inject

class StartFragment: Fragment() {

    @Inject
    lateinit var factory: GameViewModelFactory.Factory
    lateinit var gameViewModel: GameViewModel
    lateinit var gameViewModelFactory: GameViewModelFactory

    @Inject
    lateinit var systemHelper: SystemHelper

    lateinit var owlSleep: OwlSleep
    //lateinit var owlReady: OwlReady
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

        owlSleep = view.findViewById(R.id.owlSleep)
       // owlReady = view.findViewById(R.id.owlReady)

        owlSleep.setOnClickListener { activity?.finishAndRemoveTask() }
        //owlReady.setOnClickListener { findNavController().navigate(R.id.gameFragment) }

        constraintLayout = view.findViewById(R.id.constraintLayout)
        systemHelper.fadeInView(constraintLayout, 1000)

        gameViewModelFactory = factory.createGameViewModelFactory(activity!!.application)
        gameViewModel = ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)
    }
}
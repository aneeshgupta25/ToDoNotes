package com.aneesh.todonotesfinal.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.aneesh.todonotesfinal.R

class OnBoardingTwoFragment : Fragment() {

    lateinit var buttonDone : Button
    lateinit var buttonBack : Button
    lateinit var onOptionsClick : OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionsClick = context as OnOptionClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(view)
    }

    private fun onBindView(view : View) {
        buttonDone = view.findViewById(R.id.buttonDone)
        buttonBack = view.findViewById(R.id.buttonBack)
        clickListener()
    }

    private fun clickListener() {
        buttonDone.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionsClick.onOptionDone()
            }
        })

        buttonBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionsClick.onOptionBack()
            }
        })
    }

    interface OnOptionClick{
        fun onOptionBack()
        fun onOptionDone()
    }
}
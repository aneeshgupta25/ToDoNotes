package com.aneesh.todonotesfinal.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aneesh.todonotesfinal.R

class OnBoardingTwoFragment : Fragment() {

    lateinit var textViewDone : TextView
    lateinit var textViewBack : TextView
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
        textViewDone = view.findViewById(R.id.textViewDone)
        textViewBack = view.findViewById(R.id.textViewBack)
        clickListener()
    }

    private fun clickListener() {
        textViewDone.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionsClick.onOptionDone()
            }
        })

        textViewBack.setOnClickListener(object : View.OnClickListener{
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
package com.aneesh.todonotesfinal.addNotes.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.addNotes.OnOptionsClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FileSelectorFragment : BottomSheetDialogFragment(){
    companion object{
        fun newInstance() = FileSelectorFragment()
        val TAG = "FileSelectorFragment"
    }

    lateinit var textViewCamera : TextView
    lateinit var textViewGallery : TextView
    lateinit var onOptionsClickListener: OnOptionsClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionsClickListener = context as OnOptionsClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dailog_selector, container, false)
        onBindView(view)
        setUpClickListeners()
        return view
    }

    private fun setUpClickListeners() {
        textViewCamera.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionsClickListener.onCameraClick()
                dismiss()
            }
        })
        textViewGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionsClickListener.onGalleryClick()
                dismiss()
            }
        })
    }

    private fun onBindView(view: View) {
        textViewCamera = view.findViewById(R.id.textViewCamera)
        textViewGallery = view.findViewById(R.id.textViewGallery)
    }
}
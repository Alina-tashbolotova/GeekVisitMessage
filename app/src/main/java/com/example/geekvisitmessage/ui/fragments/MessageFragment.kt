package com.example.geekvisitmessage.ui.fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.geekvisitmessage.databinding.FragmentMessageBinding
import com.example.geekvisitmessage.ui.activity.MainActivity
import com.theartofdev.edmodo.cropper.CropImage

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendMessage()
        initFields()
        sendVoice()

    }

    private fun initFields() = with(binding) {
        editInputMessage.addTextChangedListener {
            val string = editInputMessage.text.toString()
            if (string.isEmpty() || string == "Запись") {
                imageSendMessage.visibility = View.GONE
                imagePhotoMessage.visibility = View.VISIBLE
                imageVoiceMessage.visibility = View.VISIBLE
            } else {
                imageSendMessage.visibility = View.VISIBLE
                imagePhotoMessage.visibility = View.GONE
                imageVoiceMessage.visibility = View.GONE
            }
        }
        imagePhotoMessage.setOnClickListener {
            sendPhoto()

        }
    }

    private fun sendPhoto() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(250, 250)
            .start(activity as MainActivity, this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            findNavController().navigate(
                MessageFragmentDirections.actionMessageFragmentToUserFragment(
                    uri.toString()
                )
            )

        }
    }

    private fun sendMessage() = with(binding) {
        imageSendMessage.setOnClickListener {
            val name = editInputMessage.text
            val hello = "$name"
            findNavController().navigate(
                MessageFragmentDirections.actionMessageFragmentToUserFragment(
                    hello
                )
            )

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun sendVoice() = with(binding) {
        imageVoiceMessage.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // TODO record
                editInputMessage.setText("Запись")
            } else if (event.action == MotionEvent.ACTION_UP) {
                // TODO stop record
                editInputMessage.setText("")

            }

            true
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
package com.aneesh.todonotesfinal.addNotes

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.aneesh.todonotesfinal.BuildConfig
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.addNotes.bottomsheet.FileSelectorFragment
import com.bumptech.glide.Glide
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity(), OnOptionsClickListener{

    companion object{
        private const val REQUEST_CODE_GALLERY = 2
        private const val REQUEST_CODE_CAMERA = 1
        private const val MY_PERMISSION_CODE = 124
    }

    lateinit var editTextTitle : EditText
    lateinit var editTextDescription : EditText
    lateinit var submitButton : Button
    lateinit var imageViewNotes : ImageView
    var picturePath = ""
    lateinit var imageLocation : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        onBindView()
        clickListener()
    }

    private fun clickListener() {
        submitButton.setOnClickListener{

            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            if(title.isNotEmpty() && description.isNotEmpty()){
                val intent = Intent()
                intent.putExtra("title", title)
                intent.putExtra("description", description)
                intent.putExtra("image_path", picturePath)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this@AddNotesActivity,
                    "Title or Description can't be empty",
                    Toast.LENGTH_SHORT).show()
            }
        }

        imageViewNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(checkAndRequestPermission()){
                    openPicker()
                }
            }
        })
    }

    private fun checkAndRequestPermission(): Boolean {
        val permissionCamera = ContextCompat.checkSelfPermission(this@AddNotesActivity, Manifest.permission.CAMERA)
        val permissionStorage = ContextCompat.checkSelfPermission(this@AddNotesActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionNeeded = ArrayList<String>()

        if(permissionStorage != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(permissionCamera != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(Manifest.permission.CAMERA)
        }

        //If the listPermissionNeeded is not empty, we'll request the permission present in the list
        if(listPermissionNeeded.isNotEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toTypedArray<String>(), MY_PERMISSION_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openPicker()
                }
            }
        }
    }

    private fun openPicker() {
        val dialog = FileSelectorFragment.newInstance()
        dialog.show(supportFragmentManager, FileSelectorFragment.TAG)
    }

    private fun createImageFile(): File? {
        //creating a temp file in pictures directory
        //adding a name to the file
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "JPEG_" + timeStamp + "_"
        //Now, we will be needing the storage directory
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }

    private fun onBindView() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        submitButton = findViewById(R.id.submitButton)
        imageViewNotes = findViewById(R.id.imageViewNotes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_CAMERA -> {
                    picturePath = imageLocation.path.toString()
                    Glide.with(this).load(imageLocation.absoluteFile).into(imageViewNotes)
                }
                REQUEST_CODE_GALLERY -> {
                    val selectedImage = data?.data
                    picturePath = selectedImage.toString()
                    Glide.with(this).load(picturePath).into(imageViewNotes)
                }
            }
        }
    }

    override fun onBackPressed() {
        Log.d("AddNotesActivity", "onBack mein aya")
        super.onBackPressed()
    }

    override fun onCameraClick() {
        //Here, we are basically telling the intent that we want to click a picture.
        //Then, we created a temporary file and converted it to a uri ,
        //so that when a picture will be clicked , it will be assigned to the uri.
        //Then, we started the activity to open the camera

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(takePictureIntent.resolveActivity(packageManager) != null){
            var photoFile : File? = null
            //assigning temporary file to photoFile
            try {
                photoFile = createImageFile()
            }catch(e : Exception){

            }
            if(photoFile != null){
                val photoUri = FileProvider.getUriForFile(this@AddNotesActivity,
                    BuildConfig.APPLICATION_ID+".provider",
                    photoFile)
                imageLocation = photoFile
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
            }
        }
    }

    override fun onGalleryClick() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }
}

interface OnOptionsClickListener{
    fun onCameraClick()
    fun onGalleryClick()
}
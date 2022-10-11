package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.bitfit.databinding.ActivityCloudDetailsBinding
import com.tinyappco.databasedemo.date
import java.util.*

class CloudDetailsActivity : AppCompatActivity() {

    private lateinit var dataMgr : DBHandler

    private var existingCloud : Cloud? = null

    private lateinit var binding: ActivityCloudDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCloudDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dataMgr = DBHandler(this)



        val cloud = intent.getSerializableExtra("cloud")
        if (cloud is Cloud){
            existingCloud = cloud
            binding.etType.setText(cloud.type)
            binding.etTitle.setText(cloud.title)
            binding.etDescription.setText(cloud.description)

            val cal = Calendar.getInstance()
            cal.time = cloud.date
            binding.datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                Calendar.DAY_OF_MONTH), null)

            binding.btnAdd.text = getString(R.string.update)
            title = getString(R.string.edit_cloud)
        } else {
            title = getString(R.string.add_cloud)
        }

        // refreshSpinner()

    }



    override fun onStart() {
        super.onStart()
    }

//    private fun refreshSpinner(){
//        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinner.adapter = adapter
//
//        val cloud = existingCloud
//        if (cloud != null){
//            binding.spinner.setSelection(adapter.getPosition(cloud.type))
//        }
//    }

    override fun onResume() {
        super.onResume()
        // refreshSpinner()
    }

    @Suppress("UNUSED_PARAMETER")
    fun addAssignment(v: View){

        val type = binding.etType.text.toString()
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()
        val date = binding.datePicker.date()

        val immutableExistingCloud = existingCloud

        if (immutableExistingCloud != null) {
            immutableExistingCloud.type = type
            immutableExistingCloud.title = title
            immutableExistingCloud.description = description
            immutableExistingCloud.date = binding.datePicker.date()

            dataMgr.update(immutableExistingCloud)
        } else {
            val cloud = existingCloud ?: Cloud(null, type, title, description, date)
            dataMgr.add(cloud)
        }

        finish()
    }
}
package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitfit.databinding.FragmentDiaryBinding
import java.security.AccessController.getContext

/**
 * A simple [Fragment] subclass.
 * Use the [DiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiaryFragment : Fragment() {

    private lateinit var binding: FragmentDiaryBinding

    private lateinit var dataManager: DBHandler
    private lateinit var dataSet : List<Cloud>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_diary, container, false)

        binding = FragmentDiaryBinding.inflate(inflater, container,false)

        dataManager = DBHandler(requireContext().applicationContext)

        refreshDeadlines()

        registerForContextMenu(binding.listView)

        // Update the return statement to return the inflated view from above
        return binding.getRoot();
    }

    companion object {
        fun newInstance(): DiaryFragment {
            return DiaryFragment()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_settings, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        menuInflater.inflate(R.menu.menu_deadlines_context, menu)
//    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_edit){
            val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val componentToEdit = dataSet[info.position]

            val intent = Intent(requireContext().applicationContext, CloudDetailsActivity::class.java)
            intent.putExtra("cloud",componentToEdit)
            startActivity(intent)

            return true
        }

        if (item.itemId == R.id.menu_delete){

            val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val component = dataSet[info.position]
            dataManager.delete(component)
            refreshDeadlines()

            return true
        }

        return super.onContextItemSelected(item)
    }

    private fun refreshDeadlines(){
        dataSet = dataManager.clouds()
        binding.listView.adapter = ArrayAdapter<Cloud>(requireContext().applicationContext,android.R.layout.simple_list_item_1,dataSet)
    }

    override fun onResume() {
        super.onResume()
        refreshDeadlines()
    }
}
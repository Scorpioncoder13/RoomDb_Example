package com.example.roomdatabase.ui.fragment

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.UserListingFragmentBinding
import com.example.roomdatabase.entity.Registration
import com.example.roomdatabase.ui.Adapter.UserListAdapter
import com.example.roomdatabase.viewModel.UserListingViewModel
import kotlinx.coroutines.launch

class UserListingFragment : Fragment() {

    companion object {
        fun newInstance() = UserListingFragment()
    }

    private lateinit var viewModel: UserListingViewModel
    private lateinit var binding: UserListingFragmentBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var data: ArrayList<Registration>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserListingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun swipeToDelete() {
        val simplecallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    deleteRecordFromdb(viewHolder)
                    data.removeAt(viewHolder.adapterPosition)
                    adapter.notifyDataSetChanged()

                    if (data.size == 0) {
                        binding.txtNodata.visibility = View.VISIBLE
                        binding.rvUserlisting.visibility = View.GONE
                    }

                    Toast.makeText(activity, "Deleted Record", +2000).show()
                }

            }

        val itemTouchHelper = ItemTouchHelper(simplecallback)
        itemTouchHelper.attachToRecyclerView(binding.rvUserlisting)
    }

    private fun deleteRecordFromdb(viewHolder: RecyclerView.ViewHolder) {
        viewModel.viewModelScope.launch {
            viewModel.deleteRecordUserIdWise(data[viewHolder.adapterPosition].user_id)
        }
    }

    private fun initview() {
        binding.rvUserlisting.layoutManager = LinearLayoutManager(context)
        getDataFromDb()
    }

    private fun getDataFromDb() {
        viewModel.viewModelScope.launch {
            data = viewModel.getUserList() as ArrayList<Registration>
            Log.d("yash", "getDataFromDb: $data")
            if (data.isNotEmpty()) {
                binding.txtNodata.visibility = View.GONE
                binding.rvUserlisting.visibility = View.VISIBLE
                adapter = UserListAdapter(data, context)
                binding.rvUserlisting.adapter = adapter
            } else {
                binding.txtNodata.visibility = View.VISIBLE
                binding.rvUserlisting.visibility = View.GONE
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserListingViewModel::class.java)
        viewModel = UserListingViewModel(Application())
        initview()
        swipeToDelete()


    }


}
package com.example.roomdatabase.ui.fragment

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.database.RegistrationDb
import com.example.roomdatabase.database.Repository
import com.example.roomdatabase.databinding.RegistrationFormFragmentBinding
import com.example.roomdatabase.entity.Registration
import com.example.roomdatabase.viewModel.RegistrationFormViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

class RegistrationFormFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationFormFragment()
    }

    private lateinit var viewModel: RegistrationFormViewModel
    private lateinit var binding: RegistrationFormFragmentBinding
    lateinit var registrationDb: RegistrationDb
    lateinit var repository: Repository
    var isUpdate = false
    var userid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistrationFormFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                    requireActivity().finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = RegistrationFormViewModel(application = Application())
        binding.showButton.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_registration_to_listFragment)
        }

        getDataFromListing()
        addData()

    }

    private fun getDataFromListing() {
        if (arguments?.getString("isFrom") != null) {
            userid = arguments?.getInt("id")!!
            val isFrom = arguments?.getString("isFrom")
            if (isFrom.equals("btnpress")) {
                binding.registerButton.text = "Update"
                isUpdate = true
                viewModel.viewModelScope.launch {
                    val registration = viewModel.getDataForUpadate(userid)
                    binding.textinputFirstname.text =
                        Editable.Factory.getInstance().newEditable(registration.firstname)
                    binding.textinputLastname.text =
                        Editable.Factory.getInstance().newEditable(registration.lastname)
                    binding.textinputAge.text =
                        Editable.Factory.getInstance().newEditable(registration.age)
                    binding.textinputMobilleno.text =
                        Editable.Factory.getInstance().newEditable(registration.mobileno)
                    binding.textinputEmail.text =
                        Editable.Factory.getInstance().newEditable(registration.email)
                }
            } else {
                clearTextInput()
                binding.registerButton.text = "Registration"

            }

        }


    }

    private fun addData() {

        binding.registerButton.setOnClickListener {
            val firstname = binding.textinputFirstname.text.toString()
            val lastname = binding.textinputLastname.text.toString()
            val age = binding.textinputAge.text.toString()
            val mobilenno = binding.textinputMobilleno.text.toString()
            val emailid = binding.textinputEmail.text.toString()

            if (firstname.isEmpty()) {
                Toast.makeText(context, "Please Enter FirstName", +2000).show()
            } else if (lastname.isEmpty()) {
                Toast.makeText(context, "Please Enter LastName", +2000).show()
            } else if (age.isEmpty()) {
                Toast.makeText(context, "Please Enter Age", +2000).show()
            } else if (mobilenno.isEmpty()) {
                Toast.makeText(context, "Please Enter Mobile No", +2000).show()
            } else if (emailid.isEmpty()) {
                Toast.makeText(context, "Please Enter Email Id", +2000).show()
            } else {
                viewModel.firstname.value = firstname
                viewModel.lastname.value = lastname
                viewModel.age.value = age
                viewModel.mobileno.value = mobilenno
                viewModel.emailid.value = emailid
                viewModel.viewModelScope.launch {
                    if (!isUpdate) {
                        viewModel.addUserData()
                    } else {
                        viewModel.updateUserData(userid)
                    }
                    clearTextInput()
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_registration_to_listFragment)
                }
            }

        }
    }

    private fun clearTextInput() {
        binding.textinputAge.text?.clear()
        binding.textinputEmail.text?.clear()
        binding.textinputFirstname.text?.clear()
        binding.textinputLastname.text?.clear()
        binding.textinputMobilleno.text?.clear()

    }
}
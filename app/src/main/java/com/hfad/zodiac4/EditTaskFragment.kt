package com.hfad.zodiac4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.zodiac4.databinding.FragmentEditTaskBinding

class EditTaskFragment :Fragment(){
    private var _binding: FragmentEditTaskBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_edit_task, container, false)
        _binding=FragmentEditTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId

        //needed to create factory
        val application= requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application  ).taskDao

        val viewModelFactory = EditTaskViewModelFactory(taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditTaskViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if(navigate){
                view.findNavController()
                    .navigate(R.id.action_editTextFragment_to_tasksFragment)
                viewModel.onNavigatedToList()
            }
        } )

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
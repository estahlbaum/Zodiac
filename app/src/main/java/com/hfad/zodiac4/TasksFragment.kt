package com.hfad.zodiac4

import androidx.navigation.fragment.findNavController
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.hfad.zodiac4.databinding.FragmentTasksBinding

class TasksFragment : Fragment(){
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        //gets reference to Dao object to use as TasksViewModelFactory's argument
        //adds TasksViewModel object to Tasks Fragment
        val application = requireNotNull(this.activity).application
        val dao= TaskDatabase.getInstance(application).taskDao
        val viewModelFactory = TasksViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(TasksViewModel::class.java)
        binding.viewModel = viewModel
        //lets layout respond to live data updates
        binding.lifecycleOwner = viewLifecycleOwner

        //recycler view
        //(onClick) val adapter = TaskItemAdapter()
        val adapter =TaskItemAdapter{taskId ->
            viewModel.onTaskClicked(taskId)}
        //(for nav)->Toast.makeText(context, "Clicked task $taskId", Toast.LENGTH_SHORT).show()}
        binding.tasksList.adapter = adapter

        //live data observation
        viewModel.tasks.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapter.submitList(it)
                //adapter.data = it
            }
        })

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                val action = TasksFragmentDirections.actionTasksFragmentToEditTextFragment(taskId)
                this.findNavController().navigate(action)
                viewModel.onTaskNavigated()
            }})

        //assigns fragment's viewModel property to the data binding variable
        //binding.viewModel = viewModel

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


package com.openweathermap.weatherforecast.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openweathermap.weatherforecast.viewmodels.factory.ViewModelFactory
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

abstract class BaseFragment<VM : ViewModel, Binding : ViewDataBinding> : Fragment(),
    DIAware {

    override val di: DI by di()

    private val viewModelFactory: ViewModelFactory by instance()

    protected lateinit var binding: Binding

    protected lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = getFragmentBinding(inflater, container)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModel())

        return binding.root
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): Binding
}
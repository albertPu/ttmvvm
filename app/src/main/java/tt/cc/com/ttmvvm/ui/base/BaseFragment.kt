package tt.cc.com.ttmvvm.ui.main

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    var bind: T? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind =
                DataBindingUtil.inflate(inflater, getContentView(), container, false)
        return bind!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bind?.setLifecycleOwner(this)
        initViewModel(bind)

    }

    open fun initViewModel(binding: T?) {

    }

    abstract fun getContentView(): Int


}
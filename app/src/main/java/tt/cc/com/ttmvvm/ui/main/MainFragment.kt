package tt.cc.com.ttmvvm.ui.main

import android.arch.lifecycle.ViewModelProviders
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.MainFragmentBinding

class MainFragment : BaseFragment<MainFragmentBinding>() {


    override fun getContentView() = R.layout.main_fragment


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel


    override fun initViewModel(binding: MainFragmentBinding?) {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.childFragmentManager=childFragmentManager
        viewModel.touchHome()
        bind?.viewModel = viewModel
    }

}

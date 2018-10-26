package tt.cc.com.ttmvvm.ui.main.home

import android.arch.lifecycle.ViewModelProviders
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.HomeFragmentBinding
import tt.cc.com.ttmvvm.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override fun getContentView() = R.layout.home_fragment

    override fun initViewModel(binding: HomeFragmentBinding?) {
        binding?.viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java).also {
            lifecycle.addObserver(it)
        }

    }
}
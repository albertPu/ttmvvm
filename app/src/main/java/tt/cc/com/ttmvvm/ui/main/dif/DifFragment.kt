package tt.cc.com.ttmvvm.ui.main.dif

import android.arch.lifecycle.ViewModelProviders
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.DifFragmentBinding
import tt.cc.com.ttmvvm.ui.base.BaseFragment

class DifFragment : BaseFragment<DifFragmentBinding>() {

    override fun getContentView() = R.layout.dif_fragment

    override fun initViewModel(binding: DifFragmentBinding?) {
        binding?.viewModel = ViewModelProviders.of(this).get(DifViewModel::class.java)
    }
}
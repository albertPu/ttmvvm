package tt.cc.com.ttmvvm.ui.main.vip

import android.arch.lifecycle.ViewModelProviders
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.VipFragmentBinding
import tt.cc.com.ttmvvm.ui.base.BaseFragment

class VipFragment : BaseFragment<VipFragmentBinding>() {

    override fun getContentView() = R.layout.vip_fragment

    override fun initViewModel(binding: VipFragmentBinding?) {
        binding?.viewModel = ViewModelProviders.of(this).get(VipViewModel::class.java)
    }
}
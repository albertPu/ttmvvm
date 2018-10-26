package tt.cc.com.ttmvvm.ui.main.mine

import android.arch.lifecycle.ViewModelProviders
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.MineFragmentBinding
import tt.cc.com.ttmvvm.ui.base.BaseFragment

class MineFragment : BaseFragment<MineFragmentBinding>() {

    override fun getContentView() = R.layout.mine_fragment

    override fun initViewModel(binding: MineFragmentBinding?) {
        binding?.viewModel = ViewModelProviders.of(this).get(MineViewModel::class.java)
    }
}
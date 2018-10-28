package tt.cc.com.ttmvvm.ui.main.dif

import android.arch.lifecycle.ViewModelProviders
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.DifFragmentBinding
import tt.cc.com.ttmvvm.ui.base.BaseFragment
import java.lang.ref.WeakReference

class DifFragment : BaseFragment<DifFragmentBinding>() {

    override fun getContentView() = R.layout.dif_fragment

    override fun initViewModel(binding: DifFragmentBinding?) {
        binding?.viewModel = ViewModelProviders.of(this).get(DifViewModel::class.java)
        binding?.setLifecycleOwner(this)
        binding?.viewModel?.lifecycleOwner = WeakReference(this)
    }
}
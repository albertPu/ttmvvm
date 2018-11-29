package tt.cc.com.ttmvvm.ui.main.home

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import com.chad.library.adapter.base.BaseQuickAdapter
import org.greenrobot.eventbus.EventBus
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.HomeFragmentBinding
import tt.cc.com.ttmvvm.ui.base.BaseFragment
import tt.cc.com.ttmvvm.ui.video.VideoActivity
import tt.cc.com.ttmvvm.ui.video.VideoFragment
import java.lang.ref.WeakReference

class HomeFragment : BaseFragment<HomeFragmentBinding>() {



    override fun getContentView() = R.layout.home_fragment

    override fun initViewModel(binding: HomeFragmentBinding?) {
        binding?.viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java).also {
            lifecycle.addObserver(it)
            it.lifecycleOwner = WeakReference(this)
        }
    }
}
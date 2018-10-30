package tt.cc.com.ttmvvm.ui.main.home

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import org.greenrobot.eventbus.EventBus
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.HomeFragmentBinding
import tt.cc.com.ttmvvm.model.page.MovieVo
import tt.cc.com.ttmvvm.ui.adapter.reclcerview.OnRVItemClickListener
import tt.cc.com.ttmvvm.ui.base.BaseFragment
import tt.cc.com.ttmvvm.ui.video.VideoActivity
import tt.cc.com.ttmvvm.ui.video.VideoFragment
import java.lang.ref.WeakReference

class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    companion object {
        var START = OnRVItemClickListener { rootView, position, data ->
            val intent = Intent(rootView.context, VideoActivity::class.java)
            EventBus.getDefault().postSticky(data)
            rootView.context?.let {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    val pair = Pair(rootView, VideoFragment.IMG_TRANSITION)
                    val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        rootView.context as Activity, pair
                    )
                    ActivityCompat.startActivity(rootView.context as Activity, intent, activityOptions.toBundle())
                } else {
                    rootView.context.startActivity(intent)
                }
            }

        }
    }

    override fun getContentView() = R.layout.home_fragment

    override fun initViewModel(binding: HomeFragmentBinding?) {
        binding?.viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java).also {
            lifecycle.addObserver(it)
            it.lifecycleOwner = WeakReference(this)
        }
    }
}
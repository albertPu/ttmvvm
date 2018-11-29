package tt.cc.com.ttmvvm.ui.main.home

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.*
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import io.reactivex.rxkotlin.subscribeBy
import org.greenrobot.eventbus.EventBus
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.TtApplication
import tt.cc.com.ttmvvm.model.page.BannerVo
import tt.cc.com.ttmvvm.model.page.MovieVo
import tt.cc.com.ttmvvm.net.Api
import tt.cc.com.ttmvvm.net.ApiStore
import tt.cc.com.ttmvvm.net.ResponseTransformer
import tt.cc.com.ttmvvm.ui.adapter.reclcerview.ItemLayout
import tt.cc.com.ttmvvm.ui.adapter.viewpage.MuPagerItem
import tt.cc.com.ttmvvm.ui.base.BaseViewModel
import tt.cc.com.ttmvvm.ui.video.VideoActivity
import tt.cc.com.ttmvvm.ui.video.VideoFragment
import tt.cc.com.ttmvvm.utlis.showToast

class HomeViewModel : BaseViewModel(), LifecycleObserver {

    private var movieData = ArrayList<MovieVo>()

    private var bannerList = ArrayList<MuPagerItem<BannerVo>>()
    var movieVos = MutableLiveData<ArrayList<MovieVo>>().apply {
        value = movieData
    }

    var pageItems =
        MutableLiveData<ArrayList<MuPagerItem<BannerVo>>>().apply { value = bannerList }

    var recItems = ArrayList<ItemLayout>().apply {
        add(ItemLayout().apply {
            layout = R.layout.item_one_rec
            type = 0
        })
    }


    var isLoading = MutableLiveData<Boolean>().also { it.value = false }
    var page = 0


    @SuppressLint("CheckResult")
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        ApiStore.create(Api::class.java).getBanner().compose(ResponseTransformer.handleResult()).subscribeBy(
            onNext = {
                it.forEach { banner ->
                    bannerList.add(MuPagerItem(R.layout.item_page, banner))
                }
                pageItems.value = bannerList
            },
            onError = {}
        )

        load()
    }


    var layoutManager = GridLayoutManager(TtApplication.context, 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                return 1
            }
        }
    }

    var onLoadMore = OnLoadMoreListener {
        load()
    }

    var itemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, rootView, position ->
        val intent = Intent(rootView.context, VideoActivity::class.java)
        EventBus.getDefault().postSticky(movieData[position])
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

    var itemClickClickListener=BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->

        if(view is TextView){
            showToast(view.text.toString())
        }
    }

    @SuppressLint("CheckResult")
    fun onClick(view: View) {
        showToast("点击了")
    }

    @SuppressLint("CheckResult")
    private fun load() {
        page++
        ApiStore.create(Api::class.java).getMovies(page).compose(ResponseTransformer.handleResult()).subscribeBy(
            onNext = {
                isLoading.value = false
                movieData.clear()
                movieData.addAll(it)
                movieVos.value = movieData
                if (it.isEmpty()) page--
            },
            onError = {}
        )
    }


}
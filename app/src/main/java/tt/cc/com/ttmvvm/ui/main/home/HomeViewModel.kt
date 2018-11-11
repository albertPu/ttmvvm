package tt.cc.com.ttmvvm.ui.main.home

import android.annotation.SuppressLint
import android.arch.lifecycle.*
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import io.reactivex.rxkotlin.subscribeBy
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.TtApplication
import tt.cc.com.ttmvvm.model.page.BannerVo
import tt.cc.com.ttmvvm.model.page.MovieVo
import tt.cc.com.ttmvvm.net.Api
import tt.cc.com.ttmvvm.net.ApiStore
import tt.cc.com.ttmvvm.net.ResponseTransformer
import tt.cc.com.ttmvvm.ui.adapter.reclcerview.MultiRecItem
import tt.cc.com.ttmvvm.ui.adapter.viewpage.MuPagerItem
import tt.cc.com.ttmvvm.ui.base.BaseViewModel
import tt.cc.com.ttmvvm.utlis.showToast

class HomeViewModel : BaseViewModel(), LifecycleObserver {


    private var bannerList = ArrayList<MuPagerItem<BannerVo>>()
    private var movieVos =
        ArrayList<MultiRecItem<MovieVo>>().apply { add(MultiRecItem(R.layout.item_one_rec, ArrayList())) }
    var pageItems =
        MutableLiveData<ArrayList<MuPagerItem<BannerVo>>>().apply { value = bannerList }
    var recItems = MutableLiveData<ArrayList<MultiRecItem<MovieVo>>>().apply { value = movieVos }

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

    var itemClickListener = HomeFragment.start

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
                movieVos[0].data.addAll(it)
                recItems.value = movieVos
                if (it.isEmpty()) page--
            },
            onError = {}
        )
    }


}
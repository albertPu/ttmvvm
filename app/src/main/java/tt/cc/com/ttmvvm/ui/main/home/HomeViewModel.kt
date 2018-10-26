package tt.cc.com.ttmvvm.ui.main.home

import android.annotation.SuppressLint
import android.arch.lifecycle.*
import android.databinding.ObservableArrayList
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.rxkotlin.subscribeBy
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.TtApplication
import tt.cc.com.ttmvvm.model.User
import tt.cc.com.ttmvvm.model.page.BannerVo
import tt.cc.com.ttmvvm.model.page.MovieVo
import tt.cc.com.ttmvvm.net.Api
import tt.cc.com.ttmvvm.net.ApiStore
import tt.cc.com.ttmvvm.net.ResponseTransformer
import tt.cc.com.ttmvvm.ui.adapter.reclcerview.MultiRecItem
import tt.cc.com.ttmvvm.ui.adapter.viewpage.MuPagerItem
import tt.cc.com.ttmvvm.utlis.showToast

class HomeViewModel : ViewModel(), LifecycleObserver {

    var test = MutableLiveData<String>().apply { value = "原始状体" }

    var pageItems = ObservableArrayList<MuPagerItem<BannerVo>>()

    @SuppressLint("CheckResult")
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        ApiStore.create(Api::class.java).getBanner().compose(ResponseTransformer.handleResult()).subscribeBy(
            onNext = {
                it.forEach { banner ->
                    pageItems.add(MuPagerItem(R.layout.item_page, banner))
                }
            },
            onError = {}
        )
        ApiStore.create(Api::class.java).getMovies(1).compose(ResponseTransformer.handleResult()).subscribeBy(
            onNext = {
                recItems.clear()
                recItems.add(MultiRecItem(R.layout.item_one_rec, it))
            },
            onError = {}
        )
    }
    var recItems = ObservableArrayList<MultiRecItem<*>>()

    var layoutManager = GridLayoutManager(TtApplication.context, 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                return 1
            }
        }
    }


    @SuppressLint("CheckResult")
    fun onClick(view: View) {
        showToast("点击了")
    }
}
package tt.cc.com.ttmvvm.ui.video

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.rxkotlin.subscribeBy
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.TtApplication
import tt.cc.com.ttmvvm.model.page.DiscussVo
import tt.cc.com.ttmvvm.model.page.MovieVo
import tt.cc.com.ttmvvm.net.Api
import tt.cc.com.ttmvvm.net.ApiStore
import tt.cc.com.ttmvvm.net.ResponseTransformer
import tt.cc.com.ttmvvm.ui.adapter.reclcerview.MultiRecItem
import tt.cc.com.ttmvvm.ui.adapter.reclcerview.OnRVItemClickListener
import tt.cc.com.ttmvvm.ui.base.BaseViewModel

/**
 *created by Albert
 */
class VideoViewModel : BaseViewModel(), LifecycleObserver {

    var imgUrl = MutableLiveData<String>()
    var movieVos =
        ArrayList<MultiRecItem<Any>>().apply {
            add(MultiRecItem(R.layout.item_video_detail_info, ArrayList()))
            add(MultiRecItem(R.layout.item_two_rec, ArrayList()))
        }

    var items = MutableLiveData<ArrayList<MultiRecItem<Any>>>().apply { value = movieVos }

    var layoutManager = LinearLayoutManager(TtApplication.context)
    var itemClickListener =
        OnRVItemClickListener { _, _, data ->

        }

    @SuppressLint("CheckResult")
    fun load(id: String) {
        ApiStore.create(Api::class.java).getMoviesMore(1, id).compose(ResponseTransformer.handleResult()).subscribeBy(
            onNext = {
                val data = ArrayList<Any>().apply { addAll(it.videoList) }
                val discuss = ArrayList<Any>().apply { addAll(it.discussList) }
                movieVos.add(MultiRecItem(R.layout.item_video_small_card, data))
                movieVos.add(MultiRecItem(R.layout.item_three_rc, ArrayList()))
                movieVos.add(MultiRecItem(R.layout.item_discuss, discuss))
                items.value = movieVos
            },
            onError = {}
        )
    }


}
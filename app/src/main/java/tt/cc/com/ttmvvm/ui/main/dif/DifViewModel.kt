package tt.cc.com.ttmvvm.ui.main.dif

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.view.View
import tt.cc.com.ttmvvm.ui.base.BaseViewModel

class DifViewModel : BaseViewModel(), LifecycleObserver {

    var mutableLiveData = MutableLiveData<List<DifDean>>()
    var lists = ArrayList<DifDean>()

    init {
        lists.apply {
            add(DifDean().apply { name = "方法为" })
        }
        mutableLiveData.value = lists
    }

    fun onClick(view: View) {
        lists.add(DifDean().apply { name = "发违法违规" })
        mutableLiveData.postValue(lists)
    }
}
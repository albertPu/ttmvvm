package tt.cc.com.ttmvvm.ui.main.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.view.View
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.ui.adapter.MuItem
import tt.cc.com.ttmvvm.utlis.showToast

class HomeViewModel : ViewModel() {
    var url1 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2069625180,2162766275&fm=26&gp=0.jpg"
    var title1 = "大长腿"
    var url2 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4033704162,3378043397&fm=26&gp=0.jpg"
    var title2 = "百年能"
    var url3 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2594266525,2452159316&fm=26&gp=0.jpg"
    var title3 = "的革命"
    var test = MutableLiveData<String>().apply { value = "原始状体" }

    var data1 = MutableLiveData<DataBean>().apply {
        value = DataBean().apply {
            imageUrl.set(url1)
            title.set(title1)
        }
    }
    var data2 = MutableLiveData<DataBean>().apply {
        value = DataBean().apply {
            imageUrl.set(url2)
            title.set(title2)
        }
    }

    var data3 = MutableLiveData<DataBean>().apply {
        value = DataBean().apply {
            imageUrl.set(url3)
            title.set(title3)
        }
    }
    var pageItems = ArrayList<MuItem<LiveData<DataBean>>>().apply {
        add(MuItem(R.layout.item_page, data1))
        add(MuItem(R.layout.item_page, data2))
        add(MuItem(R.layout.item_page, data3))
    }

    fun onClick(view: View) {
        showToast("点击了")
        data1.value?.title?.set("我改变了")
        data1.value?.imageUrl?.set("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2878631732,2121272933&fm=26&gp=0.jpg")

        test.value = ("我改变了")
    }
}
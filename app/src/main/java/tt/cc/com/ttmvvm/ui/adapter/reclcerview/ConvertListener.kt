package tt.cc.com.ttmvvm.ui.adapter.reclcerview

interface ConvertListener {

    fun <T> onConvert(mvViewHolder: MVViewHolder, item: T)
}
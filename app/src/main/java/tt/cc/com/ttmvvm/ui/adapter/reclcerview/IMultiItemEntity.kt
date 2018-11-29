package tt.cc.com.ttmvvm.ui.adapter.reclcerview

import com.chad.library.adapter.base.entity.MultiItemEntity

interface IMultiItemEntity : MultiItemEntity {


    fun setPosition(position: Int)

    fun getClickViewIds():ArrayList<Int>?
}
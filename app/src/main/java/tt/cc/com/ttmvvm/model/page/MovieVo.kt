package tt.cc.com.ttmvvm.model.page

import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.ui.adapter.reclcerview.IMultiItemEntity
import tt.cc.com.ttmvvm.utlis.getColor

class MovieVo : IMultiItemEntity {
    var mPosition = 0

    override fun getClickViewIds(): ArrayList<Int>? = ArrayList<Int>().apply {
        add(R.id.tv_movie_name)
    }
    override fun setPosition(position: Int) {
        this.mPosition = position
    }

    override fun getItemType(): Int = 0




    var id = ""
    var videoName = ""
    var coverImageUrl = ""
    var videoPlayUrl = ""
    var desc: String? = ""
    var discussNumber = "0"
    var vipLevel = 0
    var videoType = 0
    var typeName = ""

    var color: Int = 0
        get() = if (mPosition % 2 == 0) {
            getColor(R.color.main_red)!!
        } else {
            getColor(R.color.white)!!
        }
}
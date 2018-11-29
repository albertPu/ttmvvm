package tt.cc.com.ttmvvm.ui.adapter.reclcerview

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

abstract class BaseMvvmMultiItemQuickAdapter<T : IMultiItemEntity, K : BaseViewHolder>(data: List<T>?) :
    BaseMultiItemQuickAdapter<T, K>(data) {

    private var mLayouts = SparseIntArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        if (viewType != BaseQuickAdapter.LOADING_VIEW && viewType != BaseQuickAdapter.HEADER_VIEW && viewType != BaseQuickAdapter.EMPTY_VIEW && viewType != BaseQuickAdapter.FOOTER_VIEW) {
            val d = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                getLayoutId(viewType),
                parent,
                false
            )
            d.executePendingBindings()
            val mvViewHolder = MVViewHolder(d)
            bindViewClickListener(mvViewHolder)
            mvViewHolder.setQAdapter(this)
            return mvViewHolder as K
        } else {
            return super.onCreateViewHolder(parent, viewType)
        }
    }

    private fun getLayoutId(viewType: Int): Int {
        return mLayouts.get(viewType, TYPE_NOT_FOUND)
    }


    override fun addItemType(type: Int, layoutResId: Int) {
        if (mLayouts == null) {
            mLayouts = SparseIntArray()
        }
        mLayouts.put(type, layoutResId)
        super.addItemType(type, layoutResId)
    }

    private fun bindViewClickListener(baseViewHolder: BaseViewHolder?) {
        if (baseViewHolder == null) {
            return
        }
        val view = baseViewHolder.itemView

        onItemClickListener?.apply {
            view.setOnClickListener { v ->
                onItemClickListener.onItemClick(
                    this@BaseMvvmMultiItemQuickAdapter,
                    v,
                    baseViewHolder.layoutPosition - headerLayoutCount
                )
            }
        }
        onItemLongClickListener?.apply {
            view.setOnLongClickListener { v ->
                onItemLongClickListener.onItemLongClick(
                    this@BaseMvvmMultiItemQuickAdapter,
                    v,
                    baseViewHolder.layoutPosition - headerLayoutCount
                )
            }
        }
    }

}
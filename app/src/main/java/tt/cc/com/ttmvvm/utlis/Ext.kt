package tt.cc.com.ttmvvm.utlis

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.TextView
import android.widget.Toast
import tt.cc.com.ttmvvm.TtApplication
import tt.cc.com.ttmvvm.ui.adapter.reclcerview.IMultiItemEntity


fun ViewModel.getDrawable(res: Int): Drawable? {
    return TtApplication.context?.resources?.getDrawable(res)
}


fun ViewModel.getColor(res: Int): Int? {
    return TtApplication.context?.resources?.getColor(res)
}


fun IMultiItemEntity.getColor(res: Int): Int? {
    return TtApplication.context?.resources?.getColor(res)
}


fun ViewModel.showToast(msg: String) {
    Toast.makeText(TtApplication.context, msg, Toast.LENGTH_SHORT).show()
}


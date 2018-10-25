package tt.cc.com.ttmvvm.ui.main.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread

class MutableItem(val index: Int, var checkable: Boolean = false) {
    private val mutChecked = MutableLiveData<Boolean>().apply { value = false }
    val checked: LiveData<Boolean> = mutChecked

    fun setChecked(value: Boolean) {
        if (!checkable) {
            return
        }
        mutChecked.value = value
    }

    @MainThread
    fun onToggleChecked(): Boolean {
        if (!checkable) {
            return false
        }
        mutChecked.value = !(mutChecked.value)!!
        return true
    }
}

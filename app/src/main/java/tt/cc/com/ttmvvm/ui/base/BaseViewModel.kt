package tt.cc.com.ttmvvm.ui.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 *created by Albert
 */
open class BaseViewModel : ViewModel() {

    var lifecycleOwner: WeakReference<LifecycleOwner>? = null



    override fun onCleared() {
        super.onCleared()
        lifecycleOwner = null
    }
}
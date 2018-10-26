package tt.cc.com.ttmvvm.model

import android.databinding.BaseObservable
import android.databinding.Bindable

/**
 * created by Albert
 */
class UserInfo : BaseObservable() {

    @Bindable
    var name: String? = ""

    @Bindable
    var address: String? = ""
}

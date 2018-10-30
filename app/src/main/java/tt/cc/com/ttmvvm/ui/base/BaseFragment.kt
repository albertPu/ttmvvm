package tt.cc.com.ttmvvm.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    var bind: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind =
                DataBindingUtil.inflate(inflater, getContentView(), container, false)
        return bind!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bind?.setLifecycleOwner(this)
        initViewModel(bind)
        EventBus.getDefault().register(this)

    }

    open fun initViewModel(binding: T?) {

    }


    abstract fun getContentView(): Int

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    open fun onEvent(any: Any) {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    open fun onBackPressed() {

    }
}
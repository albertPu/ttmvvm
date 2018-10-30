package tt.cc.com.ttmvvm

import android.os.Bundle
import tt.cc.com.ttmvvm.ui.base.BaseActivity
import tt.cc.com.ttmvvm.ui.main.MainFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setStatusColor(true)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}

package tt.cc.com.ttmvvm

import android.app.Application

class TtApplication : Application() {

    companion object {
        var context: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
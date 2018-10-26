package tt.cc.com.ttmvvm.net

import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tt.cc.com.tt.model.Response
import tt.cc.com.ttmvvm.TtApplication


/**
 * created by Albert
 */
object ResponseTransformer {

    fun <T> handleResult(): ObservableTransformer<Response<T>, T> {
        return ObservableTransformer { observable ->
            observable.map { it ->
                if (it.data != null) {
                    it.data
                } else {
                    throw CustomException.MsgException(it.msg!!)
                }
            }.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext { throwable: Throwable ->
                    Observable.error(CustomException.handleException(throwable))
                }.doOnError { e: Throwable ->
                    Toast.makeText(TtApplication.context, e.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

}


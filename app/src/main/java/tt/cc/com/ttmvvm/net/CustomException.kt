package tt.cc.com.ttmvvm.net

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * created by Albert
 */
object CustomException {
    /**
     * 未知错误
     */
    val UNKNOWN = 1000

    /**
     * 解析错误
     */
    val PARSE_ERROR = 1001

    /**
     * 网络错误
     */
    val NETWORK_ERROR = 1002

    /**
     * 协议错误
     */
    val HTTP_ERROR = 1003

    class MsgException(var msg: String) : Exception() {
        override val message: String?
            get() = msg
    }

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            //解析错误
            ex = ApiException(PARSE_ERROR, e.message)
            return ex
        } else if (e is ConnectException) {
            //网络错误
            ex = ApiException(NETWORK_ERROR, e.message)
            return ex
        } else if (e is UnknownHostException || e is SocketTimeoutException) {
            //连接错误
            ex = ApiException(NETWORK_ERROR, e.message)
            return ex
        } else {
            //未知错误
            ex = ApiException(UNKNOWN, e.message)
            return ex
        }
    }

}

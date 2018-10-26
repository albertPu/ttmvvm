package tt.cc.com.tt.model

/**
 * created by Albert
 */
class Response<T> {
    var data: T? = null
    var success: Boolean? = true
    var code: Int? = -1
    var msg: String? = ""
}

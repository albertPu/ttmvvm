package tt.cc.com.ttmvvm.net

/**
 * created by Albert
 */
class ApiException(var code: Int, var msg: String?) : Exception() {

    override val message: String?
        get() = msg


}

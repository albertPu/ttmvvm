package tt.cc.com.ttmvvm.net

import tt.cc.com.ttmvvm.model.page.BannerVo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import tt.cc.com.tt.model.Response
import tt.cc.com.ttmvvm.model.MemberVO
import tt.cc.com.ttmvvm.model.page.DetailVo
import tt.cc.com.ttmvvm.model.page.MovieVo

/**
 * created by Albert
 */
interface Api {

    @POST("api/member/register")
    fun register(@Query("name") name: String, @Query("pwd") pwd: String, @Query("age") age: Int): Observable<Response<MemberVO>>

    @GET("api/member/list")
    fun getMemberList(): Observable<Response<List<MemberVO>>>

    @GET("api/page/banner")
    fun getBanner(): Observable<Response<List<BannerVo>>>

    @POST("api/page/movies")
    fun getMovies(@Query("page") page: Int): Observable<Response<List<MovieVo>>>

    @POST("api/page/moviesMore")
    fun getMoviesMore(@Query("page") page: Int, @Query("id") id: String): Observable<Response<DetailVo>>
}

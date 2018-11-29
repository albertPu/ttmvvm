package tt.cc.com.ttmvvm.ui.video

import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.support.v4.view.ViewCompat
import android.transition.Transition
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.video_fragment.*
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.databinding.VideoFragmentBinding
import tt.cc.com.ttmvvm.model.page.MovieVo
import tt.cc.com.ttmvvm.ui.base.BaseFragment
import tt.cc.com.ttmvvm.view.CleanLeakUtils
import tt.cc.com.ttmvvm.view.VideoListener
import java.lang.ref.WeakReference

class VideoFragment : BaseFragment<VideoFragmentBinding>() {

    companion object {
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"
    }


    /**
     * Item 详细数据
     */
    private var orientationUtils: OrientationUtils? = null


    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    private var isTransition: Boolean = false

    private var transition: Transition? = null


    override fun getContentView() = R.layout.video_fragment

    override fun initViewModel(binding: VideoFragmentBinding?) {
        super.initViewModel(binding)
        binding?.viewModel = ViewModelProviders.of(this).get(VideoViewModel::class.java)
        binding?.viewModel?.let {
            it.lifecycleOwner = WeakReference(this)
            lifecycle.addObserver(it)
        }
        initTransition()
        initVideoViewConfig()
    }

    override fun onEvent(any: Any) {
        if (any is MovieVo) {
            bind?.viewModel?.imgUrl?.value = any.coverImageUrl
            bind?.viewModel?.load(any.id)
            //增加封面
            val imageView = ImageView(activity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(this)
                .load(any.coverImageUrl)
                .centerCrop()
                .into(imageView)
            mVideoView.thumbImageView = imageView
           /* bind?.viewModel?.items.apply {
                bind?.viewModel?.movieVos!![0].data.add(any)
                this!!.value = bind?.viewModel?.movieVos
            }*/
            setVideo(any.videoPlayUrl)
        }

    }

    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, VideoFragment.IMG_TRANSITION)
            startPostponedEnterTransition()
        }
    }

    /**
     * 设置播放视频 URL
     */
    fun setVideo(url: String) {
        mVideoView.setUp(url, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()
    }


    /**
     * 初始化 VideoView 的配置
     */
    private fun initVideoViewConfig() {
        //设置旋转
        orientationUtils = OrientationUtils(activity, mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)


        mVideoView.setStandardVideoAllCallBack(object : VideoListener {

            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
            }

            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
            }

            override fun onEnterFullscreen(url: String, vararg objects: Any) {
                super.onEnterFullscreen(url, *objects)
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()
            }
        })
        //设置返回按键功能
        mVideoView.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(activity, true, true)
        }
        //锁屏事件
        mVideoView.setLockClickListener { _, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils?.isEnable = !lock
        }
    }


    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(activity))
            return
        //释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        super.onBackPressed()
    }

    override fun onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(activity)
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }


}
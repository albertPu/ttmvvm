package tt.cc.com.ttmvvm.ui.main

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import tt.cc.com.ttmvvm.R
import tt.cc.com.ttmvvm.ui.base.BaseFragment
import tt.cc.com.ttmvvm.ui.main.dif.DifFragment
import tt.cc.com.ttmvvm.ui.main.home.HomeFragment
import tt.cc.com.ttmvvm.ui.main.live.LiveFragment
import tt.cc.com.ttmvvm.ui.main.mine.MineFragment
import tt.cc.com.ttmvvm.ui.main.vip.VipFragment
import tt.cc.com.ttmvvm.utlis.getColor
import tt.cc.com.ttmvvm.utlis.getDrawable
import java.util.ArrayList

class MainViewModel : ViewModel() {

    var name = ObservableField<String>()
    var homeBottomIcon = ObservableField<Drawable>()
    var homeBottomColor = ObservableField<Int>()

    var vipBottomIcon = ObservableField<Drawable>()
    var vipBottomColor = ObservableField<Int>()

    var diffBottomIcon = ObservableField<Drawable>()
    var diffBottomColor = ObservableField<Int>()

    var livePlaBottomIcon = ObservableField<Drawable>()
    var livePlaBottomColor = ObservableField<Int>()

    var minBottomIcon = ObservableField<Drawable>()
    var minBottomColor = ObservableField<Int>()

    var childFragmentManager: FragmentManager? = null
    var currentFragment: BaseFragment<*>? = null

    val fragments = ArrayList<BaseFragment<*>>().apply {
        add(HomeFragment())
        add(VipFragment())
        add(DifFragment())
        add(LiveFragment())
        add(MineFragment())
    }

    init {
        rest()
        currentFragment = fragments[0]
        homeBottomIcon.set(getDrawable(R.drawable.icon_shikan_pressed))
        homeBottomColor.set(getColor(R.color.main_red))
    }


    fun onClick(view: View) {
        rest()
        when (view.id) {
            R.id.bottom_home -> {
                homeBottomIcon.set(getDrawable(R.drawable.icon_shikan_pressed))
                homeBottomColor.set(getColor(R.color.main_red))
                switchFragment(fragments[0])
            }
            R.id.bottom_vip -> {
                vipBottomIcon.set(getDrawable(R.drawable.icon_vip_pressed))
                vipBottomColor.set(getColor(R.color.main_red))
                switchFragment(fragments[1])
            }
            R.id.bottom_dif
            -> {
                diffBottomIcon.set(getDrawable(R.drawable.icon_fenlei_pressed))
                diffBottomColor.set(getColor(R.color.main_red))
                switchFragment(fragments[2])
            }
            R.id.bottom_live
            -> {
                livePlaBottomIcon.set(getDrawable(R.drawable.icon_zhibo_pressed))
                livePlaBottomColor.set(getColor(R.color.main_red))
                switchFragment(fragments[3])
            }
            R.id.bottom_min
            -> {
                minBottomIcon.set(getDrawable(R.drawable.icon_user_pressed))
                minBottomColor.set(getColor(R.color.main_red))
                switchFragment(fragments[4])
            }

        }

    }

    fun rest() {
        homeBottomIcon.set(getDrawable(R.drawable.icon_shikan_normal))
        homeBottomColor.set(getColor(R.color.normol_text))

        vipBottomIcon.set(getDrawable(R.drawable.icon_vip_normal))
        vipBottomColor.set(getColor(R.color.normol_text))

        diffBottomIcon.set(getDrawable(R.drawable.icon_fenlei_normal))
        diffBottomColor.set(getColor(R.color.normol_text))

        livePlaBottomIcon.set(getDrawable(R.drawable.icon_zhibo_normal))
        livePlaBottomColor.set(getColor(R.color.normol_text))

        minBottomIcon.set(getDrawable(R.drawable.icon_user_normal))
        minBottomColor.set(getColor(R.color.normol_text))
    }

    private fun switchFragment(targetFragment: Fragment) {
        val transaction = childFragmentManager?.beginTransaction()
        if (!targetFragment.isAdded) {
            transaction
                ?.hide(currentFragment as Fragment)
                ?.add(R.id.home_container, targetFragment)
                ?.show(targetFragment)?.commit()
        } else {
            transaction
                ?.hide(currentFragment as Fragment)
                ?.show(targetFragment)
                ?.commit()
        }
        currentFragment = targetFragment as BaseFragment<*>
    }

    fun touchHome() {
        switchFragment(fragments[0])
    }

}
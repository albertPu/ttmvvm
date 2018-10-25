package tt.cc.com.ttmvvm.ui.adapter.viewpage;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tt.cc.com.ttmvvm.BR;
import tt.cc.com.ttmvvm.ui.adapter.MuItem;

import java.util.List;


public class MutableAdapter<T extends MuItem> extends PagerAdapter {

    @BindingAdapter("pageItems")
    public static <T> void BindPageAdapter(ViewPager viewPager, List<MuItem<T>> data) {
        if (viewPager.getAdapter() == null) {
            MutableAdapter<MuItem<T>> adapter = new MutableAdapter<>(data, viewPager.getContext());
            viewPager.setAdapter(adapter);
        }
    }

    private List<T> mDatas;
    private Context context;
    private Pools.Pool<View> pool = new Pools.SimplePool<>(100);

    private MutableAdapter(List<T> datas, Context context) {
        mDatas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = pool.acquire();
        if (view == null) {
            view = DataBindingUtil.inflate(LayoutInflater.from(context), mDatas.get(position).getRes(), container, false).getRoot();
            ViewDataBinding bind = DataBindingUtil.bind(view);
            Object data = mDatas.get(position).getData();
            if (data instanceof LiveData) {
                bind.setVariable(BR.item, ((LiveData) data).getValue());
            } else {
                bind.setVariable(BR.item, data);
            }
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
        pool.release(view);
    }


}

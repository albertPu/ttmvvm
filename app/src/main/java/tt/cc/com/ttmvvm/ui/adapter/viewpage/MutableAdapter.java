package tt.cc.com.ttmvvm.ui.adapter.viewpage;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tt.cc.com.ttmvvm.BR;

import java.util.ArrayList;
import java.util.List;


public class MutableAdapter<T> extends PagerAdapter {


    @BindingAdapter({"pageItems", "bindLifecycleOwner"})
    public static <T> void bindPageAdapter(ViewPager viewPager, LiveData<ArrayList<MuPagerItem<T>>> data, LifecycleOwner owner) {
        if (viewPager.getAdapter() == null) {
            final MutableAdapter adapter = new MutableAdapter(data, viewPager.getContext());
            viewPager.setAdapter(adapter);
            data.observe(owner, new Observer<ArrayList<MuPagerItem<T>>>() {
                @Override
                public void onChanged(@Nullable ArrayList<MuPagerItem<T>> muPagerItems) {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }


    private LiveData<List<MuPagerItem<T>>> mDatas;
    private Context context;

    private MutableAdapter(LiveData<List<MuPagerItem<T>>> datas, Context context) {
        mDatas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.getValue().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = DataBindingUtil.inflate(LayoutInflater.from(context), mDatas.getValue().get(position).getRes(), container, false).getRoot();
        ViewDataBinding bind = DataBindingUtil.bind(view);
        Object data = mDatas.getValue().get(position).getData();
        if (data instanceof LiveData) {
            bind.setVariable(BR.item, ((LiveData) data).getValue());
        } else {
            bind.setVariable(BR.item, data);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }


}

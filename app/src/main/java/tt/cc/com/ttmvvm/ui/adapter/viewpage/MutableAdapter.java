package tt.cc.com.ttmvvm.ui.adapter.viewpage;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.*;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tt.cc.com.ttmvvm.BR;


public class MutableAdapter<T extends MuPagerItem> extends PagerAdapter {

    @BindingAdapter("pageItems")
    public static <T> void BindPageAdapter(ViewPager viewPager, ObservableArrayList<MuPagerItem<T>> data) {
        if (viewPager.getAdapter() == null) {
            final MutableAdapter<MuPagerItem<T>> adapter = new MutableAdapter<>(data, viewPager.getContext());
            viewPager.setAdapter(adapter);
            data.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<MuPagerItem<T>>>() {
                @Override
                public void onChanged(ObservableList<MuPagerItem<T>> sender) {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<MuPagerItem<T>> sender, int positionStart, int itemCount) {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(ObservableList<MuPagerItem<T>> sender, int positionStart, int itemCount) {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeMoved(ObservableList<MuPagerItem<T>> sender, int fromPosition, int toPosition, int itemCount) {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(ObservableList<MuPagerItem<T>> sender, int positionStart, int itemCount) {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private ObservableArrayList<T> mDatas;
    private Context context;

    private MutableAdapter(ObservableArrayList<T> datas, Context context) {
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

        View view = DataBindingUtil.inflate(LayoutInflater.from(context), mDatas.get(position).getRes(), container, false).getRoot();
        ViewDataBinding bind = DataBindingUtil.bind(view);
        Object data = mDatas.get(position).getData();
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

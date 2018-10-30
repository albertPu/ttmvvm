package tt.cc.com.ttmvvm.utlis;

import android.arch.lifecycle.LiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import jp.wasabeef.glide.transformations.BlurTransformation;
import tt.cc.com.ttmvvm.utlis.glid.GlideCircleTransform;

public class Bind {
    @BindingAdapter("bind:color")
    public static void textColor(TextView textView, ObservableField<Integer> color) {
        //  int color1 = TtApplication.Companion.getContext().getResources().getColor(color.get());
        textView.setTextColor(color.get());
    }

    @BindingAdapter("bindImageUrl")
    public static void bindImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter("bindBlurImageUrl")
    public static void bindBlurImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url).bitmapTransform(new BlurTransformation(view.getContext(), 14, 3))
                .into(view);
    }

    @BindingAdapter("bindCircleImageUrl")
    public static void bindCircleImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url).bitmapTransform(new GlideCircleTransform(view.getContext()))
                .into(view);
    }


    @BindingAdapter({"loadMore", "isLoading"})
    public static void loadMore(SmartRefreshLayout layout, OnLoadMoreListener listener, LiveData<Boolean> isLoading) {
        layout.setOnLoadMoreListener(listener);
        if (!isLoading.getValue()) {
            layout.finishLoadMore();
        }
    }
}

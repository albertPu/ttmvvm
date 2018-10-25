package tt.cc.com.ttmvvm.utlis;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

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
}

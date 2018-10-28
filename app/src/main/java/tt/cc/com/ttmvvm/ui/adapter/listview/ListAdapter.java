package tt.cc.com.ttmvvm.ui.adapter.listview;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import tt.cc.com.ttmvvm.R;
import tt.cc.com.ttmvvm.BR;

import java.util.List;

/**
 * created by Albert
 */
public class ListAdapter {

    @BindingAdapter({"bindListItems", "bindLifecycleOwner"})
    public static <T> void bindListItem(ListView view, LiveData<List<T>> datas, LifecycleOwner lifecycleOwner) {
        if (view.getAdapter() == null) {
            final BindListAdapter<T> adapter = new BindListAdapter<>(datas, view.getContext());
            view.setAdapter(adapter);
            datas.observe(lifecycleOwner, new Observer<List<T>>() {
                @Override
                public void onChanged(@Nullable List<T> ts) {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private static class BindListAdapter<T> extends BaseAdapter {

        LiveData<List<T>> datas;
        Context context;

        BindListAdapter(LiveData<List<T>> datas, Context context) {
            this.datas = datas;
            this.context = context;
        }

        @Override
        public int getCount() {
            return datas.getValue() == null ? 0 : datas.getValue().size();
        }

        @Override
        public Object getItem(int position) {
            return datas.getValue().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.dataBinding = dataBinding;
                convertView = dataBinding.getRoot();
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.dataBinding.setVariable(BR.item, datas.getValue().get(position));
            viewHolder.dataBinding.executePendingBindings();
            return convertView;
        }

        class ViewHolder {
            public ViewDataBinding dataBinding;
        }

    }
}

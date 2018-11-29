package tt.cc.com.ttmvvm.ui.adapter.reclcerview;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import org.jetbrains.annotations.Nullable;
import tt.cc.com.ttmvvm.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Albert
 */

public class MultiRecyclerViewAdapter {


    @BindingAdapter(value = {"bindItemType",
            "bindLayoutManager",
            "bindLifecycleOwner",
            "bindItemListener",
            "bindData",
            "bindConvert",
            "bindChildClickListener"}, requireAll = false)
    public static <T extends IMultiItemEntity> void bindAdapter(RecyclerView recyclerView,
                                                                ArrayList<ItemLayout> items,
                                                                RecyclerView.LayoutManager layoutManager,
                                                                LifecycleOwner lifecycleOwner,
                                                                BaseQuickAdapter.OnItemClickListener listener,
                                                                MutableLiveData<ArrayList<T>> data,
                                                                ConvertListener convertListener,
                                                                BaseQuickAdapter.OnItemChildClickListener childClickListener) {

        bind(recyclerView, items, layoutManager, lifecycleOwner, listener, data, convertListener, childClickListener);

    }

    private static <T extends IMultiItemEntity> void bind(RecyclerView recyclerView,
                                                          ArrayList<ItemLayout> items,
                                                          RecyclerView.LayoutManager layoutManager,
                                                          LifecycleOwner lifecycleOwner,
                                                          BaseQuickAdapter.OnItemClickListener listener,
                                                          MutableLiveData<ArrayList<T>> data,
                                                          ConvertListener convertListener,
                                                          BaseQuickAdapter.OnItemChildClickListener childClickListener) {

        if (recyclerView.getAdapter() == null) {
            if (items == null) return;
            if (lifecycleOwner == null) return;
            if (data == null) return;
            if (data.getValue() == null) return;
            if (data.getValue().size() <= 0) return;
            if (data.getValue().get(0) instanceof IMultiItemEntity) {
                MultiRecAdapter adapter = new MultiRecAdapter(data.getValue(), items, convertListener);
                if (listener != null) {
                    adapter.setOnItemClickListener(listener);
                }
                if (childClickListener != null) {
                    adapter.setOnItemChildClickListener(childClickListener);
                }
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                data.observe(lifecycleOwner, ts -> adapter.setNewData(ts));
            } else {
                try {
                    throw new IllegalArgumentException("RecyclerView data must instanceof IMultiItemEntity");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    static class MultiRecAdapter<T extends IMultiItemEntity> extends BaseMvvmMultiItemQuickAdapter<T, MVViewHolder> {


        ConvertListener convertListener;


        public MultiRecAdapter(@Nullable List<? extends T> data, List<ItemLayout> layouts, ConvertListener convertListener) {
            super(data);
            this.convertListener = convertListener;
            for (ItemLayout itemLayout : layouts) {
                addItemType(itemLayout.getType(), itemLayout.getLayout());
            }

        }


        @Override
        protected void convert(MVViewHolder helper, T item) {
            if (item.getClickViewIds() != null) {
                for (Integer id : item.getClickViewIds()) {
                    helper.addOnClickListener(id);
                }
            }
            item.setPosition(helper.getAdapterPosition());
            if (convertListener != null) {
                convertListener.onConvert(helper, item);
            } else {
                helper.getDataViewBinding().setVariable(BR.item, item);
            }
        }
    }


}
package tt.cc.com.ttmvvm.ui.adapter.reclcerview;

import android.content.Context;
import android.databinding.*;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tt.cc.com.ttmvvm.BR;

/**
 * created by Albert
 */
public class MultiRecyclerViewAdapter {

    @BindingAdapter({"bindRecItems", "bindLayoutManager"})
    public static <T> void bindRecItems(RecyclerView recyclerView, ObservableArrayList<MultiRecItem<T>> data, RecyclerView.LayoutManager layoutManager) {
        if (data.size() == 0) {

        } else if (data.size() == 1) {
            if (recyclerView.getAdapter() == null) {
                final SingleRecyclerAdapter<T> adapter = new SingleRecyclerAdapter<>(data.get(0), recyclerView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                data.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<MultiRecItem<T>>>() {
                    @Override
                    public void onChanged(ObservableList<MultiRecItem<T>> sender) {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeChanged(ObservableList<MultiRecItem<T>> sender, int positionStart, int itemCount) {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeInserted(ObservableList<MultiRecItem<T>> sender, int positionStart, int itemCount) {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeMoved(ObservableList<MultiRecItem<T>> sender, int fromPosition, int toPosition, int itemCount) {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeRemoved(ObservableList<MultiRecItem<T>> sender, int positionStart, int itemCount) {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        } else {
            if (recyclerView.getAdapter() == null) {
                final MultiRecyclerAdapter<T> adapter = new MultiRecyclerAdapter<>(data, recyclerView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                data.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<MultiRecItem<T>>>() {
                    @Override
                    public void onChanged(ObservableList<MultiRecItem<T>> sender) {
                        adapter.flushData();
                    }

                    @Override
                    public void onItemRangeChanged(ObservableList<MultiRecItem<T>> sender, int positionStart, int itemCount) {
                        adapter.flushData();
                    }

                    @Override
                    public void onItemRangeInserted(ObservableList<MultiRecItem<T>> sender, int positionStart, int itemCount) {
                        adapter.flushData();
                    }

                    @Override
                    public void onItemRangeMoved(ObservableList<MultiRecItem<T>> sender, int fromPosition, int toPosition, int itemCount) {
                        adapter.flushData();
                    }

                    @Override
                    public void onItemRangeRemoved(ObservableList<MultiRecItem<T>> sender, int positionStart, int itemCount) {
                        adapter.flushData();
                    }
                });
            }
        }
    }


    private static class SingleRecyclerAdapter<T> extends RecyclerView.Adapter {

        MultiRecItem<T> data;
        private Context context;

        SingleRecyclerAdapter(MultiRecItem<T> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            ViewDataBinding bind = DataBindingUtil.inflate(LayoutInflater.from(context), data.getRes(), viewGroup, false);
            SingleRecyclerViewHolder holder = new SingleRecyclerViewHolder(bind.getRoot());
            holder.setBind(bind);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            if (viewHolder instanceof SingleRecyclerViewHolder) {
                ((SingleRecyclerViewHolder) viewHolder).bind.setVariable(BR.item, data.getData().get(position));
                ((SingleRecyclerViewHolder) viewHolder).bind.executePendingBindings();
            }
        }

        @Override
        public int getItemCount() {
            return data.getData().size();
        }

        private static class SingleRecyclerViewHolder extends RecyclerView.ViewHolder {
            ViewDataBinding bind;

            SingleRecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public ViewDataBinding getBind() {
                return bind;
            }

            public void setBind(ViewDataBinding bind) {
                this.bind = bind;
            }
        }
    }

    private static class MultiRecyclerAdapter<T> extends RecyclerView.Adapter {

        ObservableArrayList<MultiRecItem<T>> data;
        Context context;
        ObservableArrayList<DataTypeBean> dataTypeBeans;

        MultiRecyclerAdapter(ObservableArrayList<MultiRecItem<T>> data, Context context) {
            this.data = data;
            this.context = context;
            dataTypeBeans = resolveDatas(data);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
            ViewDataBinding binding;
            RecyclerView.ViewHolder viewHolder = null;
            for (DataTypeBean dataTypeBean : dataTypeBeans) {
                if (dataTypeBean.getItemType() == type) {
                    binding = DataBindingUtil.inflate(LayoutInflater.from(context), dataTypeBean.res, viewGroup, false);
                    viewHolder = new MultiRecyclerViewHolder(binding.getRoot(), binding);
                    break;
                }
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            if (viewHolder instanceof MultiRecyclerViewHolder) {
                ((MultiRecyclerViewHolder) viewHolder).getBinding().setVariable(BR.item, dataTypeBeans.get(position).data);
                ((MultiRecyclerViewHolder) viewHolder).getBinding().executePendingBindings();
            }
        }

        @Override
        public int getItemCount() {
            return dataTypeBeans == null ? 0 : dataTypeBeans.size();
        }

        @Override
        public int getItemViewType(int position) {
            return dataTypeBeans.get(position).getItemType();
        }

        private static class MultiRecyclerViewHolder extends RecyclerView.ViewHolder {

            ViewDataBinding binding;

            public MultiRecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public MultiRecyclerViewHolder(@NonNull View itemView, ViewDataBinding binding) {
                super(itemView);
                this.binding = binding;
            }

            public ViewDataBinding getBinding() {
                return binding;
            }
        }

        private class DataTypeBean {
            @LayoutRes
            private int res;
            private T data;
            private int position;
            private int itemType;

            DataTypeBean(int res, T data, int position, int itemType) {
                this.res = res;
                this.data = data;
                this.position = position;
                this.itemType = itemType;
            }

            public int getRes() {
                return res;
            }

            public T getData() {
                return data;
            }

            public int getPosition() {
                return position;
            }

            public int getItemType() {
                return itemType;
            }
        }

        private ObservableArrayList<DataTypeBean> resolveDatas(ObservableArrayList<MultiRecItem<T>> data) {
            ObservableArrayList<DataTypeBean> beans = new ObservableArrayList<>();
            int position = 0;
            int currentRes = 0;
            int itemType = -1;
            for (MultiRecItem<T> t : data) {
                if (currentRes != t.getRes()) {
                    currentRes = t.getRes();
                    itemType++;
                } else {
                    throw new IllegalArgumentException("MultiRecItem layout Not the same");
                }
                for (T v : t.getData()) {
                    DataTypeBean dataTypeBean = new DataTypeBean(t.getRes(), v, position, itemType);
                    position++;
                    beans.add(dataTypeBean);
                }
            }
            return beans;
        }

        public void flushData() {
            dataTypeBeans = resolveDatas(data);
            this.notifyDataSetChanged();
        }
    }
}

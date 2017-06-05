package module.jk.cn.jkshoppingcart.module.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.common.StringUtil;

/**
 * @className: ShoppingCartAdapter
 * @classDescription: 购物车列表适配器
 * @author: leibing
 * @createTime: 2017/6/2
 */
public class ShoppingCartAdapter extends BaseExpandableListAdapter {
    // 数据源
    public ArrayList<ShoppingCartTestBean> mData;
    // 复选框接口
    private ShoppingCartInterface.CheckInterface mCheckInterface;
    // 布局填充器
    private LayoutInflater mLayoutInflater;

    /**
      * Constructor
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param context 上下文
      * @param mData 数据源
      * @return
      */
    public ShoppingCartAdapter(Context context, ArrayList<ShoppingCartTestBean> mData){
        mLayoutInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    /**
      * set data
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param mData 数据源
      * @return
      */
    public void setData(ArrayList<ShoppingCartTestBean> mData){
        this.mData = mData;
        ShoppingCartAdapter.this.notifyDataSetChanged();
    }

    /**
      * 设置复选框接口
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param mCheckInterface 复选框接口
      * @return
      */
    public void setCheckInterface(ShoppingCartInterface.CheckInterface mCheckInterface){
        this.mCheckInterface = mCheckInterface;
    }

    @Override
    public int getGroupCount() {
        return mData != null ? mData.size(): 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mData != null
                && mData.get(groupPosition) != null
                && mData.get(groupPosition).product != null)
            return mData.get(groupPosition).product.size();
        return  0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData != null? mData.get(groupPosition): null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mData != null
                && mData.get(groupPosition) != null
                && mData.get(groupPosition).product != null
                && mData.get(groupPosition).product.size() != 0
                && childPosition < mData.get(groupPosition).product.size()
                && mData.get(groupPosition).product.get(childPosition) != null)
            return mData.get(groupPosition).product.get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupViewHolder gholder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_parent_test, null);
            gholder = new GroupViewHolder(convertView);
            convertView.setTag(gholder);
        }else {
            gholder = (GroupViewHolder) convertView.getTag();
        }

        // 更新ui
        if (mData != null
                && mData.size() != 0
                && groupPosition < mData.size()
                && mData.get(groupPosition) != null){
            gholder.updateUI(mData.get(groupPosition));
        }
        // listener
        // 组选点击事件
        gholder.parentCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(groupPosition).isSelected = ((CheckBox) v).isChecked();
                // 暴露组选接口
                if (mCheckInterface != null)
                    mCheckInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
            }
        });

        return convertView;
    }

    /**
     * @className: GroupViewHolder
     * @classDescription: 组元素绑定器
     * @author: leibing
     * @createTime: 2017/6/5
     */
    static class GroupViewHolder{
        @BindView(R.id.cb_parent)
        CheckBox parentCb;
        @BindView(R.id.tv_parent)
        TextView parentTv;

        /**
          * Constructor
          * @author leibing
          * @createTime 2017/6/5
          * @lastModify 2017/6/5
          * @param view
          * @return
          */
        public GroupViewHolder(View view) {
            // bind view
            ButterKnife.bind(this, view);
        }

        /**
          * 更新ui
          * @author leibing
          * @createTime 2017/6/5
          * @lastModify 2017/6/5
          * @param model
          * @return
          */
        public void updateUI(ShoppingCartTestBean model){
            if (model == null)
                return;
            // 设置选中状态
            parentCb.setChecked(model.isSelected);
            // 设置商家名称
            if (StringUtil.isNotEmpty(model.sellerName))
                parentTv.setText(model.sellerName);
        }
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder cholder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_child_test, null);
            cholder = new ChildViewHolder(convertView);
            convertView.setTag(cholder);
        }else {
            cholder = (ChildViewHolder) convertView.getTag();
        }

        // 更新ui
        if (mData != null
                && mData.size() != 0
                && groupPosition < mData.size()
                && mData.get(groupPosition) != null
                && mData.get(groupPosition).product != null
                && mData.get(groupPosition).product.size() != 0
                && childPosition < mData.get(groupPosition).product.size()
                && mData.get(groupPosition).product.get(childPosition) != null){
            cholder.updateUI(mData.get(groupPosition).product.get(childPosition));
        }

        // listener
        // 子选点击事件
        cholder.childCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(groupPosition).product.get(childPosition).isSelected
                        = ((CheckBox) v).isChecked();
                cholder.childCb.setChecked(((CheckBox) v).isChecked());
                // 暴露子选接口
                if (mCheckInterface != null)
                    mCheckInterface.checkChild(groupPosition, childPosition,
                        ((CheckBox) v).isChecked());
            }
        });

        return convertView;
    }

    /**
     * @className: ChildViewHolder
     * @classDescription: 子元素绑定器
     * @author: leibing
     * @createTime: 2017/6/5
     */
    static class ChildViewHolder{
        @BindView(R.id.cb_child)
        CheckBox childCb;
        @BindView(R.id.tv_product_child)
        TextView productChildTv;

        /**
         * Constructor
         * @author leibing
         * @createTime 2017/6/5
         * @lastModify 2017/6/5
         * @param view
         * @return
         */
        public ChildViewHolder(View view) {
            // bind view
            ButterKnife.bind(this, view);
        }

        /**
         * 更新ui
         * @author leibing
         * @createTime 2017/6/5
         * @lastModify 2017/6/5
         * @param model
         * @return
         */
        public void updateUI(ShoppingCartTestBean.Product model){
            if (model == null)
                return;
            // 设置选中状态
            childCb.setChecked(model.isSelected);
            // 设置商家名称
            if (StringUtil.isNotEmpty(model.productName))
                productChildTv.setText(model.productName);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

package module.jk.cn.jkshoppingcart.module.orderconfirm.coupon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.common.StringUtil;
import module.jk.cn.jkshoppingcart.module.AppManager;

/**
 * @className: UseCouponAdapter
 * @classDescription: 使用优惠劵适配器
 * @author: leibing
 * @createTime: 2017/6/10
 */
public class UseCouponAdapter extends BaseAdapter{
    // 布局填充器
    private LayoutInflater mLayoutInflater;
    // 数据源
    private ArrayList<UseCouponModel> mData;
    // 选框接口
    private CheckInterface mCheckInterface;

    /**
      * Constructor
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param mContext
      * @param mData
      * @return
      */
    public UseCouponAdapter(Context mContext, ArrayList<UseCouponModel> mData){
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mData = mData;
    }

    /**
      * set data to update ui
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param mData
      * @return
      */
    public void setData(ArrayList<UseCouponModel> mData){
        this.mData = mData;
        UseCouponAdapter.this.notifyDataSetChanged();
    }

    /**
      * 设置选框接口
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param mCheckInterface
      * @return
      */
    public void setCheckInterface(CheckInterface mCheckInterface){
        this.mCheckInterface = mCheckInterface;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size(): 0;
    }

    @Override
    public Object getItem(int position) {
        return mData != null ? mData.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHodler;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_use_coupon, null);
            viewHodler = new ViewHolder(convertView);
            convertView.setTag(viewHodler);
        }else {
            viewHodler = (ViewHolder) convertView.getTag();
        }

        if (mData != null
                && mData.size() != 0
                && position < mData.size()
                && mData.get(position) != null){
            // 更新ui
            viewHodler.updateUi(mData.get(position));
            // item选项onclick
            viewHodler.useCouponCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.get(position).isCouponSelected = ((CheckBox) v).isChecked();
                    // 暴露选项接口
                    if (mCheckInterface != null)
                        mCheckInterface.itemCheck(position, ((CheckBox) v).isChecked());
                }
            });
        }

        return convertView;
    }

    /**
     * @className: ViewHolder
     * @classDescription: 视图容器
     * @author: leibing
     * @createTime: 2017/6/10
     */
    static class ViewHolder{
        // 优惠劵面值布局
        @BindView(R.id.ly_coupon_value)
        LinearLayout couponValueLy;
        // 优惠劵实际面值
        @BindView(R.id.tv_coupon_value)
        TextView couponValueTv;
        // 优惠券使用金额范围
        @BindView(R.id.tv_coupon_range)
        TextView couponRangeTv;
        // 选项
        @BindView(R.id.cb_use_coupon)
        CheckBox useCouponCb;
        // 优惠劵分类名称
        @BindView(R.id.tv_coupon_sort_name)
        TextView couponSortNameTv;
        // 优惠券使用分组范围
        @BindView(R.id.tv_coupon_use_range)
        TextView couponUseRangeTv;
        // 有效期至
        @BindView(R.id.tv_coupon_validity)
        TextView couponValidityTv;

        /**
          * Constructor
          * @author leibing
          * @createTime 2017/6/10
          * @lastModify 2017/6/10
          * @param
          * @return
          */
        public ViewHolder(View view){
            // bind view
            ButterKnife.bind(this, view);
        }

        /**
          * 更新ui
          * @author leibing
          * @createTime 2017/6/10
          * @lastModify 2017/6/10
          * @param model
          * @return
          */
        public void updateUi(UseCouponModel model){
            if (model == null)
                return;
            // 设置优惠券是否可用
            if (model.isCouponAvailable){
                // 可用
                couponValueLy.setBackgroundResource(R.color.text_color_blue);
                couponSortNameTv.setTextColor(AppManager.getInstance().currentActivity()
                        .getResources().getColor(R.color.text_color_black2));
                useCouponCb.setVisibility(View.VISIBLE);
            }else {
                // 不可用
                couponValueLy.setBackgroundResource(R.color.text_color_common_lighted);
                couponSortNameTv.setTextColor(AppManager.getInstance().currentActivity()
                        .getResources().getColor(R.color.text_color_common_lighted));
                useCouponCb.setVisibility(View.GONE);
            }
            // 设置优惠券是否可选
            useCouponCb.setChecked(model.isCouponSelected);
            // 优惠劵实际面值
            if (model.couponValue > 0)
                couponValueTv.setText(model.couponValue + "");
            // 优惠券使用金额范围
            if (model.couponRangeValue > 0)
                couponRangeTv.setText("满" + model.couponRangeValue + "可用");
            // 优惠劵分类名称
            if (StringUtil.isNotEmpty(model.couponSortName))
                couponSortNameTv.setText(model.couponSortName);
            // 优惠券使用分组范围
            if (StringUtil.isNotEmpty(model.couponRangeGroup))
                couponUseRangeTv.setText("使用范围：" + model.couponRangeGroup);
            // 有效期至
            if (StringUtil.isNotEmpty(model.couponValidDate))
                couponValidityTv.setText("有效期至：" + model.couponValidDate);
        }
    }

    /**
     * @interfaceName: CheckInterface
     * @interfaceDescription: 选框接口
     * @author: leibing
     * @createTime: 2017/6/10
     */
    public interface CheckInterface{
        /**
         * 子选框状态改变时触发的事件
         * @param position item位置
         * @param isChecked     子元素选中与否
         */
        void itemCheck(int position, boolean isChecked);
    }
}

package module.jk.cn.jkshoppingcart.module.orderconfirm.redenvelope;

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

/**
 * @className: UseRedEnvelopeAdapter
 * @classDescription: 使用红包适配器
 * @author: leibing
 * @createTime: 2017/6/12
 */
public class UseRedEnvelopeAdapter extends BaseAdapter{
    // 布局填充器
    private LayoutInflater mLayoutInflater;
    // 数据源
    private ArrayList<UseRedEnvelopeModel> mData;
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
    public UseRedEnvelopeAdapter(Context mContext, ArrayList<UseRedEnvelopeModel> mData){
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
    public void setData(ArrayList<UseRedEnvelopeModel> mData){
        this.mData = mData;
        UseRedEnvelopeAdapter.this.notifyDataSetChanged();
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
        final ViewHolder viewHodler;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_use_red_envelope, null);
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
            viewHodler.redEnvelopeCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.get(position).isRedEnvelopeSelected = ((CheckBox) v).isChecked();
                    // 暴露选项接口
                    if (mCheckInterface != null)
                        mCheckInterface.itemCheck(position, ((CheckBox) v).isChecked());
                }
            });
            // item onclick
            viewHodler.useRedEnvelopeLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.get(position).isRedEnvelopeSelected = !viewHodler.redEnvelopeCb.isChecked();
                    // 暴露选项接口
                    if (mCheckInterface != null)
                        mCheckInterface.itemCheck(position, !viewHodler.redEnvelopeCb.isChecked());
                    viewHodler.redEnvelopeCb.setChecked(!viewHodler.redEnvelopeCb.isChecked());
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
    static class ViewHolder {
        // 父容器
        @BindView(R.id.ly_use_red_envelope)
        LinearLayout useRedEnvelopeLy;
        // 红包面值
        @BindView(R.id.tv_red_envelope_value)
        TextView redEnvelopeValueTv;
        // 选项
        @BindView(R.id.cb_red_envelope)
        CheckBox redEnvelopeCb;
        // 有效期至
        @BindView(R.id.tv_red_envelope_validity)
        TextView redEnvelopeValidityTv;

        /**
         * Constructor
         * @author leibing
         * @createTime 2017/6/10
         * @lastModify 2017/6/10
         * @param
         * @return
         */
        public ViewHolder(View view) {
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
        public void updateUi(UseRedEnvelopeModel model) {
            if (model == null)
                return;
            redEnvelopeCb.setChecked(model.isRedEnvelopeSelected);
            if (model.redEnvelopeValue > 0)
                redEnvelopeValueTv.setText(model.redEnvelopeValue + "");
            if (StringUtil.isNotEmpty(model.redEnvelopeValidDate))
                redEnvelopeValidityTv.setText(model.redEnvelopeValidDate);
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

package module.jk.cn.jkshoppingcart.module.shoppingcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.common.ImageLoader;
import module.jk.cn.jkshoppingcart.common.StringUtil;
import module.jk.cn.jkshoppingcart.module.AppManager;
import module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartInterface;
import module.jk.cn.jkshoppingcart.module.shoppingcart.model.ShoppingCartBean;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_CREDITS_EXCHANGE;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_AWARD_INVALID;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_GROUP;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_GROUP_INVALID;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_SKU;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_SKU_INVALID;

/**
 * @className: ShoppingCartAdapter
 * @classDescription: 购物车列表适配器
 * @author: leibing
 * @createTime: 2017/6/2
 */
public class ShoppingCartAdapter extends BaseExpandableListAdapter {
    // 数据源
    public ArrayList<ShoppingCartBean> mData;
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
    public ShoppingCartAdapter(Context context, ArrayList<ShoppingCartBean> mData){
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
    public void setData(ArrayList<ShoppingCartBean> mData){
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
            convertView = mLayoutInflater.inflate(R.layout.item_shoppingcart_parent, null);
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
        gholder.parentItemCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(groupPosition).isSelected = ((CheckBox) v).isChecked();
                // 暴露组选接口
                if (mCheckInterface != null)
                    mCheckInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
            }
        });
        // 失效产品清空操作
        gholder.rightInvalidTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清空处理
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
        // 正常组
        // 组item容器
        @BindView(R.id.ly_normal_parent)
        LinearLayout normalPatentLy;
        // 组选
        @BindView(R.id.cb_parent_item)
        CheckBox parentItemCb;
        // 组名称
        @BindView(R.id.tv_parent_item)
        TextView parentItemTv;
        // 右文案
        @BindView(R.id.tv_right_item)
        TextView rightItemTv;
        // 右箭头图标
        @BindView(R.id.iv_right_item)
        ImageView rightItemIv;
        // 失效组
        // 组item容器
        @BindView(R.id.ly_invalid_parent)
        LinearLayout invalidParentLy;
        // 组名称
        @BindView(R.id.tv_parent_invalid_item)
        TextView invalidParentTv;
        // 右文案--清空
        @BindView(R.id.tv_right_invalid_item)
        TextView rightInvalidTv;

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
        public void updateUI(ShoppingCartBean model){
            if (model == null)
                return;
            if (model.isInvalidGoods){
                // 失效产品
                normalPatentLy.setVisibility(View.GONE);
                invalidParentLy.setVisibility(View.VISIBLE);
                // 更新失效产品ui
                if (StringUtil.isNotEmpty(model.sellerName))
                    invalidParentTv.setText(model.sellerName);
            }else {
                // 正常产品
                normalPatentLy.setVisibility(View.VISIBLE);
                invalidParentLy.setVisibility(View.GONE);
                // 更新正常产品ui
                parentItemCb.setChecked(model.isSelected);
                if (StringUtil.isNotEmpty(model.sellerName))
                    parentItemTv.setText(model.sellerName);
                if (StringUtil.isNotEmpty(model.freeShippingTips))
                    rightItemTv.setText(model.freeShippingTips);
            }
        }
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder cholder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_shoppingcart_child, null);
            cholder = new ChildViewHolder(convertView, mLayoutInflater);
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
        cholder.childSkuCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cholder.childSkuCb.setChecked(((CheckBox) v).isChecked());
                // 更新子选项
                updateChildCb(groupPosition, childPosition, ((CheckBox) v).isChecked());
            }
        });
        cholder.childGroupCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cholder.childGroupCb.setChecked(((CheckBox) v).isChecked());
                // 更新子选项
                updateChildCb(groupPosition, childPosition, ((CheckBox) v).isChecked());
            }
        });
        cholder.childAwardCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cholder.childAwardCb.setChecked(((CheckBox) v).isChecked());
                // 更新子选项
                updateChildCb(groupPosition, childPosition, ((CheckBox) v).isChecked());
            }
        });

        return convertView;
    }

    /**
      * 更新子选项
      * @author leibing
      * @createTime 2017/6/7
      * @lastModify 2017/6/7
      * @param groupPosition
      * @param childPosition
      * @param isSelected
      * @return
      */
    private void updateChildCb(int groupPosition, int childPosition, boolean isSelected){
        mData.get(groupPosition).product.get(childPosition).isSelected
                = isSelected;
        // 暴露子选接口
        if (mCheckInterface != null)
            mCheckInterface.checkChild(groupPosition, childPosition, isSelected);
    }

    /**
     * @className: ChildViewHolder
     * @classDescription: 子元素绑定器
     * @author: leibing
     * @createTime: 2017/6/5
     */
    static class ChildViewHolder{
        // 布局填充器
        private LayoutInflater mLayoutInflater;
        // 单品子项正常
        // 子项item容器
        @BindView(R.id.ly_child_sku_normal)
        LinearLayout childSkuNormalLy;
        // 子选
        @BindView(R.id.cb_child_sku)
        CheckBox childSkuCb;
        // 药品图
        @BindView(R.id.iv_child_sku)
        ImageView childSkuIv;
        // 实际价格
        @BindView(R.id.tv_real_price)
        TextView realPriceTv;
        // 原始价格
        @BindView(R.id.tv_origin_price)
        TextView originPriceTv;
        // 药品名称
        @BindView(R.id.tv_drug_name)
        TextView drugNameTv;
        // 规格
        @BindView(R.id.tv_drug_specification)
        TextView drugSpecificationTv;
        // 数量减
        @BindView(R.id.iv_child_sku_sub)
        ImageView childSkuSubIv;
        // 数量编辑
        @BindView(R.id.edt_child_sku_number)
        EditText childSkuNumEdt;
        // 数量加
        @BindView(R.id.iv_child_sku_add)
        ImageView childSkuAddIv;
        // 赠品容器
        @BindView(R.id.ly_item_child_sku_gift)
        LinearLayout childSkuGiftLy;

        // 组合子项正常
        // 子项item容器
        @BindView(R.id.ly_child_group_normal)
        LinearLayout childGroupNormalLy;
        // 子选
        @BindView(R.id.cb_child_group)
        CheckBox childGroupCb;
        // 药品图
        @BindView(R.id.iv_child_group)
        ImageView childGroupIv;
        // 药品名称
        @BindView(R.id.tv_group_drug_name)
        TextView groupDrugNameTv;
        // 价格
        @BindView(R.id.tv_group_real_price)
        TextView groupPriceTv;
        // 数量显示
        @BindView(R.id.tv_number)
        TextView numTv;
        // 商品减
        @BindView(R.id.iv_child_group_sub)
        ImageView childGroupSubIv;
        // 数量编辑
        @BindView(R.id.edt_child_group_number)
        EditText childGroupNumEdt;
        // 商品加
        @BindView(R.id.iv_child_group_add)
        ImageView childGroupAddIv;
        // 综合单项商品容器
        @BindView(R.id.ly_item_child_group)
        LinearLayout itemChildGroupLy;

        // 单品子项奖品
        // 子项item容器
        @BindView(R.id.ly_child_award)
        LinearLayout childAwardLy;
        // 子选
        @BindView(R.id.cb_child_award)
        CheckBox childAwardCb;
        // 药品图
        @BindView(R.id.iv_child_award)
        ImageView childAwardIv;
        // 实际价格
        @BindView(R.id.tv_award_real_price)
        TextView awardRealPriceTv;
        // 原始价格
        @BindView(R.id.tv_award_origin_price)
        TextView awardOriginPriceTv;
        // 药品名称
        @BindView(R.id.tv_award_drug_name)
        TextView awardDrugNameTv;
        // 规格
        @BindView(R.id.tv_award_drug_specification)
        TextView awardDrugSpecificationTv;
        // 积分兑换
        @BindView(R.id.iv_credits_exchange)
        ImageView creditsExchangeIv;
        // 奖品
        @BindView(R.id.iv_award)
        ImageView awardIv;

        // 单品子项失效
        // 子项item容器
        @BindView(R.id.ly_child_sku_invalid)
        LinearLayout childSkuInvalidLy;
        // 药品图
        @BindView(R.id.iv_child_sku_invalid)
        ImageView childSkuInvalidIv;
        // 实际价格
        @BindView(R.id.tv_sku_invalid_price)
        TextView skuInvalidPriceTv;
        // 数量显示
        @BindView(R.id.tv_sku_invalid_num)
        TextView skuInvalidNumTv;
        // 药品名称
        @BindView(R.id.tv_sku_invalid_drugname)
        TextView getSkuInvalidDrugNameTv;
        // 规格
        @BindView(R.id.tv_sku_invalid_drug_specification)
        TextView skuInvalidDrugSpecificationTv;
        // 赠品容器
        @BindView(R.id.ly_item_child_sku_invalid_gift)
        LinearLayout itemChildSkuInvalidGiftLy;

        // 组合子项失效
        // 子项item容器
        @BindView(R.id.ly_child_group_invalid)
        LinearLayout childGroupInvalidLy;
        // 套装名称
        @BindView(R.id.tv_invalid_drugname)
        TextView invalidDrugNameTv;
        // 价格
        @BindView(R.id.tv_invalid_price)
        TextView invalidPriceTv;
        // 组合单项失效商品容器
        @BindView(R.id.ly_child_group_invalid_container)
        LinearLayout invalidChildGroupContainerLy;

        /**
         * Constructor
         * @author leibing
         * @createTime 2017/6/5
         * @lastModify 2017/6/5
         * @param view
         * @param mLayoutInflater
         * @return
         */
        public ChildViewHolder(View view, LayoutInflater mLayoutInflater) {
            // bind view
            ButterKnife.bind(this, view);
            // set LayoutInflater
            this.mLayoutInflater = mLayoutInflater;
        }

        /**
         * 更新ui
         * @author leibing
         * @createTime 2017/6/5
         * @lastModify 2017/6/5
         * @param model
         * @return
         */
        public void updateUI(ShoppingCartBean.Product model){
            if (model == null)
                return;
            switch (model.productType){
                case PRODUCT_TYPE_SKU:
                    // 单品正常
                    updateSkuNormalUi(model);
                    break;
                case PRODUCT_TYPE_GROUP:
                    // 组合正常
                    updateGroupNormalUi(model);
                    break;
                case PRODUCT_TYPE_AWARD:
                    // 奖品正常
                    updateAwardNormalUi(model);
                    break;
                case PRODUCT_TYPE_SKU_INVALID:
                    // 单品失效
                    updateSkuInvalidUi(model);
                    break;
                case PRODUCT_TYPE_GROUP_INVALID:
                    // 组合失效
                    updateGroupInvalidUi(model);
                    break;
                case PRODUCT_TYPE_AWARD_INVALID:
                    // 奖品失效
                    updateAwardInvalidUi(model);
                    break;
                default:
                    break;
            }
        }

        /**
          * 奖品失效（目前奖品失效跟单品失效一样，以后可能会有改动，暂时隔离）
          * @author leibing
          * @createTime 2017/6/7
          * @lastModify 2017/6/7
          * @param model
          * @return
          */
        private void updateAwardInvalidUi(ShoppingCartBean.Product model) {
            // 单品失效
            updateSkuInvalidUi(model);
        }

        /**
          * 更新组合失效Ui
          * @author leibing
          * @createTime 2017/6/7
          * @lastModify 2017/6/7
          * @param model
          * @return
          */
        private void updateGroupInvalidUi(ShoppingCartBean.Product model) {
            childSkuNormalLy.setVisibility(View.GONE);
            childGroupNormalLy.setVisibility(View.GONE);
            childAwardLy.setVisibility(View.GONE);
            childSkuInvalidLy.setVisibility(View.GONE);
            childGroupInvalidLy.setVisibility(View.VISIBLE);

            if (model.groupProduct == null)
                return;
            // 套装名称
            if (StringUtil.isNotEmpty(model.groupProduct.productName))
                invalidDrugNameTv.setText(model.groupProduct.productName);
            // 价格
            if (model.groupProduct.groupPrice > 0)
                invalidPriceTv.setText("￥"
                        + StringUtil.doubleTwoDecimal(model.groupProduct.groupPrice));
            // 组合单项失效商品
            if (model.groupProduct.childList != null
                    && model.groupProduct.childList.size() != 0){
                invalidChildGroupContainerLy.removeAllViews();
                int size = model.groupProduct.childList.size();
                for (int i=0; i< size;i++) {
                    ShoppingCartBean.Product.GroupProduct.ChildProduct childProduct
                            = model.groupProduct.childList.get(i);
                    if (childProduct != null){
                        View childView = mLayoutInflater.inflate(R.layout.item_child_group_item_invalid,
                                null);
                        ImageView groupItemDrugIv
                                = (ImageView) childView.findViewById(R.id.iv_invalid_group_item_drug);
                        TextView childGroupPriceTv
                                = (TextView) childView.findViewById(R.id.tv_invalid_child_group_price);
                        TextView childGroupNumberTv
                                = (TextView) childView.findViewById(R.id.tv_invalid_child_group_number);
                        TextView childGroupDrugnameTv
                                = (TextView) childView.findViewById(R.id.tv_invalid_child_group_drugname);
                        TextView childGroupSpecificationTv
                                = (TextView) childView.findViewById(R.id.tv_invalid_child_group_specification);
                        if (StringUtil.isNotEmpty(childProduct.imgUrl))
                            ImageLoader.getInstance().load(
                                    AppManager.getInstance().currentActivity(), groupItemDrugIv,
                                    childProduct.imgUrl);
                        if (childProduct.price > 0)
                            childGroupPriceTv.setText("￥"
                                    + StringUtil.doubleTwoDecimal(childProduct.price));
                        if (childProduct.productAmount > 0)
                            childGroupNumberTv.setText("x" + childProduct.productAmount);
                        if (StringUtil.isNotEmpty(childProduct.productName))
                            childGroupDrugnameTv.setText(childProduct.productName);
                        if (StringUtil.isNotEmpty(childProduct.productQualification))
                            childGroupSpecificationTv.setText("规格："
                                    + childProduct.productQualification);
                        invalidChildGroupContainerLy.addView(childView);
                    }
                }
            }
        }

        /**
          * 更新单品失效Ui
          * @author leibing
          * @createTime 2017/6/7
          * @lastModify 2017/6/7
          * @param model
          * @return
          */
        private void updateSkuInvalidUi(ShoppingCartBean.Product model) {
            childSkuNormalLy.setVisibility(View.GONE);
            childGroupNormalLy.setVisibility(View.GONE);
            childAwardLy.setVisibility(View.GONE);
            childSkuInvalidLy.setVisibility(View.VISIBLE);
            childGroupInvalidLy.setVisibility(View.GONE);

            if (model.skuProduct == null)
                return;
            // 药品图
            if (StringUtil.isNotEmpty(model.skuProduct.imgUrl))
                ImageLoader.getInstance().load(AppManager.getInstance().currentActivity(),
                        childSkuInvalidIv, model.skuProduct.imgUrl);
            // 实际价格
            if (model.skuProduct.realPrice > 0)
                skuInvalidPriceTv.setText("￥"
                        + StringUtil.doubleTwoDecimal(model.skuProduct.realPrice));
            // 数量显示
            if (model.skuProduct.productAmount > 0)
                skuInvalidNumTv.setText("x" + model.skuProduct.productAmount);
            // 药品名称
            if (StringUtil.isNotEmpty(model.skuProduct.productName))
                getSkuInvalidDrugNameTv.setText(model.skuProduct.productName);
            // 规格
            if (StringUtil.isNotEmpty(model.skuProduct.productQualification))
                skuInvalidDrugSpecificationTv.setText("规格："
                        + model.skuProduct.productQualification);
            // 赠品
            if (model.skuProduct.gifts != null
                    && model.skuProduct.gifts.size() != 0){
                itemChildSkuInvalidGiftLy.removeAllViews();
                int giftSize = model.skuProduct.gifts.size();
                for (int i=0; i< giftSize; i++){
                    ShoppingCartBean.Product.SkuProduct.Gifts gift = model.skuProduct.gifts.get(i);
                    if (gift != null) {
                        View giftView = mLayoutInflater.inflate(R.layout.item_gifts_invalid, null);
                        TextView giftNameTv = (TextView) giftView.findViewById(R.id.tv_gift_invalid_name);
                        TextView giftCountTv = (TextView) giftView.findViewById(R.id.tv_gift_invalid_count);
                        if (StringUtil.isNotEmpty(gift.giftName))
                            giftNameTv.setText(gift.giftName);
                        if (gift.giftAmount > 0)
                            giftCountTv.setText("x" + gift.giftAmount);
                        itemChildSkuInvalidGiftLy.addView(giftView);
                    }
                }
            }
        }

        /**
          * 更新奖品正常Ui
          * @author leibing
          * @createTime 2017/6/7
          * @lastModify 2017/6/7
          * @param model
          * @return
          */
        private void updateAwardNormalUi(ShoppingCartBean.Product model) {
            childSkuNormalLy.setVisibility(View.GONE);
            childGroupNormalLy.setVisibility(View.GONE);
            childAwardLy.setVisibility(View.VISIBLE);
            childSkuInvalidLy.setVisibility(View.GONE);
            childGroupInvalidLy.setVisibility(View.GONE);

            // 子选
            childAwardCb.setChecked(model.isSelected);
            if (model.awardProduct == null)
                return;
            // 药品图
            if (StringUtil.isNotEmpty(model.awardProduct.imgUrl))
                ImageLoader.getInstance().load(AppManager.getInstance().currentActivity(),
                        childAwardIv, model.awardProduct.imgUrl);
            // 实际价格
            if (model.awardProduct.realPrice >= 0)
                awardRealPriceTv.setText("￥"
                        + StringUtil.doubleTwoDecimal(model.awardProduct.realPrice));
            // 原始价格
            if (model.awardProduct.originPrice >= 0)
                awardOriginPriceTv.setText("￥"
                        + StringUtil.doubleTwoDecimal(model.awardProduct.originPrice));
            // 药品名称
            if (StringUtil.isNotEmpty(model.awardProduct.awardName))
                awardDrugNameTv.setText(model.awardProduct.awardName);
            // 规格
            if (StringUtil.isNotEmpty(model.awardProduct.awardQualification))
                awardDrugSpecificationTv.setText("规格：" + model.awardProduct.awardQualification);
            switch (model.awardProduct.awardType){
                case AWARD_TYPE_AWARD:
                    // 奖品
                    creditsExchangeIv.setVisibility(View.GONE);
                    awardIv.setVisibility(View.VISIBLE);
                    break;
                case AWARD_CREDITS_EXCHANGE:
                    // 积分兑换
                    creditsExchangeIv.setVisibility(View.VISIBLE);
                    awardIv.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

        /**
          * 更新组合正常Ui
          * @author leibing
          * @createTime 2017/6/7
          * @lastModify 2017/6/7
          * @param model
          * @return
          */
        private void updateGroupNormalUi(ShoppingCartBean.Product model) {
            childSkuNormalLy.setVisibility(View.GONE);
            childGroupNormalLy.setVisibility(View.VISIBLE);
            childAwardLy.setVisibility(View.GONE);
            childSkuInvalidLy.setVisibility(View.GONE);
            childGroupInvalidLy.setVisibility(View.GONE);

            // 子选
            childGroupCb.setChecked(model.isSelected);
            if (model.groupProduct == null)
                return;
            // 药品图
            if (StringUtil.isNotEmpty(model.groupProduct.imgUrl))
                ImageLoader.getInstance().load(AppManager.getInstance().currentActivity(),
                        childGroupIv, model.groupProduct.imgUrl);
            // 药品名称
            if (StringUtil.isNotEmpty(model.groupProduct.productName))
                groupDrugNameTv.setText(model.groupProduct.productName);
            // 价格
            if (model.groupProduct.groupPrice > 0)
                groupPriceTv.setText("￥"
                        + StringUtil.doubleTwoDecimal(model.groupProduct.groupPrice));
            if (model.groupProduct.groupAmount > 0) {
                // 数量显示
                numTv.setText("x" + model.groupProduct.groupAmount);
                // 数量编辑
                childGroupNumEdt.setText(model.groupProduct.groupAmount + "");
            }
            // 综合单项商品
            if (model.groupProduct.childList != null
                    && model.groupProduct.childList.size() != 0){
                itemChildGroupLy.removeAllViews();
                int size = model.groupProduct.childList.size();
                for (int i=0; i< size;i++) {
                    ShoppingCartBean.Product.GroupProduct.ChildProduct childProduct
                            = model.groupProduct.childList.get(i);
                    if (childProduct != null){
                        View childView = mLayoutInflater.inflate(R.layout.item_child_group_item,
                                null);
                        ImageView groupItemDrugIv
                                = (ImageView) childView.findViewById(R.id.iv_group_item_drug);
                        TextView childGroupPriceTv
                                = (TextView) childView.findViewById(R.id.tv_child_group_price);
                        TextView childGroupNumberTv
                                = (TextView) childView.findViewById(R.id.tv_child_group_number);
                        TextView childGroupDrugnameTv
                                = (TextView) childView.findViewById(R.id.tv_child_group_drugname);
                        TextView childGroupSpecificationTv
                                = (TextView) childView.findViewById(R.id.tv_child_group_specification);
                        if (StringUtil.isNotEmpty(childProduct.imgUrl))
                            ImageLoader.getInstance().load(
                                    AppManager.getInstance().currentActivity(), groupItemDrugIv,
                                    childProduct.imgUrl);
                        if (childProduct.price > 0)
                            childGroupPriceTv.setText("￥"
                                    + StringUtil.doubleTwoDecimal(childProduct.price));
                        if (childProduct.productAmount > 0)
                            childGroupNumberTv.setText("x" + childProduct.productAmount);
                        if (StringUtil.isNotEmpty(childProduct.productName))
                            childGroupDrugnameTv.setText(childProduct.productName);
                        if (StringUtil.isNotEmpty(childProduct.productQualification))
                            childGroupSpecificationTv.setText("规格："
                                    + childProduct.productQualification);
                        itemChildGroupLy.addView(childView);
                    }
                }
            }
        }

        /**
          * 更新单品正常Ui
          * @author leibing
          * @createTime 2017/6/7
          * @lastModify 2017/6/7
          * @param model
          * @return
          */
        private void updateSkuNormalUi(ShoppingCartBean.Product model) {
            childSkuNormalLy.setVisibility(View.VISIBLE);
            childGroupNormalLy.setVisibility(View.GONE);
            childAwardLy.setVisibility(View.GONE);
            childSkuInvalidLy.setVisibility(View.GONE);
            childGroupInvalidLy.setVisibility(View.GONE);
            // 子选
            childSkuCb.setChecked(model.isSelected);
            if (model.skuProduct == null)
                return;
            // 药品图
            if (StringUtil.isNotEmpty(model.skuProduct.imgUrl))
                ImageLoader.getInstance().load(AppManager.getInstance().currentActivity(),
                    childSkuIv, model.skuProduct.imgUrl);
            // 实际价格
            if (model.skuProduct.realPrice > 0)
                realPriceTv.setText("￥"
                        + StringUtil.doubleTwoDecimal(model.skuProduct.realPrice));
            // 原始价格
            if (model.skuProduct.originPrice > 0)
                originPriceTv.setText("￥"
                        + StringUtil.doubleTwoDecimal(model.skuProduct.originPrice));
            // 药品名称
            if (StringUtil.isNotEmpty(model.skuProduct.productName))
                drugNameTv.setText(model.skuProduct.productName);
            // 规格
            if (StringUtil.isNotEmpty(model.skuProduct.productQualification))
                drugSpecificationTv.setText("规格：" + model.skuProduct.productQualification);
            // 数量编辑
            if (model.skuProduct.productAmount > 0)
                childSkuNumEdt.setText(model.skuProduct.productAmount + "");
            // 赠品
            if (model.skuProduct.gifts != null
                    && model.skuProduct.gifts.size() != 0){
                childSkuGiftLy.removeAllViews();
                int giftSize = model.skuProduct.gifts.size();
                for (int i=0; i< giftSize; i++){
                    ShoppingCartBean.Product.SkuProduct.Gifts gift = model.skuProduct.gifts.get(i);
                    if (gift != null) {
                        View giftView = mLayoutInflater.inflate(R.layout.item_gifts, null);
                        TextView giftNameTv = (TextView) giftView.findViewById(R.id.tv_gift_name);
                        TextView giftCountTv = (TextView) giftView.findViewById(R.id.tv_gift_count);
                        if (StringUtil.isNotEmpty(gift.giftName))
                            giftNameTv.setText(gift.giftName);
                        if (gift.giftAmount > 0)
                            giftCountTv.setText("x" + gift.giftAmount);
                        childSkuGiftLy.addView(giftView);
                    }
                }
            }
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

package module.jk.cn.jkshoppingcart.module.orderconfirm.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.common.ImageLoader;
import module.jk.cn.jkshoppingcart.common.ShoppingCartDialog;
import module.jk.cn.jkshoppingcart.common.StringUtil;
import module.jk.cn.jkshoppingcart.common.ToastUtils;
import module.jk.cn.jkshoppingcart.module.AppManager;
import module.jk.cn.jkshoppingcart.module.BaseFragmentActivity;
import module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponActivity;
import module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel;
import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderConfirmBean;
import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderInfoModel;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.CASH_ON_DELIVER;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.INVALID_NUMBER;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.NO_AVAILABLE_COUPON;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.NO_AVAILABLE_RED_ENVELOPE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.ORDER_CONFIRM;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_ACTIVITY_OPTIMAL_POSITION;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_COUPON_LIST;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_COUPON_REQUEST_ID;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_INTEGRAL_OPTIMAL_POSITION;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_ORIGIN_COUPON_VALUE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_REAL_COUPON_VALUE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_TOTAL_AMOUNT;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAY_ONLINE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderInfoModel.JIANKE_SELF_SUPPORT;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_CREDITS_EXCHANGE;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_GROUP;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_SKU;

/**
 * @className: OrderConfirmActivity
 * @classDescription: 订单确认UI层（Activity）
 * @author: leibing
 * @createTime: 2017/6/9
 */
public class OrderConfirmActivity extends BaseFragmentActivity
        implements OrderConfirmViewModel.ViewModelListener {
    // 返回
    @BindView(R.id.iv_back)
    ImageView backIv;
    // 标题
    @BindView(R.id.tv_title)
    TextView titleTv;
    // 右按钮
    @BindView(R.id.btn_edit)
    Button editBtn;
    // 有收货人地址布局
    @BindView(R.id.rly_has_receive_address)
    RelativeLayout hasReceiveAddressRly;
    // 收货人名称
    @BindView(R.id.tv_receive_name)
    TextView receiveNameTv;
    // 联系电话
    @BindView(R.id.tv_receive_phone)
    TextView receivePhoneTv;
    // 默认地址图标
    @BindView(R.id.iv_receive_default)
    ImageView receiveDefaultIv;
    // 详细地址
    @BindView(R.id.tv_receive_address)
    TextView receiveAddressTv;
    // 无收货人地址布局
    @BindView(R.id.rly_no_receive_address)
    RelativeLayout noReceiveAddressRly;
    // 身份校验默认显示、编辑布局
    @BindView(R.id.ly_default_edit)
    LinearLayout defaultEditLy;
    // 身份证编辑
    @BindView(R.id.edt_identify)
    EditText identifyEdt;
    // 身份证清空
    @BindView(R.id.iv_clear_edit)
    ImageView clearEditIv;
    // 身份证保存
    @BindView(R.id.btn_save)
    Button saveBtn;
    // 身份校验保存成功布局
    @BindView(R.id.ly_identity_save_success)
    LinearLayout identitySaveSuccessLy;
    // 身份证号
    @BindView(R.id.tv_identity_card)
    TextView identityCardTv;
    // 支付方式
    @BindView(R.id.tv_pay_method)
    TextView payMethodTv;
    // 订单信息容器
    @BindView(R.id.ly_order_info)
    LinearLayout orderInfoLy;
    // 底部地址布局
    @BindView(R.id.ly_settle_address)
    LinearLayout settleAddressLy;
    // 底部地址
    @BindView(R.id.tv_settle_address)
    TextView settleAddressTv;
    // 合计金额
    @BindView(R.id.tv_settle_total)
    TextView settleTotalTv;
    // 可用优惠券个数
    TextView couponAvailableTv;
    // 优惠券金额
    TextView couponAmountTv;
    // 可用红包个数
    TextView redEnvelopeAvailableTv;
    // 红包金额
    TextView redEnvelopeAmountTv;
    // 现金劵编辑框
    EditText useCouponEdt;
    // 健客自营合计金额
    TextView jkTotalTv;
    // 健客自营分组合计金额
    private double jkGroupTotalAmount = 0;
    // 健客自营优惠劵金额
    private double jkCouponValue = 0;
    // 总合计金额
    private double totalAmount = 0;
    // 总运费
    private double totalFreight = 0;
    // 是否显示现金劵使用布局
    boolean isShowCouponUses = false;
    // 最优活动优惠券位置
    private int activityPosition = -1;
    // 最优积分优惠券位置
    private int integralPosition = -1;
    // 是否在线支付
    boolean isPayOnline = false;
    // logic process
    private OrderConfirmViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        // bind buffer knife
        ButterKnife.bind(this);
        // init
        initView();
        // set listener
        setListener();
        // init logic process
        initViewModel();
    }

    /**
      * set listener
      * @author leibing
      * @createTime 2017/6/12
      * @lastModify 2017/6/12
      * @param
      * @return
      */
    private void setListener() {
        // 身份证编辑监听
        identifyEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 身份证布局显示
                identifyViewShow();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
      * 身份证布局显示
      * @author leibing
      * @createTime 2017/6/12
      * @lastModify 2017/6/12
      * @param
      * @return
      */
    private void identifyViewShow(){
        if (StringUtil.isEmpty(identifyEdt.getText().toString())){
            clearEditIv.setVisibility(View.GONE);
            saveBtn.setBackgroundResource(R.color.gray);
            saveBtn.setEnabled(false);
        }else {
            clearEditIv.setVisibility(View.VISIBLE);
            saveBtn.setBackgroundResource(R.color.text_color_blue);
            saveBtn.setEnabled(true);
        }
    }

    /**
      * init logic process
      * @author leibing
      * @createTime 2017/6/12
      * @lastModify 2017/6/12
      * @param
      * @return
      */
    private void initViewModel() {
        mViewModel = new OrderConfirmViewModel(this);
    }

    /**
     * init View
     * @author leibing
     * @createTime 2017/6/10
     * @lastModify 2017/6/10
     * @param
     * @return
     */
    private void initView() {
        editBtn.setVisibility(View.GONE);
        backIv.setVisibility(View.VISIBLE);
        titleTv.setText(ORDER_CONFIRM);
        // 身份证布局显示
        identifyViewShow();
    }

    @OnClick({R.id.iv_back, R.id.rly_has_receive_address, R.id.rly_no_receive_address,
            R.id.btn_save, R.id.iv_clear_edit, R.id.iv_edit, R.id.rly_payment,
            R.id.btn_settle_commit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                // 返回
                AppManager.getInstance().finishActivity();
                break;
            case R.id.rly_has_receive_address:
            case R.id.rly_no_receive_address:
                // 选择地址
                break;
            case R.id.btn_save:
                // 身份证号保存
                if (!StringUtil.isIDCard(identifyEdt.getText().toString())){
                    toastShow(INVALID_NUMBER);
                    return;
                }
                defaultEditLy.setVisibility(View.GONE);
                identitySaveSuccessLy.setVisibility(View.VISIBLE);
                identityCardTv.setText(identifyEdt.getText().toString());
                break;
            case R.id.iv_clear_edit:
                // 身份证号清空
                identifyEdt.setText("");
                break;
            case R.id.iv_edit:
                // 身份证号编辑
                defaultEditLy.setVisibility(View.VISIBLE);
                identitySaveSuccessLy.setVisibility(View.GONE);
                break;
            case R.id.rly_payment:
                // 支付方式选择
                ShoppingCartDialog.getInstance().createPaymentSelectDialog(this, isPayOnline,
                        new ShoppingCartDialog.DialogCallBack() {
                    @Override
                    public void leftBtnListener() {
                    }

                    @Override
                    public void rightBtnListener(String content) {
                    }

                    @Override
                    public void selectedListener(String content) {
                        payMethodTv.setText(content);
                        switch (content){
                            case PAY_ONLINE:
                                // 在线支付
                                isPayOnline = true;
                                break;
                            case CASH_ON_DELIVER:
                                isPayOnline = false;
                                // 货到付款
                                break;
                            default:
                                isPayOnline = false;
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.btn_settle_commit:
                // 提交订单
                break;
        }
    }

    @Override
    public void updateUI(OrderConfirmBean bean) {
        if (bean == null)
            return;
        if (bean.receiveAddress == null){
            // 无地址
            hasReceiveAddressRly.setVisibility(View.GONE);
            noReceiveAddressRly.setVisibility(View.VISIBLE);
        }else {
            // 有地址
            hasReceiveAddressRly.setVisibility(View.VISIBLE);
            noReceiveAddressRly.setVisibility(View.GONE);
            // 收货人名称
            if (StringUtil.isNotEmpty(bean.receiveAddress.receiveName))
                receiveNameTv.setText(bean.receiveAddress.receiveName);
            // 联系电话
            if (StringUtil.isNotEmpty(bean.receiveAddress.contactPhone))
                receivePhoneTv.setText(bean.receiveAddress.contactPhone);
            // 默认地址图标
            if (bean.receiveAddress.isDefaultAddress){
                receiveDefaultIv.setVisibility(View.VISIBLE);
            }else {
                receiveDefaultIv.setVisibility(View.GONE);
            }
            // 详细地址
            if (StringUtil.isNotEmpty(bean.receiveAddress.detailAddress))
                receiveAddressTv.setText(bean.receiveAddress.detailAddress);
        }
        // 支付方式
        if (StringUtil.isNotEmpty(bean.payMode)) {
            payMethodTv.setText(bean.payMode);
            switch (bean.payMode){
                case PAY_ONLINE:
                    // 在线支付
                    isPayOnline = true;
                    break;
                case CASH_ON_DELIVER:
                    isPayOnline = false;
                    // 货到付款
                    break;
                default:
                    isPayOnline = false;
                    break;
            }
        }
        // 订单信息
        if (bean.productInfoList != null
                && bean.productInfoList.size() != 0){
            orderInfoLy.removeAllViews();
            totalAmount = 0;
            int infoSize = bean.productInfoList.size();
            for (int i=0;i<infoSize;i++){
                final OrderInfoModel model = bean.productInfoList.get(i);
                double groupFreight = 0;
                double groupTotalAmount = 0;
                if (model != null) {
                    // 厂家布局
                    View manufacturerView = LayoutInflater.from(this).inflate(
                            R.layout.layout_order_info_manufacturer, null);
                    // 厂家名称
                    TextView manufacturerNameTv =
                            (TextView) manufacturerView.findViewById(R.id.tv_manufacturer_name);
                    if (StringUtil.isNotEmpty(model.sellerName))
                        manufacturerNameTv.setText(model.sellerName);
                    // 添加厂家布局到订单信息
                    orderInfoLy.addView(manufacturerView);
                    // 产品信息
                    if (model.product != null && model.product.size() != 0) {
                        int productSize = model.product.size();
                        for (int j = 0; j < productSize; j++) {
                            OrderInfoModel.Product product = model.product.get(j);
                            if (product != null) {
                                switch (product.productType) {
                                    case PRODUCT_TYPE_SKU:
                                        // 单品
                                        View skuView = LayoutInflater.from(this).inflate(
                                                R.layout.layout_order_info_sku_product, null);
                                        // 单品图片
                                        ImageView skuProductPicIv =
                                                (ImageView) skuView.findViewById(R.id.iv_sku_product_pic);
                                        // 单品名称
                                        TextView skuProductNameTv
                                                = (TextView) skuView.findViewById(R.id.tv_sku_product_name);
                                        // 规格
                                        TextView skuProductSpecificationTv
                                                = (TextView) skuView.findViewById(R.id.tv_sku_product_specification);
                                        // 价格
                                        TextView skuProductPriceTv
                                                = (TextView) skuView.findViewById(R.id.tv_sku_product_price);
                                        // 数量
                                        TextView skuProductCountTv
                                                = (TextView) skuView.findViewById(R.id.tv_sku_product_count);
                                        // 赠品容器
                                        LinearLayout skuGiftLy
                                                = (LinearLayout) skuView.findViewById(R.id.ly_order_info_sku_gift);
                                        if (product.skuProduct != null) {
                                            if (StringUtil.isNotEmpty(product.skuProduct.imgUrl))
                                                ImageLoader.getInstance()
                                                        .load(this.getApplicationContext(),
                                                                skuProductPicIv, product.skuProduct.imgUrl);
                                            if (StringUtil.isNotEmpty(product.skuProduct.productName))
                                                skuProductNameTv.setText(product.skuProduct.productName);
                                            if (StringUtil.isNotEmpty(product.skuProduct.productQualification))
                                                skuProductSpecificationTv.setText(product.skuProduct.productQualification);
                                            if (product.skuProduct.price > 0)
                                                skuProductPriceTv.setText("￥"
                                                        + StringUtil.doubleTwoDecimal(product.skuProduct.price));
                                            if (product.skuProduct.productAmount > 0)
                                                skuProductCountTv.setText("x"
                                                        + product.skuProduct.productAmount);
                                            // 计算合计金额
                                            if (product.skuProduct.price > 0
                                                    && product.skuProduct.productAmount > 0) {
                                                groupTotalAmount += product.skuProduct.price
                                                        * product.skuProduct.productAmount;
                                            }
                                            if (product.skuProduct.gifts != null
                                                    && product.skuProduct.gifts.size() != 0) {
                                                int giftSize = product.skuProduct.gifts.size();
                                                skuGiftLy.removeAllViews();
                                                for (int z = 0; z < giftSize; z++) {
                                                    OrderInfoModel.Product.SkuProduct.Gifts gifts
                                                            = product.skuProduct.gifts.get(z);
                                                    if (gifts != null) {
                                                        View giftView = LayoutInflater.from(this)
                                                                .inflate(R.layout.layout_order_info_gifts, null);
                                                        // 赠品名称
                                                        TextView giftNameTv
                                                                = (TextView) giftView.findViewById(R.id.tv_order_info_gift_name);
                                                        // 赠品数量
                                                        TextView giftCountTv
                                                                = (TextView) giftView.findViewById(R.id.tv_order_info_gift_count);
                                                        if (StringUtil.isNotEmpty(gifts.giftName))
                                                            giftNameTv.setText(gifts.giftName);
                                                        if (gifts.giftAmount > 0)
                                                            giftCountTv.setText("x" + gifts.giftAmount);
                                                        // 添加赠品到赠品容器
                                                        skuGiftLy.addView(giftView);
                                                    }
                                                }
                                            }
                                        }
                                        // 添加单品到容器
                                        orderInfoLy.addView(skuView);
                                        break;
                                    case PRODUCT_TYPE_GROUP:
                                        // 组合
                                        View groupView = LayoutInflater.from(this)
                                                .inflate(R.layout.layout_order_info_group_product, null);
                                        // 套装名称
                                        TextView groupPruductNameTv
                                                = (TextView) groupView.findViewById(R.id.tv_group_pruduct_name);
                                        // 价格
                                        TextView groupProductPriceTv
                                                = (TextView) groupView.findViewById(R.id.tv_group_product_price);
                                        // 数量
                                        TextView groupProductCountTv
                                                = (TextView) groupView.findViewById(R.id.tv_group_product_count);
                                        // 套装子商品容器
                                        LinearLayout groupProductContainerLy
                                                = (LinearLayout) groupView.findViewById(R.id.ly_group_product_container);
                                        if (product.groupProduct != null) {
                                            if (StringUtil.isNotEmpty(product.groupProduct.productName))
                                                groupPruductNameTv.setText(product.groupProduct.productName);
                                            if (product.groupProduct.groupPrice > 0)
                                                groupProductPriceTv.setText("￥"
                                                        + StringUtil.doubleTwoDecimal(product.groupProduct.groupPrice));
                                            if (product.groupProduct.groupAmount > 0)
                                                groupProductCountTv.setText("x" + product.groupProduct.groupAmount);
                                            // 计算合计金额
                                            if (product.groupProduct.groupPrice > 0
                                                    && product.groupProduct.groupAmount > 0) {
                                                groupTotalAmount += product.groupProduct.groupPrice
                                                        * product.groupProduct.groupAmount;
                                            }
                                            if (product.groupProduct.childList != null
                                                    && product.groupProduct.childList.size() != 0) {
                                                groupProductContainerLy.removeAllViews();
                                                int childSize = product.groupProduct.childList.size();
                                                for (int c = 0; c < childSize; c++) {
                                                    OrderInfoModel.Product.GroupProduct.ChildProduct childProduct
                                                            = product.groupProduct.childList.get(c);
                                                    if (childProduct != null) {
                                                        View childProductView = LayoutInflater.from(this)
                                                                .inflate(R.layout.layout_order_info_group_product_item, null);
                                                        // 组合子商品图片
                                                        ImageView groupItemPicIv = (ImageView)
                                                                childProductView.findViewById(R.id.iv_group_item_product_pic);
                                                        // 组合子商品名称
                                                        TextView groupItemProductNameTv = (TextView)
                                                                childProductView.findViewById(R.id.tv_group_item_product_name);
                                                        // 组合子商品规格
                                                        TextView groupItemSpecificationTv = (TextView) childProductView
                                                                .findViewById(R.id.tv_group_item_product_specification);
                                                        // 组合子商品价格
                                                        TextView groupItemProductPriceTv = (TextView) childProductView
                                                                .findViewById(R.id.tv_group_item_product_price);
                                                        // 组合子商品数量
                                                        TextView groupItemProductCountTv = (TextView) childProductView
                                                                .findViewById(R.id.tv_group_item_product_count);
                                                        if (StringUtil.isNotEmpty(childProduct.imgUrl))
                                                            ImageLoader.getInstance().load(this.getApplicationContext(),
                                                                    groupItemPicIv, childProduct.imgUrl);
                                                        if (StringUtil.isNotEmpty(childProduct.productName))
                                                            groupItemProductNameTv.setText(childProduct.productName);
                                                        if (StringUtil.isNotEmpty(childProduct.productQualification))
                                                            groupItemSpecificationTv.setText(childProduct.productQualification);
                                                        if (childProduct.price > 0)
                                                            groupItemProductPriceTv.setText("￥"
                                                                    + StringUtil.doubleTwoDecimal(childProduct.price));
                                                        if (childProduct.productAmount > 0)
                                                            groupItemProductCountTv.setText("x"
                                                                    + childProduct.productAmount);
                                                        // 添加组合子商品到容器
                                                        groupProductContainerLy.addView(childProductView);
                                                    }
                                                }
                                            }
                                        }
                                        // 添加组合到容器
                                        orderInfoLy.addView(groupView);
                                        break;
                                    case PRODUCT_TYPE_AWARD:
                                        // 奖品
                                        View awardView = LayoutInflater.from(this).inflate(
                                                R.layout.layout_order_info_award_product, null);
                                        // 奖品图片
                                        ImageView awardProductPicIv =
                                                (ImageView) awardView.findViewById(R.id.iv_award_product_pic);
                                        // 奖品名称
                                        TextView awardProductNameTv
                                                = (TextView) awardView.findViewById(R.id.tv_award_product_name);
                                        // 规格
                                        TextView awardProductSpecificationTv
                                                = (TextView) awardView.findViewById(R.id.tv_award_product_specification);
                                        // 价格
                                        TextView awardProductPriceTv
                                                = (TextView) awardView.findViewById(R.id.tv_award_product_price);
                                        // 数量
                                        TextView awardProductCountTv
                                                = (TextView) awardView.findViewById(R.id.tv_award_product_count);
                                        // 积分兑换图标
                                        ImageView creditsExchangeIv
                                                = (ImageView) awardView.findViewById(R.id.iv_order_info_credits_exchange);
                                        // 奖品图标
                                        ImageView awardIv = (ImageView) awardView
                                                .findViewById(R.id.iv_order_info_award);
                                        if (product.awardProduct != null) {
                                            switch (product.awardProduct.awardType) {
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
                                            if (StringUtil.isNotEmpty(product.awardProduct.imgUrl))
                                                ImageLoader.getInstance()
                                                        .load(this.getApplicationContext(),
                                                                awardProductPicIv, product.awardProduct.imgUrl);
                                            if (StringUtil.isNotEmpty(product.awardProduct.awardName))
                                                awardProductNameTv.setText(product.awardProduct.awardName);
                                            if (StringUtil.isNotEmpty(product.awardProduct.awardQualification))
                                                awardProductSpecificationTv.setText(product.awardProduct.awardQualification);
                                            if (product.awardProduct.awardPrice > 0)
                                                awardProductPriceTv.setText("￥"
                                                        + StringUtil.doubleTwoDecimal(product.awardProduct.awardPrice));
                                            if (product.awardProduct.awardAmount > 0)
                                                awardProductCountTv.setText("x"
                                                        + product.awardProduct.awardAmount);
                                        }
                                        // 添加单品到容器
                                        orderInfoLy.addView(awardView);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                    // 当卖家类型为健客自营才允许有优惠券、红包、现金劵模块
                    if (model.sellerType == JIANKE_SELF_SUPPORT) {
                        // 优惠券、红包、现金券模块
                        View couponView = LayoutInflater.from(this)
                                .inflate(R.layout.layout_order_info_coupon, null);
                        // 可用优惠券个数
                        couponAvailableTv =
                                (TextView) couponView.findViewById(R.id.tv_coupon_available);
                        // 优惠劵金额
                        couponAmountTv = (TextView) couponView.findViewById(R.id.tv_coupon_amount);
                        boolean isCouponAvailable;
                        if (model.mUseCouponModelList != null
                                && model.mUseCouponModelList.size() != 0){
                            couponAvailableTv.setText(model.mUseCouponModelList.size() + "个可用");
                            couponAvailableTv.setVisibility(View.VISIBLE);
                            couponAmountTv.setText("未使用");
                            isCouponAvailable = true;
                        }else {
                            couponAvailableTv.setVisibility(View.GONE);
                            couponAmountTv.setText("无可用");
                            isCouponAvailable = false;
                        }
                        // 使用优惠券item点击事件
                        final boolean finalIsCouponAvailable = isCouponAvailable;
                        couponView.findViewById(R.id.rly_coupon).setOnClickListener(
                                new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!finalIsCouponAvailable){
                                    toastShow(NO_AVAILABLE_COUPON);
                                }
                                // 跳转到使用优惠券页面
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(PAGE_INTENT_COUPON_LIST,
                                        model.mUseCouponModelList);
                                bundle.putDouble(PAGE_INTENT_TOTAL_AMOUNT, jkGroupTotalAmount
                                        + jkCouponValue);
                                bundle.putInt(PAGE_INTENT_ACTIVITY_OPTIMAL_POSITION, activityPosition);
                                bundle.putInt(PAGE_INTENT_INTEGRAL_OPTIMAL_POSITION, integralPosition);
                                bundle.putDouble(PAGE_INTENT_ORIGIN_COUPON_VALUE, jkCouponValue);
                                startTargetActivityForResult(UseCouponActivity.class, bundle,
                                        PAGE_INTENT_COUPON_REQUEST_ID);
                            }
                        });
                        // 可用红包个数
                        redEnvelopeAvailableTv = (TextView)
                                couponView.findViewById(R.id.tv_red_envelope_available);
                        // 红包金额
                        redEnvelopeAmountTv
                                = (TextView) couponView.findViewById(R.id.tv_red_envelope_amount);
                        boolean isRedEnvelopeAvailable;
                        if (model.mUseRedEnvelopeModelList != null
                                && model.mUseRedEnvelopeModelList.size() != 0){
                            redEnvelopeAvailableTv.setText(model.mUseRedEnvelopeModelList.size() + "个可用");
                            redEnvelopeAvailableTv.setVisibility(View.VISIBLE);
                            redEnvelopeAmountTv.setText("未使用");
                            isRedEnvelopeAvailable = true;
                        }else {
                            redEnvelopeAvailableTv.setVisibility(View.GONE);
                            redEnvelopeAmountTv.setText("无可用");
                            isRedEnvelopeAvailable = false;
                        }
                        // 使用红包item点击事件
                        final boolean finalIsRedEnvelopeAvailable = isRedEnvelopeAvailable;
                        couponView.findViewById(R.id.rly_red_envelope).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!finalIsRedEnvelopeAvailable){
                                    toastShow(NO_AVAILABLE_RED_ENVELOPE);
                                }
                            }
                        });
                        // 现金劵使用布局
                        final LinearLayout cashCouponUsesLy
                                = (LinearLayout) couponView.findViewById(R.id.ly_cash_coupon_uses);
                        // 现金劵编辑框
                        useCouponEdt = (EditText) couponView.findViewById(R.id.edt_use_coupon);
                        // 现金劵使用按钮
                        Button useCouponBtn = (Button) couponView.findViewById(R.id.btn_use_coupon);
                        // 现金劵使用按钮事件
                        useCouponBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        // 现金券item点击事件
                        couponView.findViewById(R.id.rly_cash_coupon).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isShowCouponUses){
                                    cashCouponUsesLy.setVisibility(View.VISIBLE);
                                }else {
                                    cashCouponUsesLy.setVisibility(View.GONE);
                                }
                                isShowCouponUses = !isShowCouponUses;
                            }
                        });
                        // 添加优惠券、红包、现金券模块到容器
                        orderInfoLy.addView(couponView);
                    }
                    // 添加分割粗线
                    View boldDiv = LayoutInflater.from(this)
                            .inflate(R.layout.layout_line_gray_big_interval, null);
                    orderInfoLy.addView(boldDiv);
                    // 选填要求模块
                    View optionalView = LayoutInflater.from(this)
                            .inflate(R.layout.layout_order_info_optional, null);
                    // 选填
                    EditText optionalEdt = (EditText) optionalView.findViewById(R.id.edt_optional);
                    // 添加选填模块到容器
                    orderInfoLy.addView(optionalView);
                    // 添加分割粗线
                    boldDiv = LayoutInflater.from(this)
                            .inflate(R.layout.layout_line_gray_big_interval, null);
                    orderInfoLy.addView(boldDiv);
                    // 发票模块
                    View invoiceView = LayoutInflater.from(this)
                            .inflate(R.layout.layout_order_info_invoice, null);
                    // 发票抬头布局
                    final LinearLayout invoiceTitleLy
                            = (LinearLayout) invoiceView.findViewById(R.id.ly_invoice_title);
                    // 发票编辑框
                    EditText invoiceTitleEdit
                            = (EditText) invoiceView.findViewById(R.id.edt_invoice_title);
                    // 发票选项
                    CheckBox invoiceCb = (CheckBox) invoiceView.findViewById(R.id.cb_invoice);
                    // 发票选项点击事件
                    invoiceCb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (((CheckBox) v).isChecked()){
                                invoiceTitleLy.setVisibility(View.VISIBLE);
                            }else {
                                invoiceTitleLy.setVisibility(View.GONE);
                            }
                        }
                    });
                    // 计算合计金额(处理运费)
                    if (model.freight > 0) {
                        groupFreight += model.freight;
                        groupTotalAmount+=groupFreight;
                    }
                    // 运费
                    TextView freightTv = (TextView) invoiceView.findViewById(R.id.tv_freight);
                    if (groupFreight > 0)
                        freightTv.setText("￥" + StringUtil.doubleTwoDecimal(groupFreight));
                    // 处理健客自营合计金额
                    if (model.sellerType == JIANKE_SELF_SUPPORT){
                        // 合计金额（健客自营合计金额）
                        jkTotalTv = (TextView) invoiceView.findViewById(R.id.tv_total);
                        jkGroupTotalAmount = groupTotalAmount;
                        if (groupTotalAmount > 0)
                            jkTotalTv.setText("￥" + StringUtil.doubleTwoDecimal(groupTotalAmount));
                        // 计算最佳优惠劵选择
                        if (mViewModel != null)
                            mViewModel.couponOptimalDeal(model.mUseCouponModelList, jkGroupTotalAmount);
                    }else {
                        // 合计金额
                        TextView totalTv = (TextView) invoiceView.findViewById(R.id.tv_total);
                        if (groupTotalAmount > 0)
                            totalTv.setText("￥" + StringUtil.doubleTwoDecimal(groupTotalAmount));
                    }
                    // 添加发票模块到容器
                    orderInfoLy.addView(invoiceView);
                    // 添加分割粗线
                    boldDiv = LayoutInflater.from(this)
                            .inflate(R.layout.layout_line_gray_big_interval, null);
                    orderInfoLy.addView(boldDiv);
                    // 计算总合计金额
                    totalAmount+=groupTotalAmount;
                    // 计算总运费
                    totalFreight+=groupFreight;
                }
            }
            // 显示合计金额Ui
            settleTotalTv.setText("合计：￥" + totalAmount);
        }
    }

    @Override
    public void toastShow(String msg) {
        ToastUtils.show(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAGE_INTENT_COUPON_REQUEST_ID
                && resultCode == RESULT_OK){
            // 选择优惠劵处理
            double originCouponValue = data.getDoubleExtra(PAGE_INTENT_ORIGIN_COUPON_VALUE, 0.0);
            double realCouponValue = data.getDoubleExtra(PAGE_INTENT_REAL_COUPON_VALUE, 0.0);
            if (realCouponValue > 0) {
                jkCouponValue = realCouponValue;
                couponAmountTv.setTextColor(getResources().getColor(R.color.order_conform_red_txt));
                couponAmountTv.setText("-￥" + StringUtil.doubleTwoDecimal(realCouponValue));
            }
            jkGroupTotalAmount-= (realCouponValue - originCouponValue);
            totalAmount -= (realCouponValue - originCouponValue);
            // 显示合计金额Ui
            if (settleTotalTv != null)
                settleTotalTv.setText("合计：￥" + totalAmount);
            // 显示健客自营分组合计金额Ui
            if (jkTotalTv != null)
                jkTotalTv.setText("￥" + StringUtil.doubleTwoDecimal(jkGroupTotalAmount));
        }
    }

    @Override
    public void couponOptimal(int activityPosition, int integralPosition,
                              ArrayList<UseCouponModel> mUseCouponModelList) {
        // 处理优惠券最优处理ui显示
        jkCouponValue = mUseCouponModelList.get(activityPosition).couponValue
                + mUseCouponModelList.get(integralPosition).couponValue;
        couponAmountTv.setTextColor(getResources().getColor(R.color.order_conform_red_txt));
        couponAmountTv.setText("-￥" + StringUtil.doubleTwoDecimal(jkCouponValue));
        // 初始化最优活动优惠券、最优积分优惠券位置
        this.activityPosition = activityPosition;
        this.integralPosition = integralPosition;
        // 计算合计金额
        jkGroupTotalAmount-= jkCouponValue;
        totalAmount -= jkCouponValue;
        // 显示合计金额Ui
        if (settleTotalTv != null)
            settleTotalTv.setText("合计：￥" + totalAmount);
        // 显示健客自营分组合计金额Ui
        if (jkTotalTv != null)
            jkTotalTv.setText("￥" + StringUtil.doubleTwoDecimal(jkGroupTotalAmount));
    }
}

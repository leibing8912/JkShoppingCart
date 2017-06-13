package module.jk.cn.jkshoppingcart.module.orderconfirm.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.module.AppManager;
import module.jk.cn.jkshoppingcart.module.BaseFragmentActivity;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_COUPON_LIST;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_ORIGIN_COUPON_VALUE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_REAL_COUPON_VALUE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_TOTAL_AMOUNT;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.ACTIVITY_COUPON_TYPE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.INTEGRAL_COUPON_TYPE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.OTHER_COUPON_TYPE;

/**
 * @className: UseCouponActivity
 * @classDescription: 使用优惠劵
 * @author: leibing
 * @createTime: 2017/6/10
 */
public class UseCouponActivity extends BaseFragmentActivity implements UseCouponAdapter.CheckInterface {
    // 使用优惠券文案
    public final static String  USE_COUPON = "使用优惠券";
    // 使用规则文案
    public final static String SERVICE_REGULATIONS = "使用规则";
    // 返回
    @BindView(R.id.iv_back)
    ImageView backIv;
    // 标题
    @BindView(R.id.tv_title)
    TextView titleTv;
    // 右按钮
    @BindView(R.id.btn_edit)
    Button editBtn;
    // 列表
    @BindView(R.id.lv_use_coupon)
    ListView useCouponLv;
    // 优惠累计金额
    @BindView(R.id.tv_discount_amount)
    TextView discountAmountTv;
    // 适配器
    private UseCouponAdapter mAdapter;
    // 数据源
    private ArrayList<UseCouponModel> mData;
    // 待付款总金额
    private double totalAmount;
    // 原始优惠金额
    private double originDiscountAmount;
    // 总计优惠金额
    private double totalDiscountAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_coupon);
        // bind buffer knife
        ButterKnife.bind(this);
        // init
        init();
        // set adapter
        setAdapter();
        // get intent to update ui
        getIntentUpdateUi();
    }

    /**
      * get intent to update ui
      * @author leibing
      * @createTime 2017/6/13
      * @lastModify 2017/6/13
      * @param
      * @return
      */
    private void getIntentUpdateUi() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mData = (ArrayList<UseCouponModel>) bundle.getSerializable(PAGE_INTENT_COUPON_LIST);
            totalAmount = bundle.getDouble(PAGE_INTENT_TOTAL_AMOUNT);
            originDiscountAmount = bundle.getDouble(PAGE_INTENT_ORIGIN_COUPON_VALUE);
            totalDiscountAmount = originDiscountAmount;
        }
        // 更新数据源
        if (mAdapter != null)
            mAdapter.setData(mData);
        // 计算优惠金额
        calculateDiscountAmount();
    }

    /**
      * set adapter
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param
      * @return
      */
    private void setAdapter() {
        // init data
        mData = new ArrayList<>();
        // init adapter
        mAdapter = new UseCouponAdapter(this, mData);
        // 设置选项接口
        mAdapter.setCheckInterface(this);
        // set adapter
        useCouponLv.setAdapter(mAdapter);
    }

    /**
      * init
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param
      * @return
      */
    private void init() {
        backIv.setVisibility(View.VISIBLE);
        titleTv.setText(USE_COUPON);
        editBtn.setText(SERVICE_REGULATIONS);
    }

    @Override
    public void itemCheck(int position, boolean isChecked) {
        mData.get(position).isCouponSelected = isChecked;
        switch (mData.get(position).couponType){
            case INTEGRAL_COUPON_TYPE:
                // 积分优惠券
                if (isChecked){
                    // 选中
                    integralSelected(position);
                }else {
                    // 取消
                    integralCancel(position);
                }
                break;
            case ACTIVITY_COUPON_TYPE:
                // 活动优惠券
                if (isChecked){
                    // 选中
                    activitySelected(position);
                }else {
                    // 取消
                    activityCancel(position);
                }
                break;
            case OTHER_COUPON_TYPE:
                // 其他
                if (isChecked){
                    // 选中
                    otherSelected(position);
                }else {
                    // 取消
                    otherCancel(position);
                }
                break;
        }
        // 计算优惠金额
        calculateDiscountAmount();
    }

    /**
      * 其他取消选中处理
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param position
      * @return
      */
    private void otherCancel(int position) {
        if (mData == null)
            return;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null){
                switch (model.couponType){
                    case INTEGRAL_COUPON_TYPE:
                        // 积分优惠券
                        if (!isIntegralCouponSelected()) {
                            if (getConditionAmount() >= mData.get(i).couponRangeValue) {
                                // 可选
                                mData.get(i).isCouponSelected = false;
                                mData.get(i).isCouponAvailable = true;
                            } else {
                                // 不可选
                                mData.get(i).isCouponSelected = false;
                                mData.get(i).isCouponAvailable = false;
                            }
                        }
                        break;
                    case ACTIVITY_COUPON_TYPE:
                        // 活动优惠券
                        if (!isOtherCouponSelected()){
                            // 活动全部变可选
                            mData.get(i).isCouponSelected = false;
                            mData.get(i).isCouponAvailable = true;
                        }
                        break;
                    case OTHER_COUPON_TYPE:
                        // 其他
                        if (mData.get(i).couponRangeGroup
                                .equals(mData.get(position).couponRangeGroup)){
                            // 同类
                            // 其他全部可选
                            if (i != position) {
                                mData.get(i).isCouponSelected = false;
                                mData.get(i).isCouponAvailable = true;
                            }
                        }
                        break;
                }
            }
        }
        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    /**
      * 其他选中处理
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param position
      * @return
      */
    private void otherSelected(int position) {
        if (mData == null)
            return;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null){
                switch (model.couponType){
                    case INTEGRAL_COUPON_TYPE:
                        // 积分优惠券
                        if (getConditionAmount() >= mData.get(i).couponRangeValue){
                            // 可选
                            mData.get(i).isCouponSelected = false;
                            mData.get(i).isCouponAvailable = true;
                        }else {
                            // 不可选
                            mData.get(i).isCouponSelected = false;
                            mData.get(i).isCouponAvailable = false;
                        }
                        break;
                    case ACTIVITY_COUPON_TYPE:
                        // 活动优惠券
                        // 活动全部不可选
                        mData.get(i).isCouponSelected = false;
                        mData.get(i).isCouponAvailable = false;
                        break;
                    case OTHER_COUPON_TYPE:
                        // 其他
                        if (mData.get(i).couponRangeGroup
                                .equals(mData.get(position).couponRangeGroup)){
                            // 同类
                            // 其他全部不可选
                            if (i != position) {
                                mData.get(i).isCouponSelected = false;
                                mData.get(i).isCouponAvailable = false;
                            }
                        }
                        break;
                }
            }
        }
        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    /**
      * 活动优惠券取消选中处理
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param position
      * @return
      */
    private void activityCancel(int position) {
        if (mData == null)
            return;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null){
                switch (model.couponType){
                    case INTEGRAL_COUPON_TYPE:
                        // 积分优惠券
                        if (!isIntegralCouponSelected()) {
                            if (getConditionAmount() >= mData.get(i).couponRangeValue) {
                                // 可选
                                mData.get(i).isCouponSelected = false;
                                mData.get(i).isCouponAvailable = true;
                            } else {
                                // 不可选
                                mData.get(i).isCouponSelected = false;
                                mData.get(i).isCouponAvailable = false;
                            }
                        }
                        break;
                    case ACTIVITY_COUPON_TYPE:
                        // 活动优惠券
                        // 活动全部可选
                        mData.get(i).isCouponSelected = false;
                        mData.get(i).isCouponAvailable = true;
                        break;
                    case OTHER_COUPON_TYPE:
                        // 其他
                        // 其他全部可选
                        mData.get(i).isCouponSelected = false;
                        mData.get(i).isCouponAvailable = true;
                        break;
                }
            }
        }
        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    /**
      * 活动优惠券选中处理
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param position
      * @return
      */
    private void activitySelected(int position) {
        if (mData == null)
            return;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null){
                switch (model.couponType){
                    case INTEGRAL_COUPON_TYPE:
                        // 积分优惠券
                        // 优惠参考金额
                        double conditionAmount = totalAmount - mData.get(position).couponValue;
                        if (conditionAmount >= mData.get(i).couponRangeValue){
                            // 可选
                            mData.get(i).isCouponSelected = false;
                            mData.get(i).isCouponAvailable = true;
                        }else {
                            // 不可选
                            mData.get(i).isCouponSelected = false;
                            mData.get(i).isCouponAvailable = false;
                        }
                        break;
                    case ACTIVITY_COUPON_TYPE:
                        // 活动优惠券
                        // 其他活动不可选
                        if (i != position){
                            mData.get(i).isCouponSelected = false;
                            mData.get(i).isCouponAvailable = false;
                        }
                        break;
                    case OTHER_COUPON_TYPE:
                        // 其他
                        // 其他全部不可选
                        mData.get(i).isCouponSelected = false;
                        mData.get(i).isCouponAvailable = false;
                        break;
                }
            }
        }
        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    /**
      * 积分优惠券取消选中处理
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param position
      * @return
      */
    private void integralCancel(int position) {
        if (mData == null)
            return;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null){
                switch (model.couponType){
                    case INTEGRAL_COUPON_TYPE:
                        // 积分优惠券
                        // 其他积分优惠劵不可用状态
                        if (i != position) {
                            mData.get(i).isCouponSelected = false;
                            mData.get(i).isCouponAvailable = true;
                        }
                        break;
                    case ACTIVITY_COUPON_TYPE:
                        // 活动优惠券
                        if (!isActivityCouponSelected() && !isOtherCouponSelected()){
                            if (getConditionAmount() >= mData.get(i).couponRangeValue){
                                mData.get(i).isCouponAvailable = true;
                            }else {
                                mData.get(i).isCouponAvailable = false;
                            }
                        }
                        break;
                }
            }
        }
        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    /**
      * 积分优惠券选中处理
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param position
      * @return
      */
    private void integralSelected(int position) {
        if (mData == null)
            return;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null){
                switch (model.couponType){
                    case INTEGRAL_COUPON_TYPE:
                        // 积分优惠券
                        // 其他积分优惠劵不可用状态
                        if (i != position) {
                            mData.get(i).isCouponSelected = false;
                            mData.get(i).isCouponAvailable = false;
                        }
                        break;
                    case ACTIVITY_COUPON_TYPE:
                        // 活动优惠券
                        if (!isActivityCouponSelected() && !isOtherCouponSelected()){
                            if (getConditionAmount() >= mData.get(i).couponRangeValue){
                                mData.get(i).isCouponAvailable = true;
                            }else {
                                mData.get(i).isCouponAvailable = false;
                            }
                        }
                        break;
                }
            }
        }
        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    /**
      * 是否有活动优惠券被选中
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param
      * @return
      */
    private boolean isActivityCouponSelected(){
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null
                    && model.couponType == ACTIVITY_COUPON_TYPE
                    && model.isCouponSelected) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有积分优惠券被选中
     * @author leibing
     * @createTime 2017/6/10
     * @lastModify 2017/6/10
     * @param
     * @return
     */
    private boolean isIntegralCouponSelected(){
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null
                    && model.couponType == INTEGRAL_COUPON_TYPE
                    && model.isCouponSelected) {
                return true;
            }
        }
        return false;
    }

    /**
      * 是否有其他优惠券被选中
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param
      * @return
      */
    private boolean isOtherCouponSelected(){
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null
                    && model.couponType == OTHER_COUPON_TYPE
                    && model.isCouponSelected) {
                return true;
            }
        }
        return false;
    }

    /**
      * 获取优惠参考金额
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param
      * @return
      */
    private double getConditionAmount(){
        double conditionAmount = totalAmount;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null && model.isCouponSelected) {
                conditionAmount = conditionAmount - model.couponValue;
            }
        }
        return conditionAmount;
    }

    /**
      * 重置
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param
      * @return
      */
    private void resetData(){
        if (mData == null)
            return;
        for (int i=0;i<mData.size();i++){
            mData.get(i).isCouponAvailable = true;
            mData.get(i).isCouponSelected = false;
        }
        totalDiscountAmount = 0;
        discountAmountTv.setText("优惠劵累计金额：" + (int) totalDiscountAmount);
        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    /**
      * 计算优惠金额
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param
      * @return
      */
    private void calculateDiscountAmount(){
        // 优惠劵金额
        totalDiscountAmount = 0;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null && model.isCouponSelected) {
                totalDiscountAmount+=model.couponValue;
            }
        }
        discountAmountTv.setText("优惠劵累计金额：" + (int) totalDiscountAmount);
    }

    @OnClick({R.id.btn_discount_amount_ok,
            R.id.btn_discount_amount_reset,
            R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                // 返回
                AppManager.getInstance().finishActivity();
                break;
            case R.id.btn_discount_amount_ok:
                // 确定
                Intent intent = new Intent();
                intent.putExtra(PAGE_INTENT_TOTAL_AMOUNT, totalAmount);
                intent.putExtra(PAGE_INTENT_ORIGIN_COUPON_VALUE, originDiscountAmount);
                intent.putExtra(PAGE_INTENT_REAL_COUPON_VALUE, totalDiscountAmount);
                setResult(RESULT_OK, intent);
                AppManager.getInstance().finishActivity();
                break;
            case R.id.btn_discount_amount_reset:
                // 重置
                resetData();
                break;
        }
    }
}

package module.jk.cn.jkshoppingcart.module.orderconfirm.coupon;

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
import module.jk.cn.jkshoppingcart.module.BaseFragmentActivity;
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
    private double totalMoney;

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
        // get data
        getData();
    }

    /**
      * get data
      * @author leibing
      * @createTime 2017/6/10
      * @lastModify 2017/6/10
      * @param
      * @return
      */
    private void getData() {
        totalMoney = 300;

        UseCouponModel model;

        for (int i =0; i<5;i++) {
            model = new UseCouponModel();
            model.couponValue = 50;
            model.couponRangeValue = 200;
            model.couponRangeGroup = "健客自营产品";
            model.couponType = INTEGRAL_COUPON_TYPE;
            model.couponValidDate = "2017.06.10-07.10";
            model.couponSortName = "积分兑换";
            mData.add(model);
        }

        for (int i =0; i<5;i++) {
            model = new UseCouponModel();
            model.couponValue = 100;
            model.couponRangeValue = 300;
            model.couponRangeGroup = "健客自营产品";
            model.couponType = ACTIVITY_COUPON_TYPE;
            model.couponValidDate = "2017.06.10-07.10";
            model.couponSortName = "活动领取";
            mData.add(model);
        }

        for (int i =0; i<5;i++) {
            model = new UseCouponModel();
            model.couponValue = 10;
            model.couponRangeValue = 99;
            model.couponRangeGroup = "结算页测试1";
            model.couponType = OTHER_COUPON_TYPE;
            model.couponValidDate = "2017.06.10-07.10";
            model.couponSortName = "详情页领取";
            mData.add(model);
        }

        for (int i =0; i<2;i++) {
            model = new UseCouponModel();
            model.couponValue = 10;
            model.couponRangeValue = 99;
            model.couponRangeGroup = "结算页测试2";
            model.couponType = OTHER_COUPON_TYPE;
            model.couponValidDate = "2017.06.10-07.10";
            model.couponSortName = "详情页领取";
            mData.add(model);
        }

        if (mAdapter != null){
            mAdapter.setData(mData);
        }
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
                        double conditionAmount = totalMoney - mData.get(position).couponValue;
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
                        if (!isActivityCouponSelected()){
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
                        if (!isActivityCouponSelected()){
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
        double conditionAmount = totalMoney;
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
        discountAmountTv.setText("优惠劵累计金额：" + 0);
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
        double discountAmount = 0;
        for (int i=0;i<mData.size();i++){
            UseCouponModel model = mData.get(i);
            if (model != null && model.isCouponSelected) {
                discountAmount+=model.couponValue;
            }
        }
        discountAmountTv.setText("优惠劵累计金额：" + discountAmount);
    }

    @OnClick({R.id.btn_discount_amount_ok,
            R.id.btn_discount_amount_reset})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_discount_amount_ok:
                // 确定
                break;
            case R.id.btn_discount_amount_reset:
                // 重置
                resetData();
                break;
        }
    }
}

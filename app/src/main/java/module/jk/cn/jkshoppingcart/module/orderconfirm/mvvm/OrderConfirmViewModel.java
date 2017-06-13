package module.jk.cn.jkshoppingcart.module.orderconfirm.mvvm;

import java.util.ArrayList;
import module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmInterface;
import module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel;
import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderConfirmBean;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.ACTIVITY_COUPON_TYPE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.INTEGRAL_COUPON_TYPE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.OTHER_COUPON_TYPE;

/**
 * @className: OrderConfirmViewModel
 * @classDescription: 订单确认逻辑层
 * @author: leibing
 * @createTime: 2017/6/12
 */
public class OrderConfirmViewModel implements OrderConfirmModel.ModelListener {
    // logic process listener
    private ViewModelListener mViewModel;
    // model
    private OrderConfirmModel model;

    /**
      * Constructor
      * @author leibing
      * @createTime 2017/6/12
      * @lastModify 2017/6/12
      * @param mViewModel logic process listener
      * @return
      */
    public OrderConfirmViewModel(ViewModelListener mViewModel){
        this.mViewModel = mViewModel;
        // init model
        model = new OrderConfirmModel(this);
    }

    /**
      * 积分优惠券最优选择处理
      * @author leibing
      * @createTime 2017/6/13
      * @lastModify 2017/6/13
      * @param mUseCouponModelList
      * @param jkGroupTotalAmount 健客自营合计金额
      * @return
      */
    public void couponOptimalDeal(ArrayList<UseCouponModel> mUseCouponModelList,
                                  double jkGroupTotalAmount){
        if (mUseCouponModelList != null
                && mUseCouponModelList.size() != 0){
            int activityPosition = -1;
            int integralPosition = -1;
            int couponSize = mUseCouponModelList.size();
            double referValue = -1;
            // 遍历获取最佳活动或单品优惠券
            for (int i=0;i<couponSize;i++){
                UseCouponModel model = mUseCouponModelList.get(i);
                if (model != null){
                    switch (model.couponType){
                        case ACTIVITY_COUPON_TYPE:
                        case OTHER_COUPON_TYPE:
                            // 活动或单品优惠券
                            // 获取
                            if (model.couponValue >= referValue){
                                referValue = model.couponValue;
                                activityPosition = i;
                            }
                            break;
                    }
                }
            }
            if (referValue != -1){
                double referTotalAmount = jkGroupTotalAmount - referValue;
                referValue = -1;
                // 遍历获取最佳积分优惠券
                for (int j=0;j<couponSize;j++){
                    UseCouponModel model = mUseCouponModelList.get(j);
                    if (model != null){
                        switch (model.couponType){
                            case INTEGRAL_COUPON_TYPE:
                                // 积分优惠券
                                if (referTotalAmount >= model.couponRangeValue
                                        && model.couponValue >= referValue){
                                    referValue = model.couponValue;
                                    integralPosition = j;
                                }
                                break;
                        }
                    }
                }
                if (integralPosition != -1
                        && activityPosition != -1
                        && mViewModel != null){
                    // 回调最优优惠券选择
                    mViewModel.couponOptimal(activityPosition, integralPosition,
                            mUseCouponModelList);
                }
            }
        }
    }

    @Override
    public void updateUI(OrderConfirmBean bean) {
        if (mViewModel != null)
            mViewModel.updateUI(bean);
    }

    @Override
    public void toastShow(String msg) {
        if (mViewModel != null)
            mViewModel.toastShow(msg);
    }

    /**
     * @interfaceName: ViewModelListener
     * @interfaceDescription: logic process listener
     * @author: leibing
     * @createTime: 2017/6/12
     */
    public interface ViewModelListener extends OrderConfirmInterface.DataToUIListener {
        /**
         * 积分优惠券最优选择
         * @param activityPosition 活动或单品最佳选择位置
         * @param integralPosition 积分最佳选择位置
         * @param mUseCouponModelList 优惠券列表
         */
        void couponOptimal(int activityPosition, int integralPosition,
                           ArrayList<UseCouponModel> mUseCouponModelList);
    }
}

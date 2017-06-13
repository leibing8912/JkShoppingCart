package module.jk.cn.jkshoppingcart.module.orderconfirm.coupon;

import java.io.Serializable;

/**
 * @className: UseCouponModel
 * @classDescription: 使用优惠券数据模型
 * @author: leibing
 * @createTime: 2017/6/10
 */
public class UseCouponModel implements Serializable{
    // uid
    private static final long serialVersionUID = -4297744804030210372L;
    // 优惠券类型--积分优惠券
    public final static int INTEGRAL_COUPON_TYPE = 0;
    // 优惠券类型--活动优惠券
    public final static int ACTIVITY_COUPON_TYPE = 1;
    // 优惠券类型--其他
    public final static int OTHER_COUPON_TYPE = 2;
    // 优惠券面值
    public int couponValue;
    // 优惠券金额适用范围
    public int couponRangeValue;
    // 优惠券类型名称
    public String couponSortName;
    // 优惠券类型(0：积分优惠券；1：活动优惠券；2：其他)
    public int couponType = INTEGRAL_COUPON_TYPE;
    // 优惠券分组适用范围
    public String couponRangeGroup;
    // 优惠券使用有效期至
    public String couponValidDate;
    // 优惠券是否可用
    public boolean isCouponAvailable = true;
    // 优惠券是否选中
    public boolean isCouponSelected = false;
}

package module.jk.cn.jkshoppingcart.module.orderconfirm.mvvm;

import android.os.Handler;
import java.util.ArrayList;
import module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmInterface;
import module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel;
import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderConfirmBean;
import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderInfoModel;
import module.jk.cn.jkshoppingcart.module.orderconfirm.redenvelope.UseRedEnvelopeModel;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAY_ONLINE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.ACTIVITY_COUPON_TYPE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.INTEGRAL_COUPON_TYPE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.coupon.UseCouponModel.OTHER_COUPON_TYPE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderInfoModel.JIANKE_SELF_SUPPORT;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_GROUP;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_SKU;

/**
 * @className: OrderConfirmBean
 * @classDescription: 订单确认（数据层）
 * @author: leibing
 * @createTime: 2017/6/12
 */
public class OrderConfirmModel {
    // model listener
    private ModelListener modelListener;
    // 数据源
    private OrderConfirmBean bean;

    /**
      * Constructor
      * @author leibing
      * @createTime 2017/6/12
      * @lastModify 2017/6/12
      * @param modelListener
      * @return
      */
    public OrderConfirmModel(ModelListener modelListener){
        this.modelListener = modelListener;
        // get data
        getData();
    }

    /**
      * get data
      * @author leibing
      * @createTime 2017/6/12
      * @lastModify 2017/6/12
      * @param
      * @return
      */
    private void getData() {
        bean  = new OrderConfirmBean();
        // 地址
        OrderConfirmBean.ReceiveAddress receiveAddress = new OrderConfirmBean.ReceiveAddress();
        receiveAddress.isDefaultAddress = true;
        receiveAddress.addressId = "1001";
        receiveAddress.contactPhone = "18818881898";
        receiveAddress.detailAddress = "广州市萝岗区科学大道99号科汇三街1号";
        receiveAddress.receiveName = "周先生";
        bean.receiveAddress = receiveAddress;
        // 支付方式
        bean.payMode = PAY_ONLINE;
        // 订单信息列表
        ArrayList<OrderInfoModel> productInfoList = new ArrayList<>();

        OrderInfoModel model = new OrderInfoModel();
        model.sellerName = "健客自营";
        model.sellerId = "1001";
        model.sellerType = JIANKE_SELF_SUPPORT;
        // 优惠券列表
        ArrayList<UseCouponModel> mUseCouponModelList = new ArrayList<>();
        UseCouponModel couponModel = new UseCouponModel();
        couponModel.couponValue = 5;
        couponModel.couponRangeValue = 100;
        couponModel.couponSortName = "积分兑换";
        couponModel.couponType = INTEGRAL_COUPON_TYPE;
        couponModel.couponRangeGroup = "健客自营";
        couponModel.couponValidDate = "2017.06.12-07.10";
        mUseCouponModelList.add(couponModel);

        couponModel = new UseCouponModel();
        couponModel.couponValue = 10;
        couponModel.couponRangeValue = 100;
        couponModel.couponSortName = "积分兑换";
        couponModel.couponType = INTEGRAL_COUPON_TYPE;
        couponModel.couponRangeGroup = "健客自营";
        couponModel.couponValidDate = "2017.06.12-07.10";
        mUseCouponModelList.add(couponModel);

        couponModel = new UseCouponModel();
        couponModel.couponValue = 15;
        couponModel.couponRangeValue = 100;
        couponModel.couponSortName = "积分兑换";
        couponModel.couponType = INTEGRAL_COUPON_TYPE;
        couponModel.couponRangeGroup = "健客自营";
        couponModel.couponValidDate = "2017.06.12-07.10";
        mUseCouponModelList.add(couponModel);

        couponModel = new UseCouponModel();
        couponModel.couponValue = 10;
        couponModel.couponRangeValue = 100;
        couponModel.couponSortName = "活动领取";
        couponModel.couponType = ACTIVITY_COUPON_TYPE;
        couponModel.couponRangeGroup = "健客自营";
        couponModel.couponValidDate = "2017.06.12-07.10";
        mUseCouponModelList.add(couponModel);

        couponModel = new UseCouponModel();
        couponModel.couponValue = 15;
        couponModel.couponRangeValue = 100;
        couponModel.couponSortName = "单品测试1";
        couponModel.couponType = OTHER_COUPON_TYPE;
        couponModel.couponRangeGroup = "单品范围1";
        couponModel.couponValidDate = "2017.06.12-07.10";
        mUseCouponModelList.add(couponModel);

        couponModel = new UseCouponModel();
        couponModel.couponValue = 20;
        couponModel.couponRangeValue = 100;
        couponModel.couponSortName = "单品测试2";
        couponModel.couponType = OTHER_COUPON_TYPE;
        couponModel.couponRangeGroup = "单品范围2";
        couponModel.couponValidDate = "2017.06.12-07.10";
        mUseCouponModelList.add(couponModel);

        model.mUseCouponModelList = mUseCouponModelList;

        // 红包列表
        ArrayList<UseRedEnvelopeModel> mUseRedEnvelopeModelList = new ArrayList<>();
        UseRedEnvelopeModel mUseRedEnvelopeModel = new UseRedEnvelopeModel();
        mUseRedEnvelopeModel.redEnvelopeValue = 5;
        mUseRedEnvelopeModel.redEnvelopeValidDate = "5天内有效";
        mUseRedEnvelopeModelList.add(mUseRedEnvelopeModel);

        mUseRedEnvelopeModel = new UseRedEnvelopeModel();
        mUseRedEnvelopeModel.redEnvelopeValue = 10;
        mUseRedEnvelopeModel.redEnvelopeValidDate = "10天内有效";
        mUseRedEnvelopeModelList.add(mUseRedEnvelopeModel);

        mUseRedEnvelopeModel = new UseRedEnvelopeModel();
        mUseRedEnvelopeModel.redEnvelopeValue = 20;
        mUseRedEnvelopeModel.redEnvelopeValidDate = "3天内有效";
        mUseRedEnvelopeModelList.add(mUseRedEnvelopeModel);

        model.mUseRedEnvelopeModelList = mUseRedEnvelopeModelList;

        // 产品信息
        ArrayList<OrderInfoModel.Product> products = new ArrayList<>();
        // 单品
        OrderInfoModel.Product product = new OrderInfoModel.Product();
        product.productType = PRODUCT_TYPE_SKU;
        product.productId = "110";
        OrderInfoModel.Product.SkuProduct skuProduct = new OrderInfoModel.Product.SkuProduct();
        skuProduct.imgUrl = "";
        skuProduct.price = 25.5;
        skuProduct.productAmount = 5;
        skuProduct.productQualification = "10包*15ml";
        skuProduct.productName = "测试单品";
        ArrayList<OrderInfoModel.Product.SkuProduct.Gifts> gifts = new ArrayList<>();
        OrderInfoModel.Product.SkuProduct.Gifts gift = new OrderInfoModel.Product.SkuProduct.Gifts();
        gift.giftAmount = 1;
        gift.giftName = "测试赠品1";
        gifts.add(gift);

        gift = new OrderInfoModel.Product.SkuProduct.Gifts();
        gift.giftAmount = 2;
        gift.giftName = "测试赠品2";
        gifts.add(gift);
        skuProduct.gifts = gifts;
        product.skuProduct = skuProduct;
        products.add(product);
        // 组合
        product = new OrderInfoModel.Product();
        product.productType = PRODUCT_TYPE_GROUP;
        product.productId = "111";
        OrderInfoModel.Product.GroupProduct groupProduct = new OrderInfoModel.Product.GroupProduct();
        groupProduct.groupAmount = 1;
        groupProduct.groupPrice = 8;
        groupProduct.imgUrl = "";
        groupProduct.productName = "测试组合";
        ArrayList<OrderInfoModel.Product.GroupProduct.ChildProduct> childList = new ArrayList<>();
        OrderInfoModel.Product.GroupProduct.ChildProduct childProduct
                = new OrderInfoModel.Product.GroupProduct.ChildProduct();
        childProduct.productAmount = 1;
        childProduct.price = 3;
        childProduct.productName = "测试组合子商品1";
        childProduct.productQualification = "10包*5ml";
        childList.add(childProduct);

        childProduct
                = new OrderInfoModel.Product.GroupProduct.ChildProduct();
        childProduct.productAmount = 2;
        childProduct.price = 2.5;
        childProduct.productName = "测试组合子商品2";
        childProduct.productQualification = "10包*5ml";
        childList.add(childProduct);
        groupProduct.childList = childList;
        product.groupProduct = groupProduct;
        products.add(product);
        // 奖品
        product = new OrderInfoModel.Product();
        product.productType = PRODUCT_TYPE_AWARD;
        product.productId = "112";
        OrderInfoModel.Product.AwardProduct awardProduct
                = new OrderInfoModel.Product.AwardProduct();
        awardProduct.awardType = AWARD_TYPE_AWARD;
        awardProduct.awardAmount = 1;
        awardProduct.awardPrice = 0;
        awardProduct.awardName = "奖品1";
        awardProduct.awardQualification = "10瓶*10包";
        product.awardProduct = awardProduct;
        products.add(product);

        new OrderInfoModel.Product();
        product.productType = PRODUCT_TYPE_AWARD;
        product.productId = "113";
        awardProduct
                = new OrderInfoModel.Product.AwardProduct();
        awardProduct.awardType = AWARD_TYPE_AWARD;
        awardProduct.awardAmount = 2;
        awardProduct.awardPrice = 0;
        awardProduct.awardName = "奖品2";
        awardProduct.awardQualification = "10瓶*10包";
        product.awardProduct = awardProduct;
        products.add(product);

        model.product = products;

        productInfoList.add(model);

        bean.productInfoList = productInfoList;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (modelListener != null)
                    modelListener.updateUI(bean);
            }
        }, 100);
    }

    /**
     * @interfaceName: ModelListener
     * @interfaceDescription: model listener
     * @author: leibing
     * @createTime: 2017/6/12
     */
    public interface ModelListener extends OrderConfirmInterface.DataToUIListener{
    }
}

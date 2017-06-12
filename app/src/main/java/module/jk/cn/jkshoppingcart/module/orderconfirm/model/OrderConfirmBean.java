package module.jk.cn.jkshoppingcart.module.orderconfirm.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @className: OrderConfirmBean
 * @classDescription: 订单确认数据模型
 * @author: leibing
 * @createTime: 2017/6/12
 */
public class OrderConfirmBean implements Serializable{
    // uid
    private static final long serialVersionUID = 5060534017826948094L;
    // 收货地址
    public ReceiveAddress receiveAddress;

    /**
     * @className: ReceiveAddress
     * @classDescription: 收货地址
     * @author: leibing
     * @createTime: 2017/6/12
     */
    public static class ReceiveAddress implements Serializable{
        // uid
        private static final long serialVersionUID = -6866093944737612224L;
        // 地址id
        public String addressId;
        // 收货人
        public String receiveName;
        // 联系电话
        public String contactPhone;
        // 是否默认地址
        public boolean isDefaultAddress = false;
        // 详细地址
        public String detailAddress;
    }
    // 进口商品需要填入的身份证号
    public String identityCard;
    // 支付方式（在线支付、货到付款）
    public String payMode = "在线支付";
    // 订单信息列表
    public ArrayList<OrderInfoModel> productInfoList;
}

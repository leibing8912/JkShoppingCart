package module.jk.cn.jkshoppingcart.module.orderconfirm.mvvm;

import java.util.ArrayList;

import module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmInterface;
import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderConfirmBean;
import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderInfoModel;

import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAY_ONLINE;

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

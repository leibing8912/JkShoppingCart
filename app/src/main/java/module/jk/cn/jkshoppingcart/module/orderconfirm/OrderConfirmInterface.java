package module.jk.cn.jkshoppingcart.module.orderconfirm;

import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderConfirmBean;

/**
 * @className: OrderConfirmInterface
 * @classDescription: 订单确认接口回调
 * @author: leibing
 * @createTime: 2017/6/12
 */
public class OrderConfirmInterface {

    /**
     * @interfaceName:UIToDataInterface
     * @interfaceDescription: ui层往数据层操作接口
     * @author: leibing
     * @createTime: 2017/6/12
     */
    public interface UIToDataInterface{
    }

    /**
     * @interfaceName: DataToUIListener
     * @interfaceDescription: 数据层往ui层回调监听
     * @author: leibing
     * @createTime: 2017/6/12
     */
    public interface DataToUIListener{
        // 更新ui数据
        void updateUI(OrderConfirmBean bean);
        // toast显示
        void toastShow(String msg);
    }   
}

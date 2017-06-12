package module.jk.cn.jkshoppingcart.module.orderconfirm.mvvm;

import module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmInterface;

/**
 * @className: OrderConfirmBean
 * @classDescription: 订单确认（数据层）
 * @author: leibing
 * @createTime: 2017/6/12
 */
public class OrderConfirmModel {
    // model listener
    private ModelListener modelListener;

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

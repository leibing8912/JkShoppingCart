package module.jk.cn.jkshoppingcart.module.orderconfirm.mvvm;

import module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmInterface;
import module.jk.cn.jkshoppingcart.module.orderconfirm.model.OrderConfirmBean;

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

    }
}

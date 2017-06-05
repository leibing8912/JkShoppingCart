package module.jk.cn.jkshoppingcart.module.shoppingcart;

/**
 * @className: ShoppingCartInterface
 * @classDescription: 购物车回调接口
 * @author: leibing
 * @createTime: 2017/6/5
 */
public class ShoppingCartInterface {
    
    /**
     * @interfaceName: CheckInterface
     * @interfaceDescription: 复选框接口
     * @author: leibing
     * @createTime: 2017/6/5
     */
    public interface CheckInterface{
        /**
         * 组选框状态改变触发的事件
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * @interfaceName:UIToDataInterface
     * @interfaceDescription: ui层往数据层操作接口
     * @author: leibing
     * @createTime: 2017/6/5
     */
    public interface UIToDataInterface{
        // 保存编辑前数据
        void saveBeforeEditData(Object object);
        // 获取编辑前数据
        void getBeforeEditData();
    }

    /**
     * @interfaceName: DataToUIListener
     * @interfaceDescription: 数据层往ui层回调监听
     * @author: leibing
     * @createTime: 2017/6/5
     */
    public interface DataToUIListener{
        // 读取编辑前数据
        void readBeforeEditData(Object object);
        // 保存编辑前数据成功
        void saveBeforeEditDataSuccess();
    }
}

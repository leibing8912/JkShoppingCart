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


}

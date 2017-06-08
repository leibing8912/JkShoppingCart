package module.jk.cn.jkshoppingcart.module.shoppingcart;

import android.view.View;

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
     * @interfaceName: ModifyCountInterface
     * @interfaceDescription: 改变数量的接口
     * @author: leibing
     * @createTime: 2017/6/8
     */
    public interface ModifyCountInterface {

        /**
         * 增加操作
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 编辑数量
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doEditNum(int groupPosition, int childPosition, View showCountView,boolean isChecked);
    }

    /**
     * @interfaceName: DeleteInterface
     * @interfaceDescription: 删除的接口
     * @author: leibing
     * @createTime: 2017/6/8
     */
    public interface DeleteInterface{
        /**
         * 清空失效商品
         * @param groupPosition 组元素位置
         */
        void doClearInvalid(int groupPosition);
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

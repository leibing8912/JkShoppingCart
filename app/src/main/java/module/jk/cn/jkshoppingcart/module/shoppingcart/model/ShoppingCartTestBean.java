package module.jk.cn.jkshoppingcart.module.shoppingcart.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @className: ShoppingCartTestBean
 * @classDescription: 测试数据模型
 * @author: leibing
 * @createTime: 2017/6/5
 */
public class ShoppingCartTestBean implements Serializable{
    // uid
    private static final long serialVersionUID = 7835084188888087406L;
    // 卖家名称
    public String sellerName = "";
    // 该卖家下产品是否全部选中
    public boolean isSelected = false;
    // 产品
    public ArrayList<Product> product;

    /**
     * @className: Product
     * @classDescription: 产品
     * @author: leibing
     * @createTime: 2017/6/2
     */
    public static class Product implements Serializable{
        // uid
        private static final long serialVersionUID = 7897435387291649986L;
        // 产品名
        public String productName = "";
        // 产品价格
        public double price = -1;
        // 产品数量
        public int count = 0;
        // 产品是否选中
        public boolean isSelected = false;
    }
}

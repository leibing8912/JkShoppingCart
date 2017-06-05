package module.jk.cn.jkshoppingcart.module.shoppingcart;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @className: ShoppingCartBean
 * @classDescription: 
 * @author: leibing
 * @createTime: 2017/6/2
 */
public class ShoppingCartBean implements Serializable{
    // uid
    private static final long serialVersionUID = -8427119425747273036L;
    // 卖家名称
    public String sellerName = "";
    // 包邮提示(目前是自营产品才有)
    public String freeShippingTips = "";
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
    public static class Product{
        // 产品类型--单品
        public final static int PRODUCT_TYPE_SKU = 0x10;
        // 产品类型--组合
        public final static int PRODUCT_TYPE_GROUP = 0x11;
        // 产品组合类型（单品、组合产品，默认类型为单品）
        public int productType = PRODUCT_TYPE_SKU;
        // 单品
        public SkuProduct skuProduct;

        /**
         * @className: SkuProduct
         * @classDescription: 单品
         * @author: leibing
         * @createTime: 2017/6/2
         */
        public static class SkuProduct{
            // 产品id
            public String productId = "";
            // 产品图片地址
            public String imgUrl = "";
            // 产品名称
            public String productName = "";
            // 产品原始价格
            public double originPrice;
            // 产品实际价格
            public double realPrice;
            // 产品数量
            public int productAmount;
            // 产品规格
            public String productQualification = "";
            // 产品提示(奖品、积分兑换等需提示)
            public String productTips;
            // 赠品
            public Gifts gifts;

            /**
             * @className: Gifts
             * @classDescription: 赠品
             * @author: leibing
             * @createTime: 2017/6/2
             */
            public static class Gifts{
                // 赠品名称
                public String giftName = "";
                // 赠品数量
                public int giftAmount;
            }
        }

        // 组合产品
        public GroupProduct groupProduct;

        /**
         * @className: GroupProduct
         * @classDescription: 组合产品
         * @author: leibing
         * @createTime: 2017/6/2
         */
        public static class GroupProduct{
            // 组合产品id
            public String productId = "";
            // 组合产品名称
            public String productName = "";
            // 组合产品价格
            public double groupPrice;
            // 组合产品数量
            public int groupAmount;
            // 子产品数组
            public ArrayList<ChildProduct> childList;

            /**
             * @className: ChildProduct
             * @classDescription: 子产品
             * @author: leibing
             * @createTime: 2017/6/2
             */
            public static class ChildProduct{
                // 子产品图片地址
                public String imgUrl = "";
                // 子产品名称
                public String productName = "";
                // 子产品价格
                public double price;
                // 子产品数量
                public int productAmount;
                // 子产品规格
                public String productQualification = "";
            }
        }
    }
}

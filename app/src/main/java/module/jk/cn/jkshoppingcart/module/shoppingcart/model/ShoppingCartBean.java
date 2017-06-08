package module.jk.cn.jkshoppingcart.module.shoppingcart.model;

import java.io.Serializable;
import java.util.ArrayList;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_SKU;

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
    // 是否失效商品
    public boolean isInvalidGoods = false;
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
        private static final long serialVersionUID = -7557891821348413638L;
        // 产品组合类型（单品、组合产品、奖品，默认类型为单品）
        public int productType = PRODUCT_TYPE_SKU;
        // 产品是否选中
        public boolean isSelected = false;
        // 产品是否编辑状态
        public boolean isEdit = false;
        // 产品id
        public String productId = "";
        // 单品
        public SkuProduct skuProduct;

        /**
         * @className: SkuProduct
         * @classDescription: 单品
         * @author: leibing
         * @createTime: 2017/6/2
         */
        public static class SkuProduct implements Serializable{
            // uid
            private static final long serialVersionUID = 3403986876262729285L;
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
            // 赠品
            public ArrayList<Gifts> gifts;

            /**
             * @className: Gifts
             * @classDescription: 赠品
             * @author: leibing
             * @createTime: 2017/6/2
             */
            public static class Gifts implements Serializable{
                // uid
                private static final long serialVersionUID = 7325335574969442576L;
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
        public static class GroupProduct implements Serializable{
            // uid
            private static final long serialVersionUID = -2128058434540954370L;
            // 组合产品名称
            public String productName = "";
            // 组合产品图片url
            public String imgUrl = "";
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
            public static class ChildProduct implements Serializable{
                // uid
                private static final long serialVersionUID = -5366609880665798603L;
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

        // 奖品
        public AwardProduct awardProduct;

        /**
         * @className: AwardProduct
         * @classDescription: 奖品
         * @author: leibing
         * @createTime: 2017/6/7
         */
        public static class AwardProduct implements Serializable{
            // uid
            private static final long serialVersionUID = -6058625283973914720L;
            // 奖品图片地址
            public String imgUrl = "";
            // 奖品名称
            public String awardName = "";
            // 奖品原始价格
            public double originPrice;
            // 奖品实际价格
            public double realPrice;
            // 奖品数量
            public int awardAmount;
            // 奖品规格
            public String awardQualification = "";
            // 奖品类型(奖品、积分兑换，默认类型为奖品)
            public int awardType = AWARD_TYPE_AWARD;
        }
    }
}

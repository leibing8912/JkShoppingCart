package module.jk.cn.jkshoppingcart.module.shoppingcart;

/**
 * @className: ShoppingCartConstant
 * @classDescription: 购物车常量类
 * @author: leibing
 * @createTime: 2017/6/2
 */
public class ShoppingCartConstant {
    // 编辑
    public final static String EDIT_TXT = "编辑";
    // 完成
    public final static String COMPLETE_TXT = "完成";
    // 购物车
    public final static String SHOPPINGCART = "购物车";
    // 合计金额前缀符号
    public final static String TOTAL_PRICE_PREX = "合计：￥";
    // 确定
    public final static String OK = "确定";
    // 取消
    public final static String CANCEL = "取消";
    // 收藏成功
    public final static String COLLECT_SUCCESS = "收藏成功";
    // 兑换商品和奖品不可删除哦~
    public final static String AWARD_CANNOT_DELETE = "兑换商品和奖品不可删除哦~";
    // 您还没有选择商品哦~
    public final static String NOT_SELECT_GOODS = " 您还没有选择商品哦~";
    // 兑换商品和奖品不可移入收藏哦~
    public final static String AWARD_CANNOT_COLLECT = "兑换商品和奖品不可移入收藏哦~";
    // 产品类型--单品正常
    public final static int PRODUCT_TYPE_SKU = 0x10;
    // 产品类型--组合正常
    public final static int PRODUCT_TYPE_GROUP = 0x11;
    // 产品类型--奖品
    public final static int PRODUCT_TYPE_AWARD = 0x12;
    // 产品类型--单品失效
    public final static int PRODUCT_TYPE_SKU_INVALID = 0x13;
    // 产品类型--组合失效
    public final static int PRODUCT_TYPE_GROUP_INVALID = 0x14;
    // 产品类型--奖品失效
    public final static int PRODUCT_TYPE_AWARD_INVALID = 0x15;
    // 奖品类型--奖品
    public final static int AWARD_TYPE_AWARD = 0x20;
    // 奖品类型--积分兑换
    public final static int AWARD_CREDITS_EXCHANGE = 0x21;
}

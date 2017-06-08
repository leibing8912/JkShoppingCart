package module.jk.cn.jkshoppingcart.module.shoppingcart.mvvm;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import module.jk.cn.jkshoppingcart.common.StringUtil;
import module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartInterface;
import module.jk.cn.jkshoppingcart.module.shoppingcart.model.ShoppingCartBean;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_CANNOT_COLLECT;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_CANNOT_DELETE;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.NOT_SELECT_GOODS;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_GROUP;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_SKU;

/**
 * @className: ShoppingCartViewModel
 * @classDescription: 购物车（逻辑层）
 * @author: leibing
 * @createTime: 2017/6/5
 */
public class ShoppingCartViewModel implements ShoppingCartInterface.UIToDataInterface {
    // activity weak refer
    private WeakReference<Context> mContextWeakRef;
    // shoppingcart logical processing listener
    private ViewModelListener mViewModelListener;
    // shoppingcart data instance
    private ShoppingCartModel mShoppingCartModel;
    // shoppingcart data listener
    private ShoppingCartModel.ModelListener mModelListener
            = new ShoppingCartModel.ModelListener() {

        @Override
        public void readBeforeEditData(Object object) {
            if (mViewModelListener != null)
                mViewModelListener.readBeforeEditData(object);
        }

        @Override
        public void saveBeforeEditDataSuccess() {
            if (mViewModelListener != null)
                mViewModelListener.saveBeforeEditDataSuccess();
        }

        @Override
        public void toastShow(String msg) {
            if (mViewModelListener != null)
                mViewModelListener.toastShow(msg);
        }
    };

    /**
     * Constructor
     * @author leibing
     * @createTime 2017/5/4
     * @lastModify 2017/5/4
     * @param mContext activity refer
     * @param mViewModelListener shoppingcart logical processing listener
     * @return
     */
    public ShoppingCartViewModel(Context mContext, ViewModelListener mViewModelListener){
        // init activity weak refer instance
        this.mContextWeakRef = new WeakReference<Context>(mContext);
        // init shopingcart logical processing listener
        this.mViewModelListener = mViewModelListener;
        // init shoppingcart data instance
        mShoppingCartModel = new ShoppingCartModel(mContext, mModelListener);
    }

    @Override
    public void saveBeforeEditData(Object object) {
        if (mShoppingCartModel != null)
            mShoppingCartModel.saveBeforeEditData(object);
    }

    @Override
    public void getBeforeEditData() {
        if (mShoppingCartModel != null)
            mShoppingCartModel.getBeforeEditData();
    }

    /**
      * 删除单个item
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param mData
      * @param groupPosition
      * @param childPosition
      * @return
      */
    public void delSingleItem(ArrayList<ShoppingCartBean> mData,
                              int groupPosition, int childPosition){
        mData.get(groupPosition).product.remove(childPosition);
        // 遍历删除无数据组
        for (int i=0;i<mData.size();i++){
            if (mData.get(i).product == null
                    || mData.get(i).product.size() == 0){
                mData.remove(i);
                break;
            }
        }
        // 回调更新Ui
        if (mViewModelListener != null)
            mViewModelListener.setData(mData);
    }

    /**
      * 收藏单个产品
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param mData
      * @param groupPosition
      * @param childPosition
      * @return
      */
    public void collectSingProduct(ArrayList<ShoppingCartBean> mData,
                                int groupPosition, int childPosition){
        if (mData.get(groupPosition).product.get(childPosition).productType
                == PRODUCT_TYPE_AWARD){
            if (mModelListener != null)
                mModelListener.toastShow(AWARD_CANNOT_COLLECT);
            return;
        }
        // 产品id
        String productId = mData.get(groupPosition).product.get(childPosition).productId;
        // 收藏
        if (mShoppingCartModel != null)
            mShoppingCartModel.collectProduct(productId);
    }

    /**
      * 收藏选中商品
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param mData
      * @return
      */
    public void collectSeletedProduct(ArrayList<ShoppingCartBean> mData){
        if (mData == null || mData.size() == 0){
            if (mModelListener != null)
                mModelListener.toastShow(NOT_SELECT_GOODS);
            return;
        }
        for (int i=0;i<mData.size();i++){
            if (mData.get(i).product != null) {
                boolean isHasSelected = false;
                String productIds = "";
                // 遍历添加需要删除的数据
                for (int j = 0; j < mData.get(i).product.size(); j++) {
                    ShoppingCartBean.Product product = mData.get(i).product.get(j);
                    if (product != null && product.isSelected){
                        isHasSelected = true;
                        switch (product.productType){
                            case PRODUCT_TYPE_SKU:
                            case PRODUCT_TYPE_GROUP:
                                // 单品正常
                                // 组合正常
                                productIds += product.productId + ",";
                                break;
                            case PRODUCT_TYPE_AWARD:
                                // 奖品
                                if (mModelListener != null)
                                    mModelListener.toastShow(AWARD_CANNOT_DELETE);
                                return;
                            default:
                                break;
                        }
                    }
                }
                // 没有选中商品
                if (!isHasSelected && mModelListener != null){
                    mModelListener.toastShow(NOT_SELECT_GOODS);
                    return;
                }
                // 收藏处理
                if (StringUtil.isNotEmpty(productIds)){
                    if (mShoppingCartModel != null)
                        mShoppingCartModel.collectProduct(productIds);
                }
            }
        }
    }

    /**
      * 删除选中商品
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param
      * @return
      */
    public void delSeletedProduct(ArrayList<ShoppingCartBean> mData){
        if (mData == null || mData.size() == 0){
            if (mModelListener != null)
                mModelListener.toastShow(NOT_SELECT_GOODS);
            return;
        }
        ArrayList<ShoppingCartBean.Product> needDelList = new ArrayList<>();
        for (int i=0;i<mData.size();i++){
            if (mData.get(i).product != null) {
                boolean isHasSelected = false;
                // 遍历添加需要删除的数据
                for (int j = 0; j < mData.get(i).product.size(); j++) {
                    ShoppingCartBean.Product product = mData.get(i).product.get(j);
                    if (product != null && product.isSelected){
                        isHasSelected = true;
                        switch (product.productType){
                            case PRODUCT_TYPE_SKU:
                            case PRODUCT_TYPE_GROUP:
                                // 单品正常
                                // 组合正常
                                needDelList.add(product);
                                break;
                            case PRODUCT_TYPE_AWARD:
                                // 奖品
                                if (mModelListener != null)
                                    mModelListener.toastShow(AWARD_CANNOT_DELETE);
                                return;
                            default:
                                break;
                        }
                    }
                }
                // 没有选中商品
                if (!isHasSelected && mModelListener != null){
                    mModelListener.toastShow(NOT_SELECT_GOODS);
                    return;
                }
                // 遍历删除数据
                for (int z=0;z< needDelList.size();z++){
                    mData.get(i).product.remove(needDelList.get(z));
                }
            }
        }
        // 更新编辑前缓存数据
        if (mShoppingCartModel != null)
            mShoppingCartModel.updateBeforeEditData(needDelList);
        // 遍历删除无数据组
        for (int i=0;i<mData.size();i++){
            if (mData.get(i).product == null
                    || mData.get(i).product.size() == 0){
                mData.remove(i);
                break;
            }
        }
        // 回调更新Ui
        if (mViewModelListener != null)
            mViewModelListener.setData(mData);
    }

    /**
      * 清空失效产品
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param mData
      * @param groupPosition
      * @return
      */
    public void clearInvalidGoods(ArrayList<ShoppingCartBean> mData,
                                  int groupPosition){
        mData.remove(groupPosition);
        if (mViewModelListener != null)
            mViewModelListener.setData(mData);
    }

    /**
     * @interfaceName: ViewModelListener
     * @interfaceDescription: shoppingcart logical processing listener
     * @author: leibing
     * @createTime: 2017/6/5
     */
    public interface ViewModelListener extends ShoppingCartInterface.DataToUIListener {
        // set data
        void setData(ArrayList<ShoppingCartBean> mData);
    }
}

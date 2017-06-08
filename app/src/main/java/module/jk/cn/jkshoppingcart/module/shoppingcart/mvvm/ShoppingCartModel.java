package module.jk.cn.jkshoppingcart.module.shoppingcart.mvvm;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import module.jk.cn.jkshoppingcart.cache.ListCache;
import module.jk.cn.jkshoppingcart.cache.SpLocalCache;
import module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartInterface;
import module.jk.cn.jkshoppingcart.module.shoppingcart.model.ShoppingCartBean;
import module.jk.cn.jkshoppingcart.module.shoppingcart.model.ShoppingCartTestBean;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.COLLECT_SUCCESS;

/**
 * @className: ShoppingCartModel
 * @classDescription: 购物车（数据层）
 * @author: leibing
 * @createTime: 2017/6/5
 */
public class ShoppingCartModel implements ShoppingCartInterface.UIToDataInterface {
    // activity weak refer
    private WeakReference<Context> mContextWeakRef;
    // shoppingcart data listener
    private ModelListener mModelListener;
    // shoppingcart cache
    private SpLocalCache<ListCache> lSpLocalCache;
    private ListCache<ShoppingCartTestBean> mListCache;

    /**
     * Constructor
     * @author leibing
     * @createTime 2017/5/4
     * @lastModify 2017/5/4
     * @param mContext activity refer
     * @param mModelListener shoppingcart data listener
     * @return
     */
    public ShoppingCartModel(Context mContext, ModelListener mModelListener){
        this.mContextWeakRef = new WeakReference<Context>(mContext);
        this.mModelListener = mModelListener;
        lSpLocalCache = new SpLocalCache<>(ListCache.class, ShoppingCartTestBean.class);
        mListCache = new ListCache<>();
    }

    @Override
    public void saveBeforeEditData(Object object) {
        if (object != null){
            final ArrayList<ShoppingCartTestBean> needSavaList = (ArrayList) object;
            mListCache.setObjList(needSavaList);
            lSpLocalCache.save(mContextWeakRef.get().getApplicationContext(), mListCache, new SpLocalCache.CacheCallBackListener() {
                @Override
                public void saveCacheComplete() {
                    if (mModelListener != null)
                        mModelListener.saveBeforeEditDataSuccess();
                }
            });
        }
    }

    @Override
    public void getBeforeEditData() {
        if (lSpLocalCache == null || mModelListener == null)
            return;
        lSpLocalCache.read(mContextWeakRef.get().getApplicationContext(),
                new SpLocalCache.LocalCacheCallBack() {
            @Override
            public void readCacheComplete(Object obj) {
                if (obj != null){
                    mModelListener.readBeforeEditData(obj);
                }
            }
        });
    }

    /**
      * 更新编辑前数据
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param needDelList
      * @return
      */
    public void updateBeforeEditData(final ArrayList<ShoppingCartBean.Product> needDelList){
        if (lSpLocalCache != null){
            lSpLocalCache.read(mContextWeakRef.get().getApplicationContext(),
                    new SpLocalCache.LocalCacheCallBack() {
                        @Override
                        public void readCacheComplete(Object obj) {
                            if (obj != null) {
                                ListCache<ShoppingCartBean> mReadCache
                                        = (ListCache<ShoppingCartBean>) obj;
                                ArrayList<ShoppingCartBean> mData = mReadCache.getObjList();
                                if (mData != null && mData.size() != 0){
                                    // 删除列表
                                    ArrayList<ShoppingCartBean.Product> delList
                                            = new ArrayList<ShoppingCartBean.Product>();
                                    for (int i=0;i<mData.size();i++){
                                        // 遍历添加需要删除的数据
                                        for (int j = 0; j < mData.get(i).product.size(); j++) {
                                            ShoppingCartBean.Product product = mData.get(i).product.get(j);
                                            for (int z=0;z< needDelList.size();z++){
                                                ShoppingCartBean.Product delProduct
                                                        = needDelList.get(z);
                                                if (product.productId.equals(delProduct.productId)){
                                                    delList.add(product);
                                                }
                                            }
                                        }
                                        // 遍历删除数据
                                        for (int p=0;p< delList.size();p++){
                                            if (mData.get(i) != null
                                                    && mData.get(i).product != null) {
                                                mData.get(i).product.remove(delList.get(p));
                                            }
                                        }
                                    }
                                    // 遍历删除无数据组
                                    for (int i=0;i<mData.size();i++){
                                        if (mData.get(i).product == null
                                                || mData.get(i).product.size() == 0){
                                            mData.remove(i);
                                            break;
                                        }
                                    }
                                    // 保存缓存数据
                                    mReadCache.setObjList(mData);
                                    lSpLocalCache.save(mContextWeakRef.get().getApplicationContext(),
                                            mReadCache);
                                }
                            }
                        }
                    });
        }
    }

    /**
      * 收藏产品
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param productIds 产品id
      * @return
      */
    public void collectProduct(String productIds){
        if (mModelListener != null)
            mModelListener.toastShow(COLLECT_SUCCESS);
    }

    /**
     * @interfaceName: ModelListener
     * @interfaceDescription: shoppingcart data listener
     * @author: leibing
     * @createTime: 2017/6/5
     */
    public interface ModelListener extends ShoppingCartInterface.DataToUIListener {
    }
}

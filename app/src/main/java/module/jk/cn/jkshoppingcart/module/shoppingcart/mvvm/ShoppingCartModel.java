package module.jk.cn.jkshoppingcart.module.shoppingcart.mvvm;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import module.jk.cn.jkshoppingcart.cache.ListCache;
import module.jk.cn.jkshoppingcart.cache.SpLocalCache;
import module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartInterface;
import module.jk.cn.jkshoppingcart.module.shoppingcart.model.ShoppingCartTestBean;

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
     * @interfaceName: ModelListener
     * @interfaceDescription: shoppingcart data listener
     * @author: leibing
     * @createTime: 2017/6/5
     */
    public interface ModelListener extends ShoppingCartInterface.DataToUIListener {
    }
}

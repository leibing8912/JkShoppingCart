package module.jk.cn.jkshoppingcart.module.shoppingcart;

import android.content.Context;
import java.lang.ref.WeakReference;

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
     * @interfaceName: ViewModelListener
     * @interfaceDescription: shoppingcart logical processing listener
     * @author: leibing
     * @createTime: 2017/6/5
     */
    public interface ViewModelListener extends ShoppingCartInterface.DataToUIListener {
        
    }
}

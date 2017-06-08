package module.jk.cn.jkshoppingcart.module.shoppingcart.mvvm;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartInterface;
import module.jk.cn.jkshoppingcart.module.shoppingcart.model.ShoppingCartBean;

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
        for (int i=0;i<mData.size();i++){
            switch (mData.get(groupPosition).product.get(i).productType){

            }
        }
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

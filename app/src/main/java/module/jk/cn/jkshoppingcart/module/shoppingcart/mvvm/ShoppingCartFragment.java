package module.jk.cn.jkshoppingcart.module.shoppingcart.mvvm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.cache.ListCache;
import module.jk.cn.jkshoppingcart.common.ShoppingCartDialog;
import module.jk.cn.jkshoppingcart.common.StringUtil;
import module.jk.cn.jkshoppingcart.common.ToastUtils;
import module.jk.cn.jkshoppingcart.module.AppManager;
import module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartInterface;
import module.jk.cn.jkshoppingcart.module.shoppingcart.adapter.ShoppingCartAdapter;
import module.jk.cn.jkshoppingcart.module.shoppingcart.model.ShoppingCartBean;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_CANNOT_COLLECT;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.AWARD_CANNOT_DELETE;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.COMPLETE_TXT;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.EDIT_TXT;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.LAYOUT_SHOW_HAS_GOODS;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.LAYOUT_SHOW_NO_GOODS;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_AWARD;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_AWARD_INVALID;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_GROUP;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_GROUP_INVALID;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_SKU;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.PRODUCT_TYPE_SKU_INVALID;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.SHOPPINGCART;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.TOTAL_PRICE_PREX;

/**
 * @className: ShoppingCartFragment
 * @classDescription: 购物车UI层（Fragment）
 * @author: leibing
 * @createTime: 2017/6/1
 */
public class ShoppingCartFragment extends Fragment implements ShoppingCartInterface.CheckInterface,
        ShoppingCartInterface.ModifyCountInterface, ShoppingCartInterface.DeleteInterface {
    // actionbar返回
    @BindView(R.id.iv_back)
    ImageView backIv;
    // actionbar标题
    @BindView(R.id.tv_title)
    TextView titleTv;
    // actionbar右按钮
    @BindView(R.id.btn_edit)
    Button editBtn;
    // 提示栏容器
    @BindView(R.id.ly_shoppingcart_tips)
    LinearLayout shoppingcartTipsLy;
    // 提示栏文案
    @BindView(R.id.tv_shoppingcart_tips)
    TextView shoppingcartTipsTv;
    // 全选
    @BindView(R.id.cb_all_check)
    CheckBox allCheckCb;
    // 收藏、删除布局
    @BindView(R.id.ly_collect_delete)
    LinearLayout collectDeleteLy;
    // 结算布局
    @BindView(R.id.ly_total_settle)
    LinearLayout totalSettleLy;
    // 合计金额
    @BindView(R.id.tv_total)
    TextView totalTv;
    // 购物车列表
    @BindView(R.id.exlv_shoppingcart)
    ExpandableListView shoppingcartExlv;
    // 购物车中有商品布局
    @BindView(R.id.rly_has_goods)
    RelativeLayout hasGoodsRly;
    // 购物车中无商品布局
    @BindView(R.id.ly_no_goods)
    LinearLayout noGoodsLy;
    // 数据源
    private ArrayList<ShoppingCartBean> mData;
    // 适配器
    private ShoppingCartAdapter mAdapter;
    // 购买的商品总价
    private double totalPrice = 0.00;
    // 购买的商品总数量
    private int totalCount = 0;
    // 是否编辑
    private boolean isEdited = false;
    // shoppingcart logical processing instance
    private ShoppingCartViewModel mShoppingCartViewModel;
    // shoppingcart logical processing listener
    private ShoppingCartViewModel.ViewModelListener mViewModelListener
            = new ShoppingCartViewModel.ViewModelListener() {

        @Override
        public void setData(ArrayList<ShoppingCartBean> mDatas) {
            mData = mDatas;
            // 设置全选or反选
            setAllCheck();
            // 更新数据源
            updateData();
            // 统计操作(购物车数量、合计金额)
            calculate();
        }

        @Override
        public void readBeforeEditData(Object object) {
            ListCache<ShoppingCartBean> mReadCache
                    = (ListCache<ShoppingCartBean>) object;
            mData = mReadCache.getObjList();
            // 设置全选or反选
            setAllCheck();
            // 更新数据源
            updateData();
            // 统计操作(购物车数量、合计金额)
            calculate();
        }

        @Override
        public void saveBeforeEditDataSuccess() {
            doCheckAll(false, isEdited);
        }

        @Override
        public void toastShow(String msg) {
            ToastUtils.show(AppManager.getInstance().currentActivity(), msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // create view
        View view = inflater.inflate(R.layout.fragment_shopping_cart, null);
        // bind buffer knife
        ButterKnife.bind(this, view);
        // initView
        initView();
        // set adapter
        setAdapter();
        // init shoppingcart logical processing
        initShoppingCartViewModel();
        // get data to update ui
        getData();

        return view;
    }

    /**
      * init shoppingcart logical processing
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void initShoppingCartViewModel() {
        mShoppingCartViewModel = new ShoppingCartViewModel(getActivity(), mViewModelListener);
    }

    /**
      * init view
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void initView() {
        // 右上角文案显示为“编辑”
        editBtn.setText(EDIT_TXT);
        editBtn.setVisibility(View.VISIBLE);
    }

    /**
      * get data to update ui
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void getData() {
        ShoppingCartBean mShoppingCartBean = new ShoppingCartBean();
        mShoppingCartBean.isInvalidGoods = false;
        mShoppingCartBean.freeShippingTips = "健客自营产品，优惠多多~";
        mShoppingCartBean.sellerName = "健客自营";
        ArrayList<ShoppingCartBean.Product> products = new ArrayList<>();

        ShoppingCartBean.Product product = new ShoppingCartBean.Product();
        product.productType = PRODUCT_TYPE_SKU;
        product.productId = "77958";
        ShoppingCartBean.Product.SkuProduct skuProduct = new ShoppingCartBean.Product.SkuProduct();
        skuProduct.realPrice = 89;
        skuProduct.productQualification = "5包*10";
        skuProduct.productAmount = 5;
        skuProduct.imgUrl = "https://image.jianke.com/suo/upload/prodimage/201403/2014310103337874!200x200.jpg";
        skuProduct.originPrice = 133.1;
        skuProduct.productName = "名露";
        product.skuProduct = skuProduct;
        products.add(product);

        product = new ShoppingCartBean.Product();
        product.productId = "77959";
        product.productType = PRODUCT_TYPE_GROUP;
        ShoppingCartBean.Product.GroupProduct groupProduct
                = new ShoppingCartBean.Product.GroupProduct();
        groupProduct.groupAmount = 2;
        groupProduct.groupPrice = 12.8;
        groupProduct.imgUrl = "https://image.jianke.com/suo/upload/prodimage/201603/201631716227853!200x200.jpg";
        groupProduct.productName = "盐酸氨基葡萄糖胶囊(葡立)";
        ArrayList<ShoppingCartBean.Product.GroupProduct.ChildProduct> childList = new ArrayList<>();
        for (int i=0;i<3;i++) {
            ShoppingCartBean.Product.GroupProduct.ChildProduct childProduct
                    = new ShoppingCartBean.Product.GroupProduct.ChildProduct();
            childList.add(childProduct);
        }
        groupProduct.childList = childList;
        product.groupProduct = groupProduct;
        products.add(product);

        product = new ShoppingCartBean.Product();
        product.productType = PRODUCT_TYPE_AWARD;
        products.add(product);

        mShoppingCartBean.product = products;

        mData.add(mShoppingCartBean);

        mShoppingCartBean = new ShoppingCartBean();
        mShoppingCartBean.isInvalidGoods = true;
        mShoppingCartBean.freeShippingTips = "";
        mShoppingCartBean.sellerName = "失效商品";
        products = new ArrayList<>();

        product = new ShoppingCartBean.Product();
        product.productType = PRODUCT_TYPE_SKU_INVALID;
        products.add(product);

        product = new ShoppingCartBean.Product();
        product.productType = PRODUCT_TYPE_GROUP_INVALID;
        products.add(product);

        product = new ShoppingCartBean.Product();
        product.productType = PRODUCT_TYPE_AWARD_INVALID;
        products.add(product);

        mShoppingCartBean.product = products;

        mData.add(mShoppingCartBean);

        if (mAdapter != null){
            mAdapter.setData(mData);
            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                // 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
                shoppingcartExlv.expandGroup(i);
            }
        }
    }

    /**
      * set adapter
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void setAdapter() {
        // init data list
        mData  = new ArrayList<>();
        // init adapter
        mAdapter = new ShoppingCartAdapter(getActivity(), mData);
        // 设置复选框接口
        mAdapter.setCheckInterface(this);
        // 设置改变数量接口
        mAdapter.setModifyCountInterface(this);
        // 设置删除接口
        mAdapter.setDeleteInterface(this);
        // 设置购物车item长按监听
        shoppingcartExlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id)
                        == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    long packedPos = ((ExpandableListView) parent).getExpandableListPosition(position);
                    final int groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);
                    final int childPosition = ExpandableListView.getPackedPositionChild(packedPos);
                    if (getActivity() == null)
                        return false;
                    // 失效产品长按不让弹窗
                    if (mData.get(groupPosition).product.get(childPosition).productType == PRODUCT_TYPE_AWARD_INVALID ||
                            mData.get(groupPosition).product.get(childPosition).productType == PRODUCT_TYPE_GROUP_INVALID ||
                            mData.get(groupPosition).product.get(childPosition).productType == PRODUCT_TYPE_SKU_INVALID)
                        return false;
                    // 收藏、删除
                    ShoppingCartDialog.getInstance().createDialogOne(getActivity(),
                                new ShoppingCartDialog.DialogCallBack() {
                                    @Override
                                    public void leftBtnListener() {
                                        // 收藏处理
                                        // 当子项为单品正常、组合正常才能收藏
                                        if (mData.get(groupPosition).product.get(childPosition).productType == PRODUCT_TYPE_SKU ||
                                                mData.get(groupPosition).product.get(childPosition).productType == PRODUCT_TYPE_GROUP) {
                                            if (mShoppingCartViewModel != null)
                                                mShoppingCartViewModel.collectSingProduct(mData, groupPosition, childPosition);
                                        }else {
                                            ToastUtils.show(AppManager.getInstance().currentActivity(), AWARD_CANNOT_COLLECT);
                                        }
                                    }

                                    @Override
                                    public void rightBtnListener(String content) {
                                        // 当子项为单品正常、组合正常才能删除
                                        if (mData.get(groupPosition).product.get(childPosition).productType == PRODUCT_TYPE_SKU ||
                                                mData.get(groupPosition).product.get(childPosition).productType == PRODUCT_TYPE_GROUP) {
                                            // 删除处理
                                            if (mShoppingCartViewModel != null)
                                                mShoppingCartViewModel.delSingleItem(mData, groupPosition, childPosition);
                                        }else {
                                            ToastUtils.show(AppManager.getInstance().currentActivity(), AWARD_CANNOT_DELETE);
                                        }
                                    }

                                    @Override
                                    public void selectedListener(String content) {
                                    }
                                }).show();
                    return true;
                }
                return false;
            }
        });
        // set adapter
        shoppingcartExlv.setAdapter(mAdapter);
        // 设置点击不收缩
        shoppingcartExlv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn_edit, R.id.btn_collect, R.id.btn_delete,
            R.id.btn_pay, R.id.cb_all_check, R.id.ly_all_check, R.id.btn_go_shop})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                // 返回
                break;
            case R.id.btn_edit:
                // 编辑
                editLogic();
                break;
            case R.id.btn_collect:
                // 移入收藏
                if (mShoppingCartViewModel != null)
                    mShoppingCartViewModel.collectSeletedProduct(mData);
                break;
            case R.id.btn_delete:
                // 删除
                if (mShoppingCartViewModel != null)
                    mShoppingCartViewModel.delSeletedProduct(mData);
                break;
            case R.id.btn_pay:
                // 结算
                break;
            case R.id.cb_all_check:
                // 全选、反选
                doCheckAll(allCheckCb.isChecked(), isEdited);
                break;
            case R.id.ly_all_check:
                allCheckCb.setChecked(!allCheckCb.isChecked());
                // 全选、反选
                doCheckAll(allCheckCb.isChecked(), isEdited);
                break;
            case R.id.btn_go_shop:
                // 逛一逛
                break;
            default:
                break;
        }
    }

    /**
      * 编辑逻辑
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void editLogic() {
        switch (editBtn.getText().toString().trim()){
            case EDIT_TXT:
                // 编辑处理
                // 保存编辑前数据
                isEdited = true;
                if (mShoppingCartViewModel != null)
                    mShoppingCartViewModel.saveBeforeEditData(mData);
                allCheckCb.setChecked(false);
                editBtn.setText(COMPLETE_TXT);
                collectDeleteLy.setVisibility(View.VISIBLE);
                totalSettleLy.setVisibility(View.GONE);
                break;
            case COMPLETE_TXT:
                // 编辑完成
                // 获取编辑前数据
                isEdited = false;
                if (mShoppingCartViewModel != null)
                    mShoppingCartViewModel.getBeforeEditData();
                editBtn.setText(EDIT_TXT);
                collectDeleteLy.setVisibility(View.GONE);
                totalSettleLy.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
      * 判断是否全选
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private boolean isAllCheck() {
        for (ShoppingCartBean model : mData) {
            if (!model.isSelected)
                return false;
        }
        return true;
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        if (mData != null
                && mData.size() !=0
                && groupPosition < mData.size()
                && mData.get(groupPosition) != null
                && mData.get(groupPosition).product != null
                && mData.get(groupPosition).product.size() != 0){
            for (int i = 0; i < mData.get(groupPosition).product.size(); i++) {
                mData.get(groupPosition).product.get(i).isSelected = isChecked;
            }
        }
        // 设置全选or反选
        setAllCheck();
        // 更新数据源
        updateData();
        // 统计操作(购物车数量、合计金额)
        calculate();
    }

    /**
      * 全选、反选
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param isSelectAll 是否全选
      * @param isEdit 是否编辑
      * @return
      */
    private void doCheckAll(boolean isSelectAll, boolean isEdit) {
        if (mData == null || mData.size() == 0)
            return;
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).isSelected = isSelectAll;
            if (mData.get(i).product != null
                    && mData.get(i).product.size() != 0) {
                for (int j = 0; j < mData.get(i).product.size(); j++) {
                    mData.get(i).product.get(j).isSelected = isSelectAll;
                    mData.get(i).product.get(j).isEdit = isEdit;
                }
            }
        }
        // 更新数据源
        updateData();
        // 统计操作(购物车数量、合计金额)
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        // 判断改组下面的所有子元素是否是同一种状态
        boolean allChildSameState = true;
        if (mData != null
                && mData.size() !=0
                && groupPosition < mData.size()
                && mData.get(groupPosition) != null
                && mData.get(groupPosition).product != null
                && mData.get(groupPosition).product.size() != 0){
            for (int i = 0; i < mData.get(groupPosition).product.size(); i++) {
                // 不全选中
                if (mData.get(groupPosition).product.get(i).isSelected != isChecked) {
                    allChildSameState = false;
                    break;
                }
            }

            if (allChildSameState) {
                // 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
                mData.get(groupPosition).isSelected = isChecked;
            } else {
                // 否则，组元素一律设置为未选中状态
                mData.get(groupPosition).isSelected = false;
            }
        }
        // 设置全选or反选
        setAllCheck();
        // 更新数据源
        updateData();
        // 统计操作(购物车数量、合计金额)
        calculate();
    }

    /**
      * 更新数据源
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void updateData(){
        if (mAdapter != null && mData.size() != 0){
            whatLayoutShow(LAYOUT_SHOW_HAS_GOODS);
            mAdapter.setData(mData);
        }else {
            whatLayoutShow(LAYOUT_SHOW_NO_GOODS);
        }
    }

    /**
      * 布局显示
      * @author leibing
      * @createTime 2017/6/9
      * @lastModify 2017/6/9
      * @param viewIndex
      * @return
      */
    private void whatLayoutShow(int viewIndex){
        switch (viewIndex){
            case LAYOUT_SHOW_HAS_GOODS:
                // 有商品
                hasGoodsRly.setVisibility(View.VISIBLE);
                noGoodsLy.setVisibility(View.GONE);
                break;
            case LAYOUT_SHOW_NO_GOODS:
                // 无商品
                hasGoodsRly.setVisibility(View.GONE);
                noGoodsLy.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
      * 设置全选or反选
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void setAllCheck(){
        if (isAllCheck()) {
            // 全选
            allCheckCb.setChecked(true);
        } else {
            // 反选
            allCheckCb.setChecked(false);
        }
    }

    /**
      * 统计操作(购物车数量、合计金额)
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void calculate(){
        totalCount = 0;
        totalPrice = 0.00;
        if (mData != null
                && mData.size() !=0){
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i) != null
                        && mData.get(i).product != null){
                    for (int j=0; j<mData.get(i).product.size();j++){
                        ShoppingCartBean.Product mProduct = mData.get(i).product.get(j);
                        if (mProduct != null
                                && mProduct.isSelected
                                && (mProduct.productType == PRODUCT_TYPE_SKU
                                || mProduct.productType == PRODUCT_TYPE_GROUP
                                || mProduct.productType == PRODUCT_TYPE_AWARD)){
                            totalCount++;
                            switch (mProduct.productType){
                                case PRODUCT_TYPE_SKU:
                                    // 单品正常
                                    if (mProduct.skuProduct != null){
                                        totalPrice += mProduct.skuProduct.realPrice
                                                * mProduct.skuProduct.productAmount;
                                    }
                                    break;
                                case PRODUCT_TYPE_GROUP:
                                    // 组合正常
                                    if (mProduct.groupProduct != null){
                                        totalPrice += mProduct.groupProduct.groupPrice
                                                * mProduct.groupProduct.groupAmount;
                                    }
                                    break;
                                case PRODUCT_TYPE_AWARD:
                                    // 奖品正常
                                    if (mProduct.awardProduct != null){
                                        totalPrice += mProduct.awardProduct.realPrice
                                                * mProduct.awardProduct.awardAmount;
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
            // 合计金额
            totalTv.setText(TOTAL_PRICE_PREX + StringUtil.doubleTwoDecimal(totalPrice));
            // 计算购物车的金额为0时候清空购物车的视图
            if(totalCount == 0){
                titleTv.setText(SHOPPINGCART);
            } else{
                titleTv.setText(SHOPPINGCART + "(" + totalCount + ")");
            }
        }
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        int currentCount = 0;
        ShoppingCartBean.Product product = mData.get(groupPosition).product.get(childPosition);
        switch (product.productType){
            case PRODUCT_TYPE_SKU:
                // 单品正常
                currentCount = product.skuProduct.productAmount;
                if (currentCount >= 200){
                    return;
                }
                currentCount ++;
                mData.get(groupPosition).product.get(childPosition).skuProduct.productAmount
                        = currentCount;
                break;
            case PRODUCT_TYPE_GROUP:
                // 组合正常
                currentCount = product.groupProduct.groupAmount;
                if (currentCount >= 100){
                    return;
                }
                currentCount ++;
                mData.get(groupPosition).product.get(childPosition).groupProduct.groupAmount
                        = currentCount;
                break;
            default:
                break;
        }
        ((EditText) showCountView).setText(currentCount + "");
        // 更新数据源
        updateData();
        // 统计操作(购物车数量、合计金额)
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        int currentCount = 0;
        ShoppingCartBean.Product product = mData.get(groupPosition).product.get(childPosition);
        switch (product.productType){
            case PRODUCT_TYPE_SKU:
                // 单品正常
                currentCount = product.skuProduct.productAmount;
                if (currentCount == 1)
                    return;
                currentCount --;
                mData.get(groupPosition).product.get(childPosition).skuProduct.productAmount
                        = currentCount;
                break;
            case PRODUCT_TYPE_GROUP:
                // 组合正常
                currentCount = product.groupProduct.groupAmount;
                if (currentCount == 1)
                    return;
                currentCount --;
                mData.get(groupPosition).product.get(childPosition).groupProduct.groupAmount
                        = currentCount;
                break;
            default:
                break;
        }
        ((EditText) showCountView).setText(currentCount + "");
        // 更新数据源
        updateData();
        // 统计操作(购物车数量、合计金额)
        calculate();
    }

    @Override
    public void doEditNum(final int groupPosition, final int childPosition, View showCountView, boolean isChecked) {
        if (getActivity() == null)
            return;
        ShoppingCartDialog.getInstance().createDialogTwo(getActivity(),
                new ShoppingCartDialog.DialogCallBack() {
            @Override
            public void leftBtnListener() {
            }

            @Override
            public void rightBtnListener(String content) {
                if (StringUtil.strIsNum(content)){
                    int count = Integer.parseInt(content);
                    if (count < 1){
                        return;
                    }
                    ShoppingCartBean.Product product = mData.get(groupPosition).product.get(childPosition);
                    switch (product.productType){
                        case PRODUCT_TYPE_SKU:
                            // 单品正常
                            if (count > 200){
                                return;
                            }
                            mData.get(groupPosition).product.get(childPosition).skuProduct.productAmount
                                    = count;
                            break;
                        case PRODUCT_TYPE_GROUP:
                            // 组合正常
                            if (count > 100){
                                return;
                            }
                            mData.get(groupPosition).product.get(childPosition).groupProduct.groupAmount
                                    = count;
                            break;
                        default:
                            break;
                    }
                    // 更新数据源
                    updateData();
                    // 统计操作(购物车数量、合计金额)
                    calculate();
                }
            }

                    @Override
                    public void selectedListener(String content) {
                    }
                }).show();
    }

    @Override
    public void doClearInvalid(int groupPosition) {
        if (mShoppingCartViewModel != null)
            mShoppingCartViewModel.clearInvalidGoods(mData, groupPosition);
    }
}

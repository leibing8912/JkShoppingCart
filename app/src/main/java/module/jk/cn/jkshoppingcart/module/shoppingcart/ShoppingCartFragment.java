package module.jk.cn.jkshoppingcart.module.shoppingcart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import module.jk.cn.jkshoppingcart.R;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.COMPLETE_TXT;
import static module.jk.cn.jkshoppingcart.module.shoppingcart.ShoppingCartConstant.EDIT_TXT;

/**
 * @className: ShoppingCartFragment
 * @classDescription: 购物车UI层（Fragment）
 * @author: leibing
 * @createTime: 2017/6/1
 */
public class ShoppingCartFragment extends Fragment implements ShoppingCartInterface.CheckInterface {
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
    @BindView(R.id.ly_send_tips)
    LinearLayout sendTipsLy;
    // 提示栏文案
    @BindView(R.id.tv_send_tips)
    TextView sendTipsTv;
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
    // 数据源
    private ArrayList<ShoppingCartTestBean> mData;
    // 适配器
    private ShoppingCartAdapter mAdapter;

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
        // get data to update ui
        getData();

        return view;
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
        for (int i=0; i< 5; i++){
            ShoppingCartTestBean mShoppingCartTestBean = new ShoppingCartTestBean();
            mShoppingCartTestBean.isSelected = false;
            mShoppingCartTestBean.sellerName = "卖家" + i;
            ArrayList<ShoppingCartTestBean.Product> mProducts = new ArrayList<>();
            for (int j=0;j< 5;j++){
                ShoppingCartTestBean.Product mProduct = new ShoppingCartTestBean.Product();
                mProduct.productName = "产品" + j;
                mProduct.isSelected = false;
                mProducts.add(mProduct);
            }
            mShoppingCartTestBean.product = mProducts;
            mData.add(mShoppingCartTestBean);
        }
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
        // set adapter
        shoppingcartExlv.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_back, R.id.btn_edit, R.id.btn_collect, R.id.btn_delete,
            R.id.btn_pay, R.id.cb_all_check})
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
                break;
            case R.id.btn_delete:
                // 删除
                break;
            case R.id.btn_pay:
                // 结算
                break;
            case R.id.cb_all_check:
                // 全选、反选
                doCheckAll();
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
                editBtn.setText(COMPLETE_TXT);
                collectDeleteLy.setVisibility(View.VISIBLE);
                totalSettleLy.setVisibility(View.GONE);
                break;
            case COMPLETE_TXT:
                // 编辑完成
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
        for (ShoppingCartTestBean model : mData) {
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

        if (isAllCheck()) {
            // 全选
            allCheckCb.setChecked(true);
        } else {
            // 反选
            allCheckCb.setChecked(false);
        }

        if (mAdapter != null){
            mAdapter.setData(mData);
        }
    }
    
    /**
      * 全选、反选
      * @author leibing
      * @createTime 2017/6/5
      * @lastModify 2017/6/5
      * @param
      * @return
      */
    private void doCheckAll() {
        if (mData == null || mData.size() == 0)
            return;
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).isSelected = allCheckCb.isChecked();
            if (mData.get(i).product != null
                    && mData.get(i).product.size() != 0) {
                for (int j = 0; j < mData.get(i).product.size(); j++) {
                    mData.get(i).product.get(j).isSelected = allCheckCb.isChecked();
                }
            }
        }
        if (mAdapter != null)
            mAdapter.setData(mData);
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

        if (isAllCheck()) {
            // 全选
            allCheckCb.setChecked(true);
        } else {
            // 反选
            allCheckCb.setChecked(false);
        }

        if (mAdapter != null){
            mAdapter.setData(mData);
        }
    }
}

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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import module.jk.cn.jkshoppingcart.R;

/**
 * @className: ShoppingCartFragment
 * @classDescription: 购物车UI层（Fragment）
 * @author: leibing
 * @createTime: 2017/6/1
 */
public class ShoppingCartFragment extends Fragment{
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // create view
        View view = inflater.inflate(R.layout.fragment_shopping_cart, null);
        // bind buffer knife
        ButterKnife.bind(view);

        return view;
    }

    @OnClick({R.id.iv_back, R.id.btn_edit, R.id.btn_collect, R.id.btn_delete,
            R.id.btn_pay})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                // 返回
                break;
            case R.id.btn_edit:
                // 编辑
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
            default:
                break;
        }
    }
}

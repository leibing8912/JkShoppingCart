package module.jk.cn.jkshoppingcart.module.orderconfirm;

import android.os.Bundle;

import butterknife.ButterKnife;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.module.BaseFragmentActivity;
/**
 * @className: OrderConfirmActivity
 * @classDescription: 订单确认UI层（Activity）
 * @author: leibing
 * @createTime: 2017/6/9
 */
public class OrderConfirmActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        // bind buffer knife
        ButterKnife.bind(this);
    }
}

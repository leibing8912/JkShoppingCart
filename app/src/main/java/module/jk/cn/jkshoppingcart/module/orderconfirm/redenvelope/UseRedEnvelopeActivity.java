package module.jk.cn.jkshoppingcart.module.orderconfirm.redenvelope;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.module.BaseFragmentActivity;

/**
 * @className: 使用红包
 * @classDescription: UseRedEnvelopeActivity
 * @author: leibing
 * @createTime: 2017/6/12
 */
public class UseRedEnvelopeActivity extends BaseFragmentActivity
        implements UseRedEnvelopeAdapter.CheckInterface{
    // 使用红包文案
    public final static String  USE_RED_ENVELOPE = "使用红包";
    // 返回
    @BindView(R.id.iv_back)
    ImageView backIv;
    // 标题
    @BindView(R.id.tv_title)
    TextView titleTv;
    // 右按钮
    @BindView(R.id.btn_edit)
    Button editBtn;
    // 列表
    @BindView(R.id.lv_use_red_envelope)
    ListView useRedEnvelopeLv;
    // 优惠累计金额
    @BindView(R.id.tv_discount_amount)
    TextView discountAmountTv;
    // 适配器
    private UseRedEnvelopeAdapter mAdapter;
    // 数据源
    private ArrayList<UseRedEnvelopeModel> mData;
    // 待付款总金额
    private double totalAmount;
    // 总计优惠金额
    private double totalDiscountAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_red_envelope);
        // bind buffer knife
        ButterKnife.bind(this);
        // init
        init();
        // set adapter
        setAdapter();
        // get data
        getData();
    }

    /**
     * get data
     * @author leibing
     * @createTime 2017/6/10
     * @lastModify 2017/6/10
     * @param
     * @return
     */
    private void getData() {
        UseRedEnvelopeModel model = new UseRedEnvelopeModel();
        model.redEnvelopeValue = 10;
        model.redEnvelopeValidDate = "有效期：还有5天到期";
        mData.add(model);

        for (int i=0;i<3;i++){
            model = new UseRedEnvelopeModel();
            model.redEnvelopeValue = 5;
            model.redEnvelopeValidDate = "有效期：还有10天到期";
            mData.add(model);
        }

        for (int i=0;i<5;i++){
            model = new UseRedEnvelopeModel();
            model.redEnvelopeValue = 20;
            model.redEnvelopeValidDate = "有效期：还有3天到期";
            mData.add(model);
        }

        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    /**
     * set adapter
     * @author leibing
     * @createTime 2017/6/10
     * @lastModify 2017/6/10
     * @param
     * @return
     */
    private void setAdapter() {
        // init data
        mData = new ArrayList<>();
        // init adapter
        mAdapter = new UseRedEnvelopeAdapter(this, mData);
        // 设置选项接口
        mAdapter.setCheckInterface(this);
        // set adapter
        useRedEnvelopeLv.setAdapter(mAdapter);
    }

    /**
     * init
     * @author leibing
     * @createTime 2017/6/10
     * @lastModify 2017/6/10
     * @param
     * @return
     */
    private void init() {
        editBtn.setVisibility(View.GONE);
        backIv.setVisibility(View.VISIBLE);
        titleTv.setText(USE_RED_ENVELOPE);
    }

    /**
     * 计算优惠金额
     * @author leibing
     * @createTime 2017/6/10
     * @lastModify 2017/6/10
     * @param
     * @return
     */
    private void calculateDiscountAmount(){
        // 优惠劵金额
        totalDiscountAmount = 0;
        for (int i=0;i<mData.size();i++){
            UseRedEnvelopeModel model = mData.get(i);
            if (model != null && model.isRedEnvelopeSelected) {
                totalDiscountAmount+=model.redEnvelopeValue;
            }
        }
        discountAmountTv.setText("红包累计金额：" + (int) totalDiscountAmount);
    }

    /**
     * 重置
     * @author leibing
     * @createTime 2017/6/10
     * @lastModify 2017/6/10
     * @param
     * @return
     */
    private void resetData(){
        if (mData == null)
            return;
        for (int i=0;i<mData.size();i++){
            mData.get(i).isRedEnvelopeSelected = false;
        }
        totalDiscountAmount = 0;
        discountAmountTv.setText("红包累计金额：" + (int) totalDiscountAmount);
        if (mAdapter != null)
            mAdapter.setData(mData);
    }

    @OnClick({R.id.btn_discount_amount_ok,
            R.id.btn_discount_amount_reset})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_discount_amount_ok:
                // 确定
                break;
            case R.id.btn_discount_amount_reset:
                // 重置
                resetData();
                break;
        }
    }

    @Override
    public void itemCheck(int position, boolean isChecked) {
        mData.get(position).isRedEnvelopeSelected = isChecked;
        // 如果选中，则其他红包均不能选中
        if (isChecked){
            for (int i=0;i<mData.size();i++){
                if (i != position){
                    mData.get(i).isRedEnvelopeSelected = false;
                }
            }
        }
        // 更新数据源
        if (mAdapter != null)
            mAdapter.setData(mData);
        // 计算红包优惠金额
        calculateDiscountAmount();
    }
}

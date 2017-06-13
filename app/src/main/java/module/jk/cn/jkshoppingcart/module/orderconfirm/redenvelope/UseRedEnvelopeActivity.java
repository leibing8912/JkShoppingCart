package module.jk.cn.jkshoppingcart.module.orderconfirm.redenvelope;

import android.content.Intent;
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
import module.jk.cn.jkshoppingcart.module.AppManager;
import module.jk.cn.jkshoppingcart.module.BaseFragmentActivity;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_ORIGIN_RED_ENVELOPE_VALUE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_REAL_RED_ENVELOPE_VALUE;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_RED_ENVELOPE_LIST;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAGE_INTENT_RED_ENVELOPE_OPTIMAL_POSITION;

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
    // 原始红包优惠金额
    private double originDiscountAmount;
    // 总计红包优惠金额
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
        // get intent to update ui
        getIntentUpdateUi();
    }

    /**
     * get intent to update ui
     * @author leibing
     * @createTime 2017/6/13
     * @lastModify 2017/6/13
     * @param
     * @return
     */
    private void getIntentUpdateUi() {
        Bundle bundle = getIntent().getExtras();
        int redEnvelopePosition = -1;
        if (bundle != null){
            mData = (ArrayList<UseRedEnvelopeModel>) bundle.getSerializable(PAGE_INTENT_RED_ENVELOPE_LIST);
            originDiscountAmount = bundle.getDouble(PAGE_INTENT_ORIGIN_RED_ENVELOPE_VALUE);
            totalDiscountAmount = originDiscountAmount;
            redEnvelopePosition = bundle.getInt(PAGE_INTENT_RED_ENVELOPE_OPTIMAL_POSITION, -1);
        }
        // 更新数据源
        if (mAdapter != null)
            mAdapter.setData(mData);
        if (redEnvelopePosition != -1){
            itemCheck(redEnvelopePosition, true);
        }
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
            R.id.btn_discount_amount_reset,
            R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_discount_amount_ok:
                // 确定
                Intent intent = new Intent();
                intent.putExtra(PAGE_INTENT_ORIGIN_RED_ENVELOPE_VALUE, originDiscountAmount);
                intent.putExtra(PAGE_INTENT_REAL_RED_ENVELOPE_VALUE, totalDiscountAmount);
                intent.putExtra(PAGE_INTENT_RED_ENVELOPE_OPTIMAL_POSITION, -1);
                intent.putExtra(PAGE_INTENT_RED_ENVELOPE_LIST, mData);
                setResult(RESULT_OK, intent);
                AppManager.getInstance().finishActivity();
                break;
            case R.id.btn_discount_amount_reset:
                // 重置
                resetData();
                break;
            case R.id.iv_back:
                // 返回
                AppManager.getInstance().finishActivity();
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

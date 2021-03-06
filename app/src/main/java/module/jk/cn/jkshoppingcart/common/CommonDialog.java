package module.jk.cn.jkshoppingcart.common;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import module.jk.cn.jkshoppingcart.R;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.CASH_ON_DELIVER;
import static module.jk.cn.jkshoppingcart.module.orderconfirm.OrderConfirmConstant.PAY_ONLINE;

/**
 * @className: CommonDialog
 * @classDescription: 购物车
 * @author: leibing
 * @createTime: 2017/6/6
 */
public class CommonDialog {
    // 弹窗
    private Dialog mDialog;
    // sington
    private static CommonDialog instance;

    /**
      * Constructor
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param
      * @return
      */
    private CommonDialog(){
    }
    
    /**
      * sington
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param
      * @return
      */
    public static CommonDialog getInstance(){
        if (instance == null){
            instance = new CommonDialog();
        }
        return instance;
    }

    /**
     * 创建弹窗一
     * @author leibing
     * @createTime 2017/6/6
     * @lastModify 2017/6/6
     * @param context
     * @param mCallBack 回调监听
     * @return Dialog
     */
    public Dialog createDialogOne(Context context, DialogCallBack mCallBack){
        return createDialogOne(context, "", "", "", mCallBack);
    }

    /**
      * 创建弹窗一
      * @author leibing
      * @createTime 2017/6/6
      * @lastModify 2017/6/6
      * @param context
      * @param promptMsg 弹窗标题提示
      * @param leftBtnMsg 弹窗左按钮文案
      * @param rightBtnMsg 弹窗右按钮文案
      * @param mCallBack 回调监听
      * @return Dialog
      */
    public Dialog createDialogOne(Context context, String promptMsg,
                                  String leftBtnMsg, String rightBtnMsg,
                                  final DialogCallBack mCallBack){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_one, null);
        TextView promptMsgTv = (TextView) view.findViewById(R.id.tv_prompt_msg);
        Button collectBtn = (Button) view.findViewById(R.id.btn_collect);
        Button delBtn = (Button) view.findViewById(R.id.btn_del);
        if (StringUtil.isNotEmpty(promptMsg))
            promptMsgTv.setText(promptMsg);
        if (StringUtil.isNotEmpty(leftBtnMsg))
            collectBtn.setText(leftBtnMsg);
        if (StringUtil.isNotEmpty(rightBtnMsg))
            delBtn.setText(rightBtnMsg);
        // 左按钮点击事件
        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭对话框
                dismissDialog();
                if (mCallBack != null)
                    mCallBack.leftBtnListener();
            }
        });
        // 右按钮点击事件
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭对话框
                dismissDialog();
                if (mCallBack != null)
                    mCallBack.rightBtnListener(null);
            }
        });
        mDialog = new Dialog(context, R.style.ShoppingCartDialogStyle);
        mDialog.setCancelable(true);
        mDialog.setContentView(view,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        ,(LinearLayout.LayoutParams.MATCH_PARENT)));
        return mDialog;
    }

    /**
      * 创建弹窗二
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param context
      * @param mCallBack
      * @return
      */
    public Dialog createDialogTwo(Context context, DialogCallBack mCallBack){
        return createDialogTwo(context, "", "", mCallBack);
    }

    /**
      * 创建弹窗二
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param context
      * @param leftBtnMsg
      * @param rightBtnMsg
      * @param mCallBack
      * @return
      */
    public Dialog createDialogTwo(Context context,String leftBtnMsg, String rightBtnMsg,
                                  final DialogCallBack mCallBack){
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_shopping_car_input_buy_count, null);
        TextView cancelTv = (TextView) view.findViewById(R.id.tv_cancel);
        TextView okTv = (TextView) view.findViewById(R.id.tv_ok);
        final EditText inputEdt = (EditText) view.findViewById(R.id.et_input);
        if (StringUtil.isNotEmpty(leftBtnMsg))
            cancelTv.setText(leftBtnMsg);
        if (StringUtil.isNotEmpty(rightBtnMsg))
            okTv.setText(rightBtnMsg);
        // 左按钮点击事件
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭对话框
                dismissDialog();
                if (mCallBack != null)
                    mCallBack.leftBtnListener();
            }
        });
        // 右按钮点击事件
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭对话框
                dismissDialog();
                if (mCallBack != null)
                    mCallBack.rightBtnListener(inputEdt.getText().toString());
            }
        });
        mDialog = new Dialog(context, R.style.ShoppingCartDialogStyle);
        mDialog.setCancelable(true);
        mDialog.setContentView(view,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        ,(LinearLayout.LayoutParams.MATCH_PARENT)));
        return mDialog;
    }

    /**
      * 创建支付选择弹窗
      * @author leibing
      * @createTime 2017/6/9
      * @lastModify 2017/6/9
      * @param context
      * @param isOnlinePay
      * @param mCallBack
      * @return
      */
    public Dialog createPaymentSelectDialog(Context context,
                                            boolean isOnlinePay, final DialogCallBack mCallBack){
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_payment_select, null);
        RelativeLayout payOnlineRly = (RelativeLayout) view.findViewById(R.id.rly_pay_online);
        RelativeLayout cashOnDeliverRly = (RelativeLayout) view.findViewById(R.id.rly_cash_on_deliver);
        ImageView payOnlineIv = (ImageView) view.findViewById(R.id.iv_pay_online);
        ImageView cashOnDeliverIv = (ImageView) view.findViewById(R.id.iv_cash_on_deliver);
        if (isOnlinePay){
            payOnlineIv.setVisibility(View.VISIBLE);
            cashOnDeliverIv.setVisibility(View.GONE);
        }else {
            payOnlineIv.setVisibility(View.GONE);
            cashOnDeliverIv.setVisibility(View.VISIBLE);
        }
        // 在线支付点击事件
        payOnlineRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭对话框
                dismissDialog();
                if (mCallBack != null)
                    mCallBack.selectedListener(PAY_ONLINE);
            }
        });
        // 货到付款点击事件
        cashOnDeliverRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭对话框
                dismissDialog();
                if (mCallBack != null)
                    mCallBack.selectedListener(CASH_ON_DELIVER);
            }
        });

        mDialog = new Dialog(context, R.style.ShoppingCartDialogStyle);
        mDialog.setCancelable(true);
        mDialog.setContentView(view,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        ,(LinearLayout.LayoutParams.MATCH_PARENT)));
        return mDialog;
    }

    /**
      * 关闭对话框
      * @author leibing
      * @createTime 2017/6/6
      * @lastModify 2017/6/6
      * @param
      * @return
      */
    private void dismissDialog(){
        if (mDialog != null
                && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    /**
     * @interfaceName: DialogCallBack
     * @interfaceDescription: 弹窗回调
     * @author: leibing
     * @createTime: 2017/6/6
     */
    public interface DialogCallBack{
       // 左按钮监听回调
       void leftBtnListener();
       // 右按钮监听回调
       void rightBtnListener(String content);
       // 选中监听回调
       void selectedListener(String content);
    }
}

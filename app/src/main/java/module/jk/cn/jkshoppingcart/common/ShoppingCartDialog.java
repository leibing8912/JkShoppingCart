package module.jk.cn.jkshoppingcart.common;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import module.jk.cn.jkshoppingcart.R;

/**
 * @className: ShoppingCartDialog
 * @classDescription: 购物车
 * @author: leibing
 * @createTime: 2017/6/6
 */
public class ShoppingCartDialog {
    // 弹窗
    private Dialog mDialog;
    // sington
    private static ShoppingCartDialog instance;

    /**
      * Constructor
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param
      * @return
      */
    private ShoppingCartDialog(){
    }

    /**
      * sington
      * @author leibing
      * @createTime 2017/6/8
      * @lastModify 2017/6/8
      * @param
      * @return
      */
    public static ShoppingCartDialog getInstance(){
        if (instance == null){
            instance = new ShoppingCartDialog();
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
    }
}

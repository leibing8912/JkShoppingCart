package module.jk.cn.jkshoppingcart.module;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * @className: BaseFragmentActivity
 * @classDescription: fragmentActivity基类
 * @author: leibing
 * @createTime: 2017/6/2
 */
public class BaseFragmentActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 入栈
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // 出栈
        AppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AppManager.getInstance().finishActivity(this);
        super.onBackPressed();
    }

    /**
     * 启动目标页
     * @author leibing
     * @createTime 2017/3/14
     * @lastModify 2017/3/14
     * @param clazz
     * @return
     */
    protected void startTargetActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 启动目标页
     * @author leibing
     * @createTime 2017/3/14
     * @lastModify 2017/3/14
     * @param clazz
     * @param bundle
     * @return
     */
    protected void startTargetActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 启动目标页(带返回结果)
     * @author leibing
     * @createTime 2017/3/14
     * @lastModify 2017/6/13
     * @param clazz
     * @param bundle
     * @param requestId
     * @return
     */
    protected void startTargetActivityForResult(Class clazz, Bundle bundle, int requestId) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestId);
    }
}

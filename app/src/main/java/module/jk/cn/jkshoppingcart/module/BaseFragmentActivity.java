package module.jk.cn.jkshoppingcart.module;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

/**
 * @className: BaseFragmentActivity
 * @classDescription: fragmentActivity基类
 * @author: leibing
 * @createTime: 2017/6/2
 */
public class BaseFragmentActivity extends FragmentActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
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
}

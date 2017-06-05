package module.jk.cn.jkshoppingcart.module.shoppingcart.mvvm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import module.jk.cn.jkshoppingcart.R;
import module.jk.cn.jkshoppingcart.module.BaseFragmentActivity;
import module.jk.cn.jkshoppingcart.module.shoppingcart.adapter.ViewPagerAdapter;

/**
 * @className: ShoppingCartActivity
 * @classDescription: 购物车UI层（Activity）
 * @author: leibing
 * @createTime: 2017/6/1
 */
public class ShoppingCartActivity extends BaseFragmentActivity {
    // 购物车内容页
    @BindView(R.id.vp_main)
    ViewPager mainVp;
    // 购物车Fragment
    private ShoppingCartFragment mShoppingCartFragment;
    // fragment list
    private List<Fragment> mFragmentList;
    // title list
    private List<String> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bind buffer knife
        ButterKnife.bind(this);
        // init list
        initList();
        // init fragment
        initFragment();
        // set adapter for viewpager
        setAdapter();
    }

    /**
     * init list
     * @author leibing
     * @createTime 2017/6/1
     * @lastModify 2017/6/1
     * @param
     * @return
     */
    private void initList() {
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    /**
     * init fragment
     * @author leibing
     * @createTime 2017/2/28
     * @lastModify 2017/2/28
     * @param
     * @return
     */
    private void initFragment() {
        mShoppingCartFragment =  new ShoppingCartFragment();
        mFragmentList.add(mShoppingCartFragment);
    }

    /**
     * set adapter for viewpager
     * @author leibing
     * @createTime 2017/2/28
     * @lastModify 2017/2/28
     * @param
     * @return
     */
    private void setAdapter() {
        // init adapter
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                mFragmentList, mTitleList);
        // set adapter
        mainVp.setAdapter(mAdapter);
        // set page limit
        mainVp.setOffscreenPageLimit(1);
        // set page change listener
        mainVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}

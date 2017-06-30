package module.jk.cn.jkshoppingcart.common;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className: StringUtil
 * @classDescription: 字符串操作
 * @author: leibing
 * @createTime: 2016/08/30
 */
public class StringUtil {
	// 正则表达式:验证身份证
	public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

	/**
	 * 判断是否为null或空字符串
	 * @author leibing
	 * @createTime 2016/08/30
	 * @lastModify 2016/08/30
	 * @param str
	 * @return
	 */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

	/**
	 * 判断是否不为null或不是空字符串
	 * @author leibing
	 * @createTime 2016/08/30
	 * @lastModify 2016/08/30
	 * @param str
	 * @return
	 */
    public static boolean isNotEmpty(String str){
		if (str == null || str.trim().equals(""))
			return false;
		return true;
    }

	/**
	 * 根据类名获取对象实例
	 * @author leibing
	 * @createTime 2016/08/30
	 * @lastModify 2016/08/30
	 * @param className 类名
	 * @return
	 */
	public static Object getObject(String className){
		Object object = null;
		if(StringUtil.isNotEmpty(className)){
			try {
				object = Class.forName(className).newInstance();
			}catch(ClassNotFoundException cnf) {
			}
			catch(InstantiationException ie) {
			}
			catch(IllegalAccessException ia) {
			}
		}
		return object;
	}

	/**
	 * 字符串是否数字
	 * @author leibing
	 * @createTime 2016/11/17
	 * @lastModify 2016/11/17
	 * @param
	 * @return
	 */
	public static boolean strIsNum(String str){
		// 判断是否为空
		if (StringUtil.isEmpty(str))
			return false;
		// 去空格
		str = str.trim();
		// 匹配
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ) {
			return false;
		}
		return true;
	}

	/**
	 * 保留两位小数
	 * @author leibing
	 * @createTime 2017/3/13
	 * @lastModify 2017/3/13
	 * @param num
	 * @return
	 */
	public static String doubleTwoDecimal(Double num){
		DecimalFormat df  = new DecimalFormat("######0.00");
		return df.format(num);
	}

	/**
	  * 是否身份证号
	  * @author leibing
	  * @createTime 2017/6/12
	  * @lastModify 2017/6/12
	  * @param idCard
	  * @return
	  */
	public static boolean isIDCard(String idCard) {
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 设置商品总金额显示样式
	 * @author leibing
	 * @createTime 2017/3/16
	 * @lastModify 2017/3/16
	 * @param totalPrice
	 * @return
	 */
	public static SpannableString setTotalPriceType(double totalPrice){
		String targetStr = doubleTwoDecimal(totalPrice);
		targetStr = "￥" + targetStr;
		int length = targetStr.length();
		SpannableString msp = new SpannableString(targetStr);
		// 第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素。
		msp.setSpan(new AbsoluteSizeSpan(15,true), 0, 1,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new AbsoluteSizeSpan(24,true), 1, length-3,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new AbsoluteSizeSpan(15,true), length-3, length,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return msp;
	}
}

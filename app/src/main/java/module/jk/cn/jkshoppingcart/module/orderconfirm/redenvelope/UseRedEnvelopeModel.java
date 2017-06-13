package module.jk.cn.jkshoppingcart.module.orderconfirm.redenvelope;

import java.io.Serializable;

/**
 * @className: UseRedEnvelopeModel
 * @classDescription: 使用红包数据模型
 * @author: leibing
 * @createTime: 2017/6/12
 */
public class UseRedEnvelopeModel implements Serializable{
    // uid
    private static final long serialVersionUID = -1084623654318627780L;
    // 红包面值
    public int redEnvelopeValue;
    // 红包有效期
    public String redEnvelopeValidDate;
    // 红包是否选中
    public boolean isRedEnvelopeSelected = false;
}

package org.xyc.app.open.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xuyachang
 * @date 2024/9/2
 */
@Data
public class TiktokOrderTO {
    private String orderCode;
    private String orderStatus;
    private Long updateTime;
    private Long buyerId;
    private String buyerName;
    private String buyerMobile;
    private String shippingAddress;
    private String buyerLng;
    private String buyerLat;
    private Long shopId;
    private String shopName;
    private BigDecimal needPaidAmt;
    private BigDecimal paidAmt;
    private BigDecimal shippingAmt;
    private BigDecimal skuAmt;
}

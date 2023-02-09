package pers.jd.entity.vo;

import lombok.Data;
import pers.jd.entity.PurchaseMain;
import pers.jd.entity.PurchaseSub;

import java.util.List;

@Data
public class PurchaseMainAndSub {
    public PurchaseMain purchaseMain;
    public List<PurchaseSub> purchaseSubs;
}
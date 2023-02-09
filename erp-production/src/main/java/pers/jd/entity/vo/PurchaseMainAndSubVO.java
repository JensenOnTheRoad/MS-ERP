package pers.jd.entity.vo;

import lombok.Data;
import pers.jd.entity.PurchaseMain;
import pers.jd.entity.PurchaseSub;

import java.util.List;

@Data
public class PurchaseMainAndSubVO {
    public PurchaseMainVO purchaseMainVO;
    public List<PurchaseSubVO> purchaseSubsVO;
}
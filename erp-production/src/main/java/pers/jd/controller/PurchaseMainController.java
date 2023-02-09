package pers.jd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import pers.jd.entity.*;
import pers.jd.entity.dto.RequestModelPurchaseMain;
import pers.jd.entity.vo.*;
import pers.jd.feignService.FeignCommodityService;
import pers.jd.feignService.FeignFinancialService;
import pers.jd.feignService.FeignHrService;
import pers.jd.feignService.FeignWarehouseService;
import pers.jd.feignService.dto.Financial;
import pers.jd.feignService.dto.StorageRecord;
import pers.jd.service.impl.MaterialServiceImpl;
import pers.jd.service.impl.PurchaseMainServiceImpl;
import pers.jd.service.impl.PurchaseSubServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 采购主单 前端控制器
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Transactional
@RestController
@CrossOrigin
@RequestMapping(value = "/purchase-main")
public class PurchaseMainController {
    @Resource
    PurchaseMainServiceImpl purchaseMainService;
    @Resource
    PurchaseSubServiceImpl purchaseSubService;
    @Resource
    MaterialServiceImpl materialService;

    @Resource
    FeignWarehouseService feignWarehouseService;
    @Resource
    FeignCommodityService feignCommodityService;
    @Resource
    FeignHrService feignHrService;
    @Resource
    FeignFinancialService feignFinancialService;

    // region

    /**
     * 插入一个生产计划
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultSet<Object> insertOrUpdate(@RequestBody final List<RequestModelPurchaseMain> params) throws Exception {
        boolean STATUS = false;

        if (params != null) {
            int principalId = 0;
            int warehouseId = 0;
            // 主单
            PurchaseMain purchaseMain = new PurchaseMain();

            purchaseMain.setPurchaseTime(LocalDateTime.now());
            purchaseMain.setStatus(1);

            purchaseMainService.save(purchaseMain);
            Integer newId = purchaseMain.getId();

            // 副单
            BigDecimal totalAmount = new BigDecimal(0);     // 总金额
            for (RequestModelPurchaseMain temp : params) {
                if (temp.id != 0) {

                    int id = temp.id;
                    int number = temp.number;
                    principalId = temp.principalId;
                    warehouseId = temp.warehouseId;

                    Material material = materialService.getById(id);
                    BigDecimal price = material.getPrice();     // 单价
                    BigDecimal temp_amount = price.multiply(new BigDecimal(number));//单价*数量
                    totalAmount = totalAmount.add(temp_amount);     // 累加总金额

                    PurchaseSub purchaseSub = new PurchaseSub();

                    purchaseSub.setQuantify(number);
                    purchaseSub.setCostAmount(temp_amount);
                    purchaseSub.setMaterialId(id);
                    purchaseSub.setPurchaseMainId(newId);

                    purchaseSubService.save(purchaseSub);

                }
            }

            // 根据副单生成统计，补充主单数据
            purchaseMain.setTotalAmount(totalAmount);
            purchaseMain.setPrincipalId(principalId);
            purchaseMain.setWarehouseId(warehouseId);
            purchaseMainService.updateById(purchaseMain);

            STATUS = true;
        }


        ResultSet<Object> rs = new ResultSet<>();
        if (STATUS) {
            rs.setReturnCode(1);
            rs.setReturnMessage("操作成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("操作失败");
        }
        return rs;
    }


    /**
     * 删除-根据id
     *
     * @param id
     * @return resultSet
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultSet<Object> delete(@RequestParam(value = "id") int id) {

        ResultSet<Object> rs = new ResultSet<>();
        List<PurchaseSub> subsById = purchaseSubService.getSubsById(id);

        if (purchaseMainService.removeById(id)) {
            // 删除相应副单
            subsById.forEach(i -> {
                purchaseSubService.removeById(i.getId());
            });
            rs.setReturnCode(1);
            rs.setReturnMessage("删除成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("操作失败");
        }
        return rs;
    }

    /**
     * 审核-根据id
     */
    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    public ResultSet<Object> audit(@RequestParam(value = "id") int id,
                                   @RequestParam(value = "principal_id") int auditorId) {
        ResultSet<Object> rs = new ResultSet<>();

        PurchaseMain pm = purchaseMainService.getById(id);
        pm.setStatus(2);
        pm.setWarehousingTime(LocalDateTime.now());


        List<PurchaseSub> subsById = purchaseSubService.getSubsById(id);
        subsById.forEach(i -> {
            StorageRecord record = new StorageRecord();
            record.setStorageId(i.getMaterialId());
            record.setRecordTime(LocalDateTime.now());
            record.setStorageType(0);   //0 物资
            record.setType(0);    // 入库0,出库1
            record.setQuantity(i.getQuantify());
            feignWarehouseService.storage_record(record);

            Financial financial = new Financial();
            financial.setCreationTime(LocalDateTime.now());

            financial.setAuditStatus(0);
            financial.setAuditorId(auditorId);
            financial.setTypeId(1);// 0收入，1支出
            financial.setRemark("采购支出");
            financial.setAmount(pm.getTotalAmount());
            financial.setCreationTime(LocalDateTime.now());
            feignFinancialService.storage_record(financial);


        });

        if (purchaseMainService.updateById(pm)) {
            rs.setReturnCode(1);
            rs.setReturnMessage("审核成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("审核失败");
        }
        return rs;
    }
    //endregion

    /**
     * 查询-单个数据
     *
     * @param id
     * @return data
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public PurchaseMain getOne(@RequestParam(value = "id") long id) {
        return purchaseMainService.getById(id);
    }

    /**
     * 查询-全部列表
     *
     * @return data
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<PurchaseMain> getAll() {
        List<PurchaseMain> list = purchaseMainService.list();
        return list;
    }


    // region 复合数据

    /**
     * 查询-全部列表
     *
     * @return data
     */
    @RequestMapping(value = "/getAllAndSub", method = RequestMethod.GET)
    public ResultGetAllPaging getAllAndSub(@RequestParam(value = "pageIndex", defaultValue = "0")
                                                   long pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "20")
                                                   long pageSize
//            ,
//                                           @RequestParam(value = "startDate", defaultValue = "1971-01-01")
//                                                   String startDate,
//                                           @RequestParam(value = "endDate", defaultValue = "2100-01-01")
//                                                   String endDate,
//                                           @RequestParam(value = "fuzzy", defaultValue = " ")
//                                                   String fuzzy
    ) {
        // 分页
        Page<PurchaseMain> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<PurchaseMain> queryWrapper = new QueryWrapper<>();

        String startDate_Arg = "1971-01-01";
        String endDate_Arg = "2100-01-01";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        queryWrapper.between("purchase_time", LocalDate.parse(startDate_Arg, dtf), LocalDate.parse(endDate_Arg, dtf))
//                .or()
//                .between("warehousing_time", LocalDate.parse(startDate_Arg, dtf), LocalDate.parse(endDate_Arg, dtf))
//                .or()
//                .like("principal_id", fuzzy);
        Iterable<PurchaseMain> list = purchaseMainService.getAllPaging(page, queryWrapper);

        // 组合数据
        List<PurchaseMainAndSub> resultData = new ArrayList<>();
        for (PurchaseMain p : list) {
            PurchaseMainAndSub temp = new PurchaseMainAndSub();
            List<PurchaseSub> subs = purchaseSubService.getSubsById(p.getId());
            temp.purchaseMain = p;
            temp.purchaseSubs = subs;
            resultData.add(temp);
        }

        ResultGetAllPaging result = new ResultGetAllPaging();
        long countTotal = purchaseMainService.count();
        result.setDataList(tansToVO(resultData));
        result.setCountTotal(countTotal);
        result.setCountCurrent(resultData.size());

        return result;
    }

    private List<PurchaseMainAndSubVO> tansToVO(List<PurchaseMainAndSub> params) {
        List<PurchaseMainAndSubVO> vo = new ArrayList<>();

        for (PurchaseMainAndSub i : params) {
            PurchaseMainAndSubVO temp = new PurchaseMainAndSubVO();
            PurchaseMainVO tempMain = new PurchaseMainVO();
            PurchaseMain main = i.purchaseMain;

            tempMain.setId(main.getId());
            tempMain.setPurchaseTime(main.getPurchaseTime());
            tempMain.setWarehousingTime(main.getWarehousingTime());
            tempMain.setTotalAmount(main.getTotalAmount());
            tempMain.setStatus(main.getStatus());

            tempMain.setPrincipalId(feignHrService
                    .getOne(main.getPrincipalId())
                    .getUsername());

            tempMain.setWarehouseId(feignWarehouseService
                    .getOne(main.getWarehouseId())
                    .getName());

            List<PurchaseSubVO> tempSubs = new ArrayList<>();
            List<PurchaseSub> subs = i.purchaseSubs;
            for (PurchaseSub s : subs) {
                PurchaseSubVO tempSub = new PurchaseSubVO();
                tempSub.setPurchaseMainId(s.getPurchaseMainId());
                tempSub.setId(s.getId());
                tempSub.setCostAmount(s.getCostAmount());
                tempSub.setQuantify(s.getQuantify());

                tempSub.setMaterialId(materialService
                        .getById(s.getMaterialId())
                        .getName());
                tempSubs.add(tempSub);
            }

            temp.purchaseSubsVO = tempSubs;
            temp.purchaseMainVO = tempMain;
            vo.add(temp);
        }
        return vo;
    }

    /**
     * 模糊查询
     *
     * @return
     */
    @RequestMapping(value = "/fuzzySearch", method = RequestMethod.GET)
    public ResultGetAllPaging fuzzySearch(@RequestParam(value = "") String searchKey) {
        /*
        TODO
         模糊查询后期完善，需要Feign调用其他微服务
         根据负责人、仓库
         */
        QueryWrapper<PurchaseMain> queryWrapper = new QueryWrapper<>();
        // 条件拼接
//        queryWrapper.like("id", searchKey).or().like("name", searchKey);
        queryWrapper.like("id", searchKey);
        List<PurchaseMain> dataList = purchaseMainService.list(queryWrapper);

        // 组合数据
        List<PurchaseMainAndSub> resultData = new ArrayList<>();
        for (PurchaseMain p : dataList) {
            PurchaseMainAndSub temp = new PurchaseMainAndSub();
            List<PurchaseSub> subs = purchaseSubService.getSubsById(p.getId());
            temp.purchaseMain = p;
            temp.purchaseSubs = subs;
            resultData.add(temp);
        }

        ResultGetAllPaging result = new ResultGetAllPaging();
        long countTotal = purchaseMainService.count();
        result.setDataList(resultData);
        result.setCountTotal(countTotal);
        result.setCountCurrent(resultData.size());
        return result;
    }
    //endregion


    // region 需要使用QueryWrapper

    /**
     * 查询-数据列表-分页
     *
     * @param pageIndex
     * @param pageSize
     * @return data
     */
    @RequestMapping(value = "/getAllPaging", method = RequestMethod.GET)
    public ResultGetAllPaging getAllPaging(@RequestParam(value = "pageIndex", defaultValue = "0")
                                                   long pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "20")
                                                   long pageSize) {

        ResultGetAllPaging result = new ResultGetAllPaging();

        Page<PurchaseMain> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<PurchaseMain> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("price", 312312);

        List<PurchaseMain> dataList = purchaseMainService.getAllPaging(page, queryWrapper);
        long countTotal = purchaseMainService.count();
        result.setDataList(dataList);
        result.setCountTotal(countTotal);
        result.setCountCurrent(dataList.size());
        return result;
    }


    /**
     * @param m_list
     * @return
     */
    @RequestMapping("/insertOrUpdateBatch")
    public ResultSet<Object> insertOrUpdateBatch(@RequestBody List<PurchaseMain> m_list) {

        ResultSet<Object> resultSet = new ResultSet<>();

        if (purchaseMainService.saveOrUpdateBatch(m_list)) {
            resultSet.setReturnCode(1);
            resultSet.setReturnMessage("操作成功");
        } else {
            resultSet.setReturnCode(0);
            resultSet.setReturnMessage("操作失败");
        }

        return resultSet;

    }

    //endregion


}

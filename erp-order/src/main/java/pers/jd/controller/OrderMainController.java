package pers.jd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import pers.jd.entity.OrderMain;
import pers.jd.entity.OrderSub;
import pers.jd.entity.ResultGetAllPaging;
import pers.jd.entity.ResultSet;
import pers.jd.entity.dto.RequestModelOrderMain;
import pers.jd.entity.vo.OrderMainAndSub;
import pers.jd.entity.vo.OrderMainAndSubVO;
import pers.jd.entity.vo.OrderMainVO;
import pers.jd.entity.vo.OrderSubVO;
import pers.jd.feignService.FeignCommodityService;
import pers.jd.feignService.FeignFinancialService;
import pers.jd.feignService.FeignHrService;
import pers.jd.feignService.FeignWarehouseService;
import pers.jd.feignService.dto.Commodity;
import pers.jd.feignService.dto.Financial;
import pers.jd.feignService.dto.StorageRecord;
import pers.jd.service.impl.CustomersServiceImpl;
import pers.jd.service.impl.OrderMainServiceImpl;
import pers.jd.service.impl.OrderSubServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 主订单 前端控制器
 * </p>
 *
 * @author jd
 * @since 2022-03-20
 */
@RestController
@CrossOrigin
@RequestMapping("/order-main")
public class OrderMainController {

    @Resource
    private OrderMainServiceImpl orderMainService;
    @Resource
    private OrderSubServiceImpl orderSubService;
    @Resource
    CustomersServiceImpl customersService;

    @Resource
    FeignFinancialService feignFinancialService;
    @Resource
    FeignWarehouseService feignWarehouseService;


    @Resource
    FeignHrService feignHrService;
    @Resource
    FeignCommodityService feignCommodityService;


    // region
    // 新建订单
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultSet<Object> insert(@RequestBody final List<RequestModelOrderMain> params) {

        boolean STATUS = false;

        if (params != null && params.size() > 0) {
            int principalId = 0;
            int customerId = 0;
            int deliveryType = 0;
            String remark = "";

            // 主单
            OrderMain orderMain = new OrderMain();

            orderMain.setCreateTime(LocalDateTime.now());
            orderMain.setOrderStatus(1);
            orderMain.setPayStatus(0);
            orderMainService.save(orderMain);

            int newId = orderMain.getId();
            // 所需物品
            BigDecimal amount = new BigDecimal(0);
            for (RequestModelOrderMain temp : params) {
                Integer number = temp.number;
                principalId = temp.principalId;
                customerId = temp.customerId;
                remark = temp.remark;
                deliveryType = temp.deliveryType;

                OrderSub orderSub = new OrderSub();

                Commodity commodity = feignCommodityService.getOne(temp.commodityId);
                orderSub.setOrderMainId(newId);
                orderSub.setCommodityId(commodity.getId());
                orderSub.setPrice(commodity.getPrice());
                orderSub.setQuantify(number);
                orderSub.setTotalAmount(commodity.getPrice().multiply(new BigDecimal(number)));

                amount = amount.add(commodity.getPrice().multiply(BigDecimal.valueOf(number)));

                orderSubService.save(orderSub);
                STATUS = true;
            }

            // 根据副单，补充主单数据
            orderMain.setTotalAmount(amount);
            orderMain.setRemark(remark);
            orderMain.setPrincipalId(principalId);
            orderMain.setOrderCustomerId(customerId);
            orderMain.setDeliveryType(deliveryType);
            orderMainService.updateById(orderMain);
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

    // 完成订单
    @RequestMapping(value = "audit", method = RequestMethod.GET)
    public ResultSet<Object> audit(@RequestParam(value = "id") int id) {

        OrderMain order = orderMainService.getById(id);
        order.setPayStatus(1);
        order.setOrderStatus(2);
        order.setFinishTime(LocalDateTime.now());
        order.setPayTime(LocalDateTime.now());

        order.setPayAmount(order.getTotalAmount());
        StorageRecord record = new StorageRecord();

        ArrayList<OrderSub> subs = new ArrayList<>();
        for (OrderSub sub : subs) {
            sub.getCommodityId();
            record.setStorageId(sub.getCommodityId());
            record.setStorageType(1);   // 商品1
            record.setQuantity(sub.getQuantify());
            record.setRecordTime(LocalDateTime.now());
            record.setType(1); // 入库0,出库1
            feignWarehouseService.storage_record(record);
        }

        Financial financial = new Financial();
        financial.setCreationTime(LocalDateTime.now());

        financial.setAuditStatus(0);
        financial.setAuditorId(id);
        financial.setTypeId(1);
        financial.setRemark("销售收入");
        financial.setAmount(order.getTotalAmount());
        financial.setCreationTime(LocalDateTime.now());
        feignFinancialService.storage_record(financial);

        ResultSet<Object> rs = new ResultSet<>();
        if (orderMainService.updateById(order)) {
            rs.setReturnCode(1);
            rs.setReturnMessage("审核成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("审核失败");
        }
        return rs;
    }

    @RequestMapping(value = "/getAllAndSub", method = RequestMethod.GET)
    public ResultGetAllPaging getAllAndSub(@RequestParam(value = "pageIndex", defaultValue = "0")
                                                   long pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "20")
                                                   long pageSize) {

        // 分页
        Page<OrderMain> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        Iterable<OrderMain> list = orderMainService.getAllPaging(page, queryWrapper);

        // 组合数据
        List<OrderMainAndSub> resultData = new ArrayList<>();
        for (OrderMain p : list) {
            OrderMainAndSub temp = new OrderMainAndSub();
            List<OrderSub> subs = orderSubService.getSubsById(p.getId());
            temp.orderMain = p;
            temp.orderSubs = subs;
            resultData.add(temp);
        }

        ResultGetAllPaging result = new ResultGetAllPaging();
        long countTotal = orderMainService.count();
        result.setDataList(transToVO(resultData));
        result.setCountTotal(countTotal);
        result.setCountCurrent(resultData.size());
        return result;
    }

    private List<OrderMainAndSubVO> transToVO(List<OrderMainAndSub> params) {
        List<OrderMainAndSubVO> result = new ArrayList<>();
        for (OrderMainAndSub i : params) {
            OrderMainAndSubVO vo = new OrderMainAndSubVO();

            OrderMainVO tempMain = new OrderMainVO();
            OrderMain main = i.orderMain;
            tempMain.setId(main.getId());
            tempMain.setOrderStatus(main.getOrderStatus());
            tempMain.setDeliveryType(main.getDeliveryType());
            tempMain.setRemark(main.getRemark());
            tempMain.setPayStatus(main.getPayStatus());
            tempMain.setTotalAmount(main.getTotalAmount());
            tempMain.setPayAmount(main.getPayAmount());
            tempMain.setOrderStatus(main.getOrderStatus());
            tempMain.setCreateTime(main.getCreateTime());
            tempMain.setFinishTime(main.getFinishTime());
            tempMain.setPayTime(main.getPayTime());
            tempMain.setOrderCustomerId(customersService.getById(main.getOrderCustomerId()).getName());
            tempMain.setPrincipalId(feignHrService
                    .getOne(main.getPrincipalId())
                    .getUsername());


            List<OrderSub> subs = i.orderSubs;
            List<OrderSubVO> tempSubs = new ArrayList<>();
            for (OrderSub sub : subs) {
                OrderSubVO tempSub = new OrderSubVO();
                tempSub.setId(sub.getId());
                tempSub.setPrice(sub.getPrice());
                tempSub.setTotalAmount(sub.getTotalAmount());
                tempSub.setQuantify(sub.getQuantify());
                tempSub.setCommodityId(feignCommodityService.getOne(sub.getCommodityId()).getName());
                tempSubs.add(tempSub);
            }
            vo.orderMainVO = tempMain;
            vo.orderSubsVO = tempSubs;
            result.add(vo);
        }
        return result;
    }
    // endregion

    // region 基本的增删改查操作

    /**
     * 插入/更新-单个数据
     * 前端必须以json格式传入
     *
     * @param m 若m.id为0就为插入新的数据,反之则为更新相应数据。
     * @return resultSet
     */
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    public ResultSet<Object> insertOrUpdate(@RequestBody OrderMain m) {

        ResultSet<Object> rs = new ResultSet<>();

        if (orderMainService.saveOrUpdate(m)) {
            rs.setReturnCode(1);
            if (m.getId() == 0)
                rs.setReturnMessage("插入成功！");
            else
                rs.setReturnMessage("更新成功！");
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
    public ResultSet<Object> delete(@RequestParam(value = "id") long id) {
        ResultSet<Object> rs = new ResultSet<>();
        if (orderMainService.removeById(id)) {
            rs.setReturnCode(1);
            rs.setReturnMessage("删除成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("操作失败");
        }
        return rs;
    }

    /**
     * 查询-单个数据
     *
     * @param id
     * @return data
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public OrderMain getOne(@RequestParam(value = "id") long id) {
        return orderMainService.getById(id);
    }

    /**
     * 查询-全部列表
     *
     * @return data
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<OrderMain> getAll() {
        return orderMainService.list();
    }

    // endregion


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

        Page<OrderMain> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("price", 312312);

        List<OrderMain> dataList = orderMainService.getAllPaging(page, queryWrapper);
        long countTotal = orderMainService.count();
        result.setDataList(dataList);
        result.setCountTotal(countTotal);
        result.setCountCurrent(dataList.size());
        return result;
    }

    /**
     * 模糊查询
     *
     * @return
     */
    @RequestMapping(value = "/fuzzySearch", method = RequestMethod.GET)
    public ResultGetAllPaging fuzzySearch(@RequestParam(value = "") String searchKey) {
        ResultGetAllPaging result = new ResultGetAllPaging();

        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        // 条件拼接
        queryWrapper.like("id", searchKey).or().like("name", searchKey);
        List<OrderMain> dataList = orderMainService.list(queryWrapper);

        result.setDataList(dataList);
        result.setCountTotal(dataList.size());
        result.setCountCurrent(dataList.size());
        return result;
    }

    /**
     * @param m_list
     * @return
     */
    @RequestMapping("/insertOrUpdateBatch")
    public ResultSet<Object> insertOrUpdateBatch(@RequestBody List<OrderMain> m_list) {

        ResultSet<Object> resultSet = new ResultSet<>();

        if (orderMainService.saveOrUpdateBatch(m_list)) {
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

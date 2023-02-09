package pers.jd.feignService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pers.jd.entity.ResultSet;
import pers.jd.feignService.dto.Commodity;
import pers.jd.feignService.dto.Financial;

@RestController
@FeignClient("ERP-COMMODITY")
public interface FeignCommodityService {

    @GetMapping("commodity/getOne")
    Commodity getOne(@RequestParam(value = "id") long id);
}

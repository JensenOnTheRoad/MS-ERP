package pers.jd.feignService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.jd.feignService.dto.Commodity;

@RestController
@FeignClient("ERP-COMMODITY")
public interface FeignCommodityService {

    @GetMapping("commodity/getOne")
    Commodity getOne(@RequestParam(value = "id") long id);
}

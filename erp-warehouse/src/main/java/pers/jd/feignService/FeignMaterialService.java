package pers.jd.feignService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.jd.feignService.dto.Commodity;
import pers.jd.feignService.dto.Material;

@RestController
@FeignClient("ERP-PRODUCTION")
public interface FeignMaterialService {


    @GetMapping("material/getOne")
    Material getOne(@RequestParam(value = "id") long id);
}

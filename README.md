## 端口规划
```
8888    eureka-server
8990    zuul

8991    admin-manage
8992    erp-production
8993    erp-warehouse
8994    erp-order
8995    erp-commodity
8996    erp-financial
```

数据库 2022/3/20
storage 添加两个字段
    quantity 、 storage_type

production添加一个字段
    quantity

customer 
    companyId字段从long改成String
财务管理 --financial
订单管理 --order
商品管理 --commodity
物流管理 --logistics
仓库管理 --warehouse
生产采购 --production 8991

material,production_main,production_sub,purchase_main,purchase_sub,supply,supply_materails

微服务之间相互调用

https://blog.csdn.net/weixin_41433767/article/details/105675244
https://www.jianshu.com/p/8bca50cb11d8
https://www.cnblogs.com/townsend/p/9531882.html


# Spring Distributed Transaction
提供分应用分库的分布式事务解决案例

涉及技术栈
Spring boot 2 + Feign + Redisson + tkMybatis + Mockito 2

业务流程：
1. 用户购买产品->减去产品库存->生成订单
2. 订单付款->订单完成
3. 订单超时失效/取消->订单失败->回退库存

分布式事务解决方案：
1. 基于TCC模型事务解决方案；
2. 基于MQ的分布式事务解决方案；

单测覆盖率结果:
https://codecov.io/gh/zzqfsy/spring-distributed-transaction

性能压测结果:
1. 下单TPS
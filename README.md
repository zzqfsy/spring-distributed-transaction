# Spring Distributed Transaction
提供分应用分库的分布式事务解决案例

涉及技术栈
Spring boot + Feign + Redisson + tkMybatis

业务流程：
1. 用户购买产品->减去产品库存->生成订单
2. 订单付款->订单完成
3. 订单超时/取消->订单失败->回退库存

分布式事务解决方案：
1. 普通调用TCC模型事务解决方案；
2. 基于MQ的分布式事务解决方案；（待完成）

性能压测
1. 应用TPS
2. 数据库TPS

由于目前工作过忙，该项目暂停更新，如有任何问题请联系zzqfsy@gmail.com
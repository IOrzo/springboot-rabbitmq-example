### RabbitMQ 示例

#### 项目模块简介

rabbitmq-common: 公共模块

rabbitmq-provider: 消息生产者

rabbitmq-consumer: 消息消费者

#### 启动项目

1. 首先需安装RabbitMQ服务端
2. 修改生产者模块和消费者模块中application-local.yml配置文件, 修改MQ基本信息, host, port, username,  password,  virtual-host
3. 启动生产者模块, 启动消费者模块, 然后在生产者模块controller中发送接口测试
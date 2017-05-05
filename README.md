# Finalspeed
finalspeed是一个双边加速端口转发工具(可以用于加速shadowsocks)。
这是一个修改过的控制台版本，去除了GUI，添加了多实例支持，添加了命令行指定配置文件支持，比原版好用

author: xiaozhuai - [xiaozhuai7@gmail.com](xiaozhuai7@gmail.com)

# Usage

## 客户端

```bash
$ java -jar finalspeed_client.jar config.json
```

注意，该版本的finalspeed可以多实例，这意味着可以开启多个客户端来连接不同的服务器
并且每个示例可以配置多个端口转发规则，详情查看 [example_config/config.json](example_config/config.json)

## 服务端

该版本未做服务端的任何改动和优化，推荐使用原版的破解服务端。
详情查看

[http://www.vpsdx.com/912.html](http://www.vpsdx.com/912.html)
# FinalSpeed
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

# 构建
该工程使用idea，使用idea直接打开，构建即可

# 为什么FinalSpeed可以加速
由于访问境外服务器时，主要的网络问题是丢包而不是带宽，当客户端和服务端的带宽都足够时，
网络瓶颈在于丢包后的重传等待。
而这个问题实际上用tcp协议是无法解决的，这由tcp的拥塞算法决定。

而FinalSpeed是基于udp协议，使用自己的拥塞算法，实现了一个可靠的udp协议(rudp)。**（udp协议本身是不可靠的）**
主要优化的是丢包重传的策略。

注意FinalSpeed要手动设置上下行速度，目的是使拥塞算法能够更好的根据实际带宽来调整。
设置的带宽越接近实际情况，加速效果越好。
设置的过小会浪费带宽，过大会造成拥塞。

# 示例配置

```json
{
    "protocal": "udp",
    "server_address": "47.90.52.219",
    "server_port": 150,
    "download_speed": 11915636,
    "upload_speed": 2383127,
    "port_map_list": [
        {
            "dst_port": 8388,
            "listen_port": 2000
        },
        {
            "dst_port": 22,
            "listen_port": 2001
        },
        {
            "dst_port": 80,
            "listen_port": 2002
        }
    ]
}
```

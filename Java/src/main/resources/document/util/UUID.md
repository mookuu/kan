## UUID类

用来生成唯一的标识码，由32位的16进制数字构成，保证在同一时空之中所有机器都是唯一的

### 组成部分

1. 当前的时间和日期
   
2. 时钟序列

3. 全局唯一的IEEE机器识别号码

    1. 若有网卡，从网卡的MAC地址获取
    
    2. 没有网卡，别的方式获取
## EAGAIN
前后端分离的问答平台，目前实现功能:注册，登录，提问，回答，阅读量计数，喜欢(点赞)，通知，发送邮件。

前端使用
* vue
* viewui

后端使用
* spring boot
* spring security
* jwt
* mysql
* mybatis
* redis

### 部署运行

1. 下载代码

``` git clone git@github.com:jerrykcode/eagain.git```

eagain-vue为前端vue.cli项目  
eagain为后端spring boot项目


后端:  
2. 更改 eagain的application.yml中数据库用户名及密码，redis密码.   
   使用邮件功能则需要邮件用户名及smtp授权码  

3. 创建名为eagain的数据库:
```
create database eagain;
use eagain;
```
运行resources/db目录下的schema.sql和data.sql脚本  

4. 在idea中启动eagain

服务在8763端口启动  

前端:  
5. 进入eagain-vue目录  
``` 
npm install # 安装依赖
npm run dev # 启动项目
```

访问 http://localhost:8080/

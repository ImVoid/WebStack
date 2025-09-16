### 使用 docker run -e 方式设置环境变量
支持设置的环境变量有

环境变量名称|环境变量说明|默认值
--|--|--
IMAGE_UPLOAD_PATH|图片上传路径(容器中)|/root/webstack/file
DB_HOST|数据库主机|127.0.0.1
DB_PORT|数据库端口|3306
DB_DATABASE|数据库名称|webstack
DB_USERNAME|数据库用户名|root
DB_PASSWORD|数据库密码|root

示例：
```
docker run -itd \
    -e DB_DATABASE=webstack \
    -e DB_HOST=192.168.211.28 \
    --name webstack \
    -p 8000:8000 \
    webstack
```

### 使用 docker run -v 方式映射配置文件

首先在宿主机创建并修改配置相关文件 /path/to/config/application.yml , 示例文件[application-example.yml](./src/main/resources/application-example.yml)

主要修改：
```
guns.file-upload-path
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

运行容器示例：
```
docker run -itd \
    -v /path/to/config:/root/webstack/config \
    -v /path/to/file=/root/webstack/file \
    --name webstack \
    -p 8000:8000 \
    webstack
```

## 使用

后台地址：http://domain/admin

默认用户：admin

默认密码：111111

## 如何在IDEA进行maven docker 打包推送
1. maven package打包
2. maven docker:build 此时新镜像会推送到服务器
3. 重新运行镜像
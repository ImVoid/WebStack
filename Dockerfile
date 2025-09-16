FROM openjdk:8-alpine as prod
MAINTAINER ken<793743423@qq.com>

ENV WORK_DIR=/run
WORKDIR ${WORK_DIR}

# 设置北京时间
RUN apk add -U tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && apk del tzdata

COPY target/webstack-1.0.jar ${WORK_DIR}/webstack-1.0.jar
COPY docker-entrypoint.sh /usr/local/bin/
# 配置cloudflare-scrape运行环境
RUN chmod 755 /usr/local/bin/docker-entrypoint.sh
ENTRYPOINT ["docker-entrypoint.sh"]
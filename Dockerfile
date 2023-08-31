#基础镜像
FROM openjdk:8-jdk-alpine

#作者信息
LABEL maintainer = "anpeng"

#设置工作目录
WORKDIR /app

#将项目构建输出的jar文件复制到镜像中
COPY target/*.jar app.jar

#设置容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "app.jar"]

#暴露端口
EXPOSE 8080

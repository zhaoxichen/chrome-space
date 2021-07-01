# 基础镜像
FROM java:8
# 作者
MAINTAINER galen<17722515203@163.com>
# 指定了临时文件目录为/tmp。
VOLUME /tmp
# 将spring-tracker-server-0.0.1-SNAPSHOT.jar添加（复制）到容器中并重命名为app.jar
ADD target/spring-tracker-server-0.0.1-SNAPSHOT.jar app.jar
# 运行复制过来的app.jar包
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
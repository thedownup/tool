#指定基础镜像，在其上进行定制
FROM 632443784/future:v1

#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp

#复制上下文目录下的target/demo-1.0.0.jar 到容器里
COPY target/*.jar tool-0.0.1-SNAPSHOT.jar

#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java","-jar","tool-0.0.1-SNAPSHOT.jar"]
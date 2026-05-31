# 1. 使用带 Java 环境的轻量级 Linux 镜像
FROM openjdk:17-jdk-slim

# 2. 把你的 Java 代码文件复制到云端服务器里
COPY ThreeKingdom.java /app/ThreeKingdom.java

# 3. 设置工作目录
WORKDIR /app

# 4. 编译你的 Java 文件
RUN javac ThreeKingdom.java

# 5. 网页服务器默认开启 8080 端口
EXPOSE 8080

# 6. 云端启动运行命令
CMD ["java", "ThreeKingdom"]

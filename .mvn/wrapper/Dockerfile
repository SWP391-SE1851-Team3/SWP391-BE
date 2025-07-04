FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc bên trong container
WORKDIR /app

# Copy đúng tên file .jar (phải trùng tên bạn build ra)
COPY target/School_Medical_Management_System-0.0.1-SNAPSHOT.jar app.jar

# Mở port Spring Boot (thường là 8080)
EXPOSE 8080

# Câu lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]

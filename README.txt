step 1 ) create docker file having set of commands
step 2 ) create docker-compose file having mysql and springboot app
step 3) run maven goal and generate .jar file of spring app.
        mvn clean package or mvn clean package -DskipTests
step 4) build the image using docker compose
        docker-compose build
step 5) start all service using docker-compose
        docker compose up -d
step 6) check container status it should display 3 contains mysql,myphpAdmin and spring
        docker ps
        #docker ps -a   [if certain contains not found in docker ps ]

NOTE :- WE CAN CHECK THE LOGS OF THE CONTAINER TO CHECK WEATHER OUR APPLICATION IS STARTED CORRECTLY OT NOT
        docker logs employee-app --tail 50
        docker logs phpmyadmin --tail 50
        docker logs mysql --tail 50

step 7) test your application [ if all services started ]
        springBoot app running on   : http://localhost:8082/swagger-ui/index.html
        phpmyAdmin DB UI running on : http://localhost:8081   [http://localhost:8081/index.php]

step 8) if you want to stop all service then use
        docker-compose down


==========================
Docker Logs Cheatsheet
==========================

1. View Container Logs

   a) Show all logs of a container:
      docker logs <container_name>

   b) Show last 50 lines:
      docker logs --tail 50 <container_name>

   c) Follow logs live (stream):
      docker logs -f <container_name>
      (Press Ctrl+C to stop)

   d) Show logs with timestamps:
      docker logs -t <container_name>

   e) Combine options (last 50 lines + live + timestamps):
      docker logs -f --tail 50 -t <container_name>

2. Using docker-compose

   a) Show logs for a single service:
      docker-compose logs <service_name>

   b) Follow logs live for a service:
      docker-compose logs -f <service_name>

   c) Follow logs live for all services:
      docker-compose logs -f

   d) Show last 100 lines for a service:
      docker-compose logs --tail=100 <service_name>

3. Inspect Logs Inside Container (if logs are written to files)

   a) Access the container shell:
      docker exec -it <container_name> sh

   b) Navigate to log directory (example):
      cd /app/logs

   c) View logs using:
      cat application.log
      tail -f application.log

4. Mount Logs to Host (persistent logging)

   a) Add volume mapping in docker-compose.yml or docker run:
      volumes:
        - ./logs:/app/logs

   b) Then access logs directly on host machine:
      tail -f ./logs/application.log

5. Advanced Logging Drivers

   a) Use syslog or other drivers:
      docker run --log-driver=syslog <image>

   b) Configure in docker-compose.yml:
      logging:
        driver: "json-file"
        options:
          max-size: "10m"
          max-file: "3"

6. Third-party Tools & Services

   - Portainer, Grafana Loki, ELK Stack (Elasticsearch, Logstash, Kibana)
   - Cloud services: AWS CloudWatch, Datadog, Splunk

---

Remember: Always check if the container is running using:

   docker ps

If your container is stopped or exited, logs might give clues why:

   docker ps -a

---

Happy Debugging! 🚀

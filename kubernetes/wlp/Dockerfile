FROM websphere-liberty:19.0.0.6-webProfile8
COPY --chown=1001:0  spring.war /config/dropins/
COPY --chown=1001:0  server.xml /config/
COPY lib/mysql/mysql-connector-java-8.0.12.jar /config/resources/mysql/
RUN installUtility install --acceptLicense defaultServer
ENV LICENSE accept

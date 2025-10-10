# ======================================
# Dockerfile pour Clinic JEE Application
# ======================================
FROM tomcat:10.1-jdk17-temurin-jammy

# Supprimer les webapps par défaut de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copier le fichier WAR et le déployer comme ROOT
COPY target/clinic-jee-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Exposer le port 8080
EXPOSE 8080

# Démarrer Tomcat avec logs verbeux
CMD ["catalina.sh", "run"]

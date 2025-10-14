# Makefile pour Clinic JEE
# Usage: make [target]

.PHONY: all build rebuild-image deploy clean run stop restart logs help

# Couleurs pour l'affichage
GREEN=\033[0;32m
YELLOW=\033[1;33m
RED=\033[0;31m
NC=\033[0m # No Color

# Variables
PROJECT_NAME=clinic-jee
WAR_FILE=target/$(PROJECT_NAME)-1.0-SNAPSHOT.war
TOMCAT_CONTAINER=clinic-jee-tomcat-1

# Cible par défaut: déploiement rapide optimisé
all: quick-deploy
	@echo "$(GREEN)✓ Déploiement rapide terminé!$(NC)"

# DÉPLOIEMENT RAPIDE OPTIMISÉ - Pour le développement quotidien
# Compile le projet et redémarre Tomcat (le volume monte automatiquement le nouveau WAR)
quick-deploy: build
	@echo "$(YELLOW)⚡ Redémarrage rapide de Tomcat...$(NC)"
	@echo "$(YELLOW)   Le nouveau WAR sera automatiquement chargé via le volume Docker$(NC)"
	docker compose restart tomcat
	@echo "$(GREEN)✓ Redémarrage terminé - Attendez ~5 secondes pour le rechargement$(NC)"
	@echo "$(GREEN)🌐 Application disponible sur: http://localhost:8080/$(NC)"

# PREMIER DÉPLOIEMENT - À utiliser la première fois ou après changement du Dockerfile
first-deploy: build
	@echo "$(YELLOW)🚀 Premier déploiement complet...$(NC)"
	docker compose down
	docker compose up -d
	@echo "$(GREEN)✓ Déploiement complet terminé$(NC)"
	@echo "$(GREEN)🌐 Application disponible sur: http://localhost:8080/$(NC)"

# DÉPLOIEMENT COMPLET - Pour les changements majeurs (Dockerfile, dependencies, etc.)
full-deploy: build rebuild-image deploy
	@echo "$(GREEN)✓ Déploiement complet avec rebuild terminé!$(NC)"

# Compilation du projet avec Maven
build:
	@echo "$(YELLOW)📦 Compilation du projet...$(NC)"
	./mvnw clean package -DskipTests
	@echo "$(GREEN)✓ Compilation terminée$(NC)"

# Reconstruction de l'image Docker sans cache
rebuild-image:
	@echo "$(YELLOW)🔨 Reconstruction de l'image Docker (sans cache)...$(NC)"
	docker compose build --no-cache tomcat
	@echo "$(GREEN)✓ Image Docker reconstruite$(NC)"

# Déploiement (redémarrage des conteneurs Docker)
deploy:
	@echo "$(YELLOW)🚀 Déploiement de l'application...$(NC)"
	docker compose down
	docker compose up -d
	@echo "$(GREEN)✓ Application déployée$(NC)"
	@echo "$(GREEN)🌐 Application disponible sur: http://localhost:8080/$(NC)"

# Redémarrage uniquement Tomcat (sans recompilation)
restart-tomcat:
	@echo "$(YELLOW)🔄 Redémarrage de Tomcat uniquement...$(NC)"
	docker compose restart tomcat
	@echo "$(GREEN)✓ Tomcat redémarré$(NC)"

# Compilation et déploiement avec logs en temps réel
dev: build deploy logs

# Arrêter les conteneurs
stop:
	@echo "$(YELLOW)⏸️  Arrêt des conteneurs...$(NC)"
	docker compose down
	@echo "$(GREEN)✓ Conteneurs arrêtés$(NC)"

# Démarrer les conteneurs sans rebuild
start:
	@echo "$(YELLOW)▶️  Démarrage des conteneurs...$(NC)"
	docker compose up -d
	@echo "$(GREEN)✓ Conteneurs démarrés$(NC)"

# Redémarrer les conteneurs
restart:
	@echo "$(YELLOW)🔄 Redémarrage des conteneurs...$(NC)"
	docker compose restart
	@echo "$(GREEN)✓ Conteneurs redémarrés$(NC)"

# Nettoyer les fichiers compilés
clean:
	@echo "$(YELLOW)🧹 Nettoyage des fichiers compilés...$(NC)"
	./mvnw clean
	@echo "$(GREEN)✓ Nettoyage terminé$(NC)"

# Nettoyage complet (y compris Docker)
clean-all: clean
	@echo "$(YELLOW)🧹 Nettoyage complet (Docker inclus)...$(NC)"
	docker compose down -v
	@echo "$(GREEN)✓ Nettoyage complet terminé$(NC)"

# Afficher les logs en temps réel
logs:
	@echo "$(YELLOW)📋 Affichage des logs (Ctrl+C pour quitter)...$(NC)"
	docker compose logs -f tomcat

# Afficher les logs de la base de données
logs-db:
	@echo "$(YELLOW)📋 Affichage des logs PostgreSQL...$(NC)"
	docker compose logs -f postgres

# Vérifier le statut des conteneurs
status:
	@echo "$(YELLOW)📊 Statut des conteneurs:$(NC)"
	@docker compose ps

# Compilation avec tests
test: clean
	@echo "$(YELLOW)🧪 Compilation avec tests...$(NC)"
	./mvnw clean package
	@echo "$(GREEN)✓ Tests terminés$(NC)"

# Ouvrir un shell dans le conteneur Tomcat
shell:
	@echo "$(YELLOW)🐚 Ouverture du shell Tomcat...$(NC)"
	docker exec -it $(TOMCAT_CONTAINER) bash

# Ouvrir un shell dans le conteneur PostgreSQL
shell-db:
	@echo "$(YELLOW)🐚 Ouverture du shell PostgreSQL...$(NC)"
	docker exec -it clinic-jee-postgres-1 psql -U clinicuser -d clinicdb

# Rebuild complet (supprime images et reconstruit)
rebuild: clean-all
	@echo "$(YELLOW)🔨 Rebuild complet...$(NC)"
	docker compose build --no-cache
	@echo "$(GREEN)✓ Rebuild terminé$(NC)"

# Afficher l'aide
help:
	@echo "$(GREEN)═══════════════════════════════════════════════════════════$(NC)"
	@echo "$(GREEN)        Makefile Clinic JEE - Commandes disponibles        $(NC)"
	@echo "$(GREEN)═══════════════════════════════════════════════════════════$(NC)"
	@echo ""
	@echo "$(YELLOW)🚀 DÉVELOPPEMENT (utilisation quotidienne):$(NC)"
	@echo "  make                  - Compile + redémarre Tomcat (~5 secondes)"
	@echo "  make quick-deploy     - Même chose (alias)"
	@echo "  make restart-tomcat   - Redémarre seulement Tomcat (sans recompilation)"
	@echo ""
	@echo "$(YELLOW)🔨 PREMIER DÉPLOIEMENT:$(NC)"
	@echo "  make first-deploy     - Premier déploiement (démarre tous les conteneurs)"
	@echo ""
	@echo "$(YELLOW)🏗️  DÉPLOIEMENT COMPLET (changements majeurs):$(NC)"
	@echo "  make full-deploy      - Rebuild Docker complet + tous les conteneurs"
	@echo "  make rebuild-image    - Reconstruit l'image Docker sans cache"
	@echo ""
	@echo "$(YELLOW)📦 Compilation:$(NC)"
	@echo "  make build            - Compile le projet avec Maven (sans tests)"
	@echo "  make test             - Compile avec tests"
	@echo ""
	@echo "$(YELLOW)🐳 Gestion des conteneurs:$(NC)"
	@echo "  make start            - Démarre les conteneurs"
	@echo "  make stop             - Arrête les conteneurs"
	@echo "  make restart          - Redémarre tous les conteneurs"
	@echo "  make status           - Affiche le statut des conteneurs"
	@echo ""
	@echo "$(YELLOW)📋 Logs et debugging:$(NC)"
	@echo "  make logs             - Affiche les logs Tomcat en temps réel"
	@echo "  make logs-db          - Affiche les logs PostgreSQL"
	@echo "  make shell            - Ouvre un shell dans Tomcat"
	@echo "  make shell-db     - Ouvre un shell PostgreSQL"
	@echo ""
	@echo "$(YELLOW)Maintenance:$(NC)"
	@echo "  make clean        - Nettoie les fichiers compilés"
	@echo "  make clean-all    - Nettoyage complet (Docker inclus)"
	@echo "  make rebuild      - Rebuild complet de l'application"
	@echo "  make test         - Compile avec tests"
	@echo ""
	@echo "$(GREEN)═══════════════════════════════════════════════════════════$(NC)"
	@echo "$(GREEN)🌐 Application: http://localhost:8080/$(NC)"
	@echo "$(GREEN)🗄️  pgAdmin: http://localhost:5051$(NC)"
	@echo "$(GREEN)═══════════════════════════════════════════════════════════$(NC)"

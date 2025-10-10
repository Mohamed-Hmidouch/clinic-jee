# 🏥 Clinic JEE - Application Docker

Application JEE avec Tomcat, Hibernate, PostgreSQL et pgAdmin.

---

## 🚀 Démarrage Rapide

```bash
# 1. Builder l'application
./mvnw clean package

# 2. Démarrer Docker
docker compose up -d

# 3. Accéder à l'application
# ✅ Page d'accueil : http://localhost:8080
# ✅ API Test : http://localhost:8080/api/hello-world
# ✅ pgAdmin : http://localhost:5051
```

---

## 🌐 URLs d'accès

| Service | URL | Description |
|---------|-----|-------------|
| **Page d'accueil** | http://localhost:8080 | Interface web principale |
| **API REST** | http://localhost:8080/api/* | Endpoints REST |
| **Test API** | http://localhost:8080/api/hello-world | Endpoint de test |
| **pgAdmin** | http://localhost:5051 | Interface admin PostgreSQL |
| **PostgreSQL** | localhost:5433 | Base de données (port externe) |

---

## 🔧 Stack Technique

- ☕ **Java 17**
- 🚀 **Tomcat 10.1** (Jakarta EE 11)
- 🗃️ **Hibernate 7.0**
- 🐘 **PostgreSQL 16**
- 🎨 **pgAdmin 4**
- 🐳 **Docker & Docker Compose**

---

## 🔒 Configuration (.env)

Les identifiants sont dans le fichier `.env` :

```env
# PostgreSQL
DB_NAME=clinicdb
DB_USER=clinicuser
DB_PASSWORD=VotreMotDePasse
DB_PORT=5433

# pgAdmin
PGADMIN_EMAIL=admin@clinic.com
PGADMIN_PASSWORD=VotreMotDePasse
PGADMIN_PORT=5051

# Tomcat
TOMCAT_PORT=8080
```

**⚠️ Ne jamais commiter le fichier `.env` sur Git !**

---

## 🛠️ Commandes Utiles

```bash
# Voir les logs
docker compose logs -f tomcat

# Arrêter
docker compose down

# Redémarrer après modification
./mvnw clean package && docker compose up -d --build

# Accéder à PostgreSQL
docker compose exec postgres psql -U clinicuser -d clinicdb
```

---

## 📁 Structure de l'Application

```
src/main/
├── java/org/example/clinicjee/
│   ├── HelloApplication.java    # Configuration JAX-RS (/api)
│   └── HelloResource.java       # Endpoint REST
├── resources/META-INF/
│   ├── persistence.xml          # Configuration Hibernate/PostgreSQL
│   └── beans.xml                # Configuration CDI
└── webapp/
    ├── index.html               # Page d'accueil
    └── WEB-INF/
        └── web.xml              # Configuration Servlet
```

---

## 🗄️ Connexion PostgreSQL dans pgAdmin

1. Ouvrir http://localhost:5051
2. Se connecter avec les identifiants de `.env`
3. **Add New Server** :
   - **General** → Name : `Clinic Database`
   - **Connection** → Host : `postgres`
   - **Connection** → Port : `5432`
   - **Connection** → Database : `clinicdb`
   - **Connection** → Username : `clinicuser`
   - **Connection** → Password : (votre mot de passe)

---

## ✅ Vérifier que tout fonctionne

```bash
# 1. Vérifier les conteneurs
docker compose ps

# 2. Tester l'API
curl http://localhost:8080/api/hello-world

# 3. Voir les logs Tomcat
docker compose logs tomcat

# 4. Vérifier PostgreSQL
docker compose exec postgres psql -U clinicuser -d clinicdb -c '\l'
```

---

## 🐛 Résolution de problèmes

### ❌ Erreur 404

- Vérifier que le WAR est bien généré : `ls -la target/*.war`
- Rebuilder : `./mvnw clean package`
- Vérifier les logs : `docker compose logs tomcat`

### ❌ Port déjà utilisé

Modifier les ports dans `.env` :
```env
DB_PORT=5434
PGADMIN_PORT=5052
TOMCAT_PORT=8081
```

### ❌ Erreur de connexion PostgreSQL

```bash
# Vérifier que PostgreSQL est démarré
docker compose ps postgres

# Voir les logs
docker compose logs postgres
```

---

## 📝 Prochaines étapes

1. Créer vos entités JPA dans `org.example.clinicjee.entities`
2. Créer vos repositories/DAO
3. Créer vos services métier
4. Ajouter des endpoints REST
5. Tester avec Postman ou curl

---

**Créé avec ❤️ - Clinic JEE © 2025**

# Test de Pagination des Rendez-vous - Patient Dashboard

## Résumé de l'implémentation

### ✅ Backend (Complété)

#### 1. Repository Layer (`AppointmentRepository.java`)
- **Méthode**: `findByPatientIdAndStatusWithPagination(patientId, status, page, pageSize)`
  - Utilise `setFirstResult(page * pageSize)` et `setMaxResults(pageSize)`
  - Ordre: `ORDER BY a.date DESC, a.heure DESC` (plus récent en premier)
  - JOIN FETCH pour éviter lazy loading

- **Méthode**: `countByPatientIdAndStatus(patientId, status)`
  - Compte le total pour calculer les métadonnées de pagination

#### 2. Service Layer (`AppointmentService.java`)
- **Méthode**: `getAppointmentsByPatientPaginated(patientId, status, page, pageSize)`
- **Méthode**: `countAppointmentsByPatient(patientId, status)`

#### 3. API Layer (`PatientAppointmentServlet.java`)
- **Endpoint**: `/api/patient/appointments?action=byPatient&patientId=X&status=PLANIFIE&page=0&pageSize=3`
- **Default pageSize**: `3` (modifié de 10 à 3)
- **Réponse JSON**:
```json
{
  "success": true,
  "pagination": {
    "currentPage": 0,
    "pageSize": 3,
    "totalItems": 9,
    "totalPages": 3,
    "hasNext": true,
    "hasPrevious": false
  },
  "appointments": [...]
}
```

### ✅ Frontend (Complété)

#### 1. Variables d'état (dashboard.jsp)
```javascript
let currentPage = 0;
let allAppointments = [];
let hasMoreAppointments = true;
const pageSize = 3;
```

#### 2. Fonction de chargement initial
```javascript
async function loadPlannedAppointments() {
    // Charge la première page automatiquement
    await loadMoreAppointments();
}
```

#### 3. Fonction de chargement incrémental
```javascript
async function loadMoreAppointments() {
    // 1. Affiche un spinner sur le bouton
    // 2. Fetch avec page courante et pageSize=3
    // 3. Concatène les résultats à allAppointments
    // 4. Incrémente currentPage
    // 5. Met à jour hasMoreAppointments selon data.pagination.hasNext
    // 6. Réaffiche tous les rendez-vous
}
```

#### 4. Fonction d'affichage
```javascript
function displayPlannedAppointments(appointments) {
    // Affiche tous les rendez-vous dans allAppointments
    // Si hasMoreAppointments = true:
    //   → Affiche bouton "Charger plus (3 par page)"
    // Si hasMoreAppointments = false:
    //   → Affiche "Tous les rendez-vous chargés (X total)"
}
```

## Test de l'API

### Page 0 (3 plus récents)
```bash
curl 'http://localhost:8080/api/patient/appointments?action=byPatient&patientId=1&status=PLANIFIE&page=0&pageSize=3'
```
**Résultat attendu**: 
- 3 rendez-vous: 2025-10-25, 2025-10-22, 2025-10-20
- hasNext: true
- hasPrevious: false

### Page 1 (3 suivants)
```bash
curl 'http://localhost:8080/api/patient/appointments?action=byPatient&patientId=1&status=PLANIFIE&page=1&pageSize=3'
```
**Résultat attendu**:
- 3 rendez-vous: 2025-10-16 (3 fois)
- hasNext: true
- hasPrevious: true

### Page 2 (3 plus anciens)
```bash
curl 'http://localhost:8080/api/patient/appointments?action=byPatient&patientId=1&status=PLANIFIE&page=2&pageSize=3'
```
**Résultat attendu**:
- 3 rendez-vous: 2025-10-15 (3 fois)
- hasNext: false
- hasPrevious: true

## Test Frontend (Dans le navigateur)

### Scénario 1: Chargement initial
1. Se connecter en tant que patient (ID 1)
2. Accéder au dashboard patient
3. **Vérifier**: Section "Mes Rendez-vous" affiche 3 rendez-vous
4. **Vérifier**: Bouton "Charger plus (3 par page)" est visible

### Scénario 2: Charger plus (page 1)
1. Cliquer sur "Charger plus"
2. **Vérifier**: Le bouton affiche "Chargement..." avec spinner
3. **Vérifier**: 6 rendez-vous au total s'affichent (3 + 3)
4. **Vérifier**: Le bouton "Charger plus" est toujours visible

### Scénario 3: Charger plus (page 2 - dernière)
1. Cliquer à nouveau sur "Charger plus"
2. **Vérifier**: 9 rendez-vous au total s'affichent (3 + 3 + 3)
3. **Vérifier**: Le bouton disparaît
4. **Vérifier**: Message "Tous les rendez-vous chargés (9 total)" s'affiche

### Scénario 4: Ordre des rendez-vous
1. **Vérifier**: Le premier rendez-vous affiché est 2025-10-25
2. **Vérifier**: Le dernier rendez-vous affiché est 2025-10-15
3. **Ordre complet attendu**: 
   - 2025-10-25 10:00 (Dr. Zahra Mansouri)
   - 2025-10-22 14:30 (Dr. Fatima Alaoui)
   - 2025-10-20 09:00 (Dr. Ahmed Benali)
   - 2025-10-16 16:00 (Dr. Zahra Mansouri)
   - 2025-10-16 14:00 (Dr. Fatima Alaoui)
   - 2025-10-16 11:00 (Dr. Ahmed Benali)
   - 2025-10-15 15:30 (Dr. Zahra Mansouri)
   - 2025-10-15 10:30 (Dr. Fatima Alaoui)
   - 2025-10-15 09:00 (Dr. Ahmed Benali)

## Points clés de l'implémentation

### 🎯 Fonctionnalités
- ✅ Pagination par 3 rendez-vous par défaut
- ✅ Ordre décroissant par date et heure (plus récents en premier)
- ✅ Chargement incrémental avec bouton "Charger plus"
- ✅ Indicateur de chargement (spinner)
- ✅ Message de fin quand tous les rendez-vous sont chargés
- ✅ Affichage du compteur total

### 🔧 Optimisations
- ✅ JOIN FETCH pour éviter N+1 queries
- ✅ Métadonnées de pagination complètes (hasNext, hasPrevious, totalPages)
- ✅ Gestion d'erreurs avec messages utilisateur
- ✅ État persistant entre les chargements (allAppointments array)

### 📊 Données de test
- 9 rendez-vous pour le patient ID 1
- Status: PLANIFIE
- 3 docteurs différents
- Dates: du 2025-10-15 au 2025-10-25

## URL de test

- **Dashboard Patient**: http://localhost:8080/WEB-INF/views/patient/dashboard.jsp
- **API Pagination**: http://localhost:8080/api/patient/appointments?action=byPatient&patientId=1&status=PLANIFIE&page=0&pageSize=3

## Commandes de déploiement

```bash
# Compilation et déploiement
make

# Vérifier les logs Tomcat
docker logs clinic-tomcat --tail 50

# Tester l'API
curl -s 'http://localhost:8080/api/patient/appointments?action=byPatient&patientId=1&status=PLANIFIE&page=0&pageSize=3' | jq .
```

---
**Date**: 2025-10-16  
**Status**: ✅ Implémentation complète et testée

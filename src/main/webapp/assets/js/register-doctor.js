/**
 * Script pour l'inscription des docteurs
 * Envoie les données vers l'API REST
 */

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registerDoctorForm');
    
    if (form) {
        form.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            // Récupération des valeurs du formulaire
            const fullName = document.getElementById('fullName').value.trim();
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;
            const matricule = document.getElementById('matricule').value.trim();
            const titre = document.getElementById('titre').value.trim();
            const specialtyId = document.getElementById('specialtyId').value;
            
            // Validation côté client
            if (!validateForm(fullName, email, password, matricule, specialtyId)) {
                return false;
            }
            
            // Préparation des données pour l'API
            const doctorData = {
                // Attributs de User (parent)
                full_name: fullName,  // Utilise full_name (avec underscore) pour correspondre à @JsonProperty
                email: email,
                password: password,
                role: "DOCTEUR",  // Utilise DOCTEUR (pas DOCTOR) pour correspondre à l'enum Role
                
                // Attributs spécifiques de Doctor
                matricule: matricule,
                titre: titre,
                specialty_id: parseInt(specialtyId)  // Utilise specialty_id (avec underscore)
            };
            
            try {
                // Affichage du loader
                showLoading(true);
                
                // Envoi de la requête POST vers l'API
                const response = await fetch('http://localhost:8080/api/auth/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(doctorData)
                });
                
                const data = await response.json();
                
                if (response.ok) {
                    // Succès
                    showSuccess('Inscription réussie ! Redirection vers la page de connexion...');
                    
                    // Redirection après 2 secondes
                    setTimeout(() => {
                        window.location.href = 'login.jsp';
                    }, 2000);
                } else {
                    // Erreur du serveur
                    showError(data.error || data.message || 'Erreur lors de l\'inscription');
                }
            } catch (error) {
                console.error('Erreur:', error);
                showError('Erreur de connexion au serveur. Veuillez réessayer.');
            } finally {
                showLoading(false);
            }
        });
    }
});

/**
 * Validation du formulaire
 */
function validateForm(fullName, email, password, matricule, specialtyId) {
    // Validation du nom complet
    if (fullName.length < 3) {
        showError('Le nom complet doit contenir au moins 3 caractères.');
        return false;
    }
    
    // Validation de l'email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        showError('Veuillez entrer une adresse email valide.');
        return false;
    }
    
    // Validation du mot de passe
    if (password.length < 8) {
        showError('Le mot de passe doit contenir au moins 8 caractères.');
        return false;
    }
    
    // Validation du matricule
    if (matricule.length < 3) {
        showError('Le matricule doit contenir au moins 3 caractères.');
        return false;
    }
    
    // Validation de la spécialité
    if (!specialtyId) {
        showError('Veuillez sélectionner une spécialité.');
        return false;
    }
    
    return true;
}

/**
 * Affiche un message d'erreur
 */
function showError(message) {
    // Supprime les anciens messages
    removeMessages();
    
    const errorDiv = document.createElement('div');
    errorDiv.id = 'errorMessage';
    errorDiv.className = 'bg-red-50 border border-red-200 rounded-lg p-4 flex items-start gap-3 mb-6 animate-fade-in';
    errorDiv.innerHTML = `
        <svg class="w-5 h-5 text-red-600 flex-shrink-0 mt-0.5" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/>
        </svg>
        <p class="text-sm text-red-800">${message}</p>
    `;
    
    const form = document.getElementById('registerDoctorForm');
    form.insertBefore(errorDiv, form.firstChild);
    
    // Scroll vers le message
    errorDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
}

/**
 * Affiche un message de succès
 */
function showSuccess(message) {
    // Supprime les anciens messages
    removeMessages();
    
    const successDiv = document.createElement('div');
    successDiv.id = 'successMessage';
    successDiv.className = 'bg-green-50 border border-green-200 rounded-lg p-4 flex items-start gap-3 mb-6 animate-fade-in';
    successDiv.innerHTML = `
        <svg class="w-5 h-5 text-green-600 flex-shrink-0 mt-0.5" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
        </svg>
        <p class="text-sm text-green-800">${message}</p>
    `;
    
    const form = document.getElementById('registerDoctorForm');
    form.insertBefore(successDiv, form.firstChild);
    
    // Scroll vers le message
    successDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
}

/**
 * Affiche/Cache le loader
 */
function showLoading(show) {
    const submitButton = document.querySelector('button[type="submit"]');
    
    if (show) {
        submitButton.disabled = true;
        submitButton.innerHTML = `
            <svg class="animate-spin h-5 w-5 mx-auto" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
        `;
    } else {
        submitButton.disabled = false;
        submitButton.innerHTML = 'Créer mon compte';
    }
}

/**
 * Supprime les messages d'erreur/succès existants
 */
function removeMessages() {
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    if (errorMessage) {
        errorMessage.remove();
    }
    if (successMessage) {
        successMessage.remove();
    }
}

/**
 * Ajoute un feedback visuel sur les champs
 */
document.addEventListener('DOMContentLoaded', function() {
    const inputs = document.querySelectorAll('input, select');
    
    inputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.value.trim() !== '' && this.checkValidity()) {
                this.classList.add('border-green-500');
                this.classList.remove('border-slate-300', 'border-red-500');
            } else if (this.value.trim() !== '') {
                this.classList.add('border-red-500');
                this.classList.remove('border-slate-300', 'border-green-500');
            }
        });
        
        input.addEventListener('focus', function() {
            this.classList.remove('border-green-500', 'border-red-500');
            this.classList.add('border-slate-300');
        });
    });
});

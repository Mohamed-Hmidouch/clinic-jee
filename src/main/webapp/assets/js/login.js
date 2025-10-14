/**
 * Script pour la connexion des utilisateurs (tous les rôles)
 * Envoie les données vers l'API REST
 */

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('loginForm');
    const togglePassword = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');
    const toggleIcon = document.getElementById('toggleIcon');
    
    // Gestion de l'affichage du mot de passe
    if (togglePassword) {
        togglePassword.addEventListener('click', function() {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            
            // Change l'icône
            if (type === 'password') {
                toggleIcon.classList.remove('fa-eye-slash');
                toggleIcon.classList.add('fa-eye');
            } else {
                toggleIcon.classList.remove('fa-eye');
                toggleIcon.classList.add('fa-eye-slash');
            }
        });
    }
    
    // Gestion de la soumission du formulaire
    if (form) {
        form.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            // Récupération des valeurs du formulaire
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;
            const remember = document.getElementById('remember').checked;
            
            // Validation côté client
            if (!validateForm(email, password)) {
                return false;
            }
            
            // Préparation des données pour l'API
            const loginData = {
                email: email,
                password: password
            };
            
            try {
                // Affichage du loader
                showLoading(true);
                
                // Envoi de la requête POST vers l'API
                const response = await fetch('http://localhost:8080/api/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(loginData)
                });
                
                const data = await response.json();

                
                if (response.ok) {
                    // La réponse de l'API est: {success: true, data: {token, userId, fullName, email, role, expiresIn}}
                    const authData = data.data || data;
                    console.log('authData:', authData);
                    console.log('authData.token:', authData.token);
                    
                    // Succès - Stockage du token JWT
                    if (authData.token) {
                        // Stockage dans sessionStorage ou localStorage selon "Se souvenir de moi"
                        const storage = remember ? localStorage : sessionStorage;
                        storage.setItem('authToken', authData.token);
                        
                        // Stockage des informations utilisateur
                        const userInfo = {
                            userId: authData.userId || authData.user_id,
                            fullName: authData.fullName || authData.full_name,
                            email: authData.email,
                            role: authData.role
                        };
                        storage.setItem('userInfo', JSON.stringify(userInfo));
                        
                        showSuccess('Connexion réussie ! Redirection en cours...');

                        // Déterminer le rôle de façon robuste (peut être string, objet, ou absent)
                        let roleStr = authData.role;
                        if (!roleStr && authData.role_name) roleStr = authData.role_name;
                        if (!roleStr && authData.role && typeof authData.role === 'object' && authData.role.name) roleStr = authData.role.name;
                        if (!roleStr && authData.userInfo && authData.userInfo.role) roleStr = authData.userInfo.role;
                        if (!roleStr && authData.token) {
                            try {
                                const payload = JSON.parse(atob(authData.token.split('.')[1]));
                                if (payload && payload.role) roleStr = payload.role;
                            } catch (e) {
                                console.warn('Impossible de décoder le token pour extraire le rôle', e);
                            }
                        }

                        if (roleStr && typeof roleStr === 'string') roleStr = roleStr.toUpperCase();

                        setTimeout(() => {
                            redirectByRole(roleStr);
                        }, 800);
                    } else {
                        showError('Erreur: Token manquant dans la réponse');
                    }
                } else {
                    // Erreur du serveur
                    const errorMessage = data.error || data.message || 'Email ou mot de passe incorrect';
                    showError(errorMessage);
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
function validateForm(email, password) {
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
    
    return true;
}

/**
 * Redirection selon le rôle de l'utilisateur
 */
function redirectByRole(role) {
    const baseUrl = window.location.origin;
    
    switch (role?.toUpperCase()) {
        case 'PATIENT':
            window.location.href = `${baseUrl}/patient/dashboard`;
            break;
        case 'DOCTEUR':
        case 'DOCTOR':
            window.location.href = `${baseUrl}/doctor/dashboard`;
            break;
        case 'STAFF':
            window.location.href = `${baseUrl}/staff/dashboard`;
            break;
        case 'ADMIN':
            window.location.href = `${baseUrl}/admin/dashboard`;
            break;
        default:
            // Par défaut, redirection vers la racine de l'application
            window.location.href = `${baseUrl}/`;
    }
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
    
    const form = document.getElementById('loginForm');
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
    
    const form = document.getElementById('loginForm');
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
        submitButton.innerHTML = 'Se connecter';
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
    const inputs = document.querySelectorAll('input[type="email"], input[type="password"]');
    
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

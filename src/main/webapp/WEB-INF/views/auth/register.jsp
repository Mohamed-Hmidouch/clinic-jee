<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription Patient - Clinic JEE</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');
        
        body {
            font-family: 'Inter', sans-serif;
        }
        
        .input-focus:focus {
            outline: none;
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }
    </style>
</head>
<body class="bg-gradient-to-br from-slate-50 to-blue-50 min-h-screen flex items-center justify-center p-4">
    
    <div class="w-full max-w-md">
        <!-- Header Section -->
        <div class="text-center mb-8">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-blue-600 rounded-2xl mb-4 shadow-lg">
                <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                </svg>
            </div>
            <h1 class="text-3xl font-bold text-slate-900 mb-2">Créer un compte</h1>
            <p class="text-slate-600">Inscrivez-vous pour prendre rendez-vous</p>
        </div>

        <!-- Main Form Card -->
        <div class="bg-white rounded-2xl shadow-xl p-8 border border-slate-200">
            <form id="registerPatientForm" class="space-y-6">
                
                <!-- Full Name Field -->
                <div>
                    <label for="fullName" class="block text-sm font-semibold text-slate-700 mb-2">
                        Nom Complet <span class="text-red-500">*</span>
                    </label>
                    <input 
                        type="text" 
                        id="fullName" 
                        name="fullName" 
                        required
                        placeholder="Mohamed Hmidouch"
                        class="w-full px-4 py-3 border border-slate-300 rounded-lg text-slate-900 placeholder-slate-400 input-focus transition-all duration-200"
                    >
                </div>

                <!-- CIN Field -->
                <div>
                    <label for="cin" class="block text-sm font-semibold text-slate-700 mb-2">
                        CIN <span class="text-red-500">*</span>
                    </label>
                    <input 
                        type="text" 
                        id="cin" 
                        name="cin" 
                        required
                        placeholder="AB123456"
                        class="w-full px-4 py-3 border border-slate-300 rounded-lg text-slate-900 placeholder-slate-400 input-focus transition-all duration-200"
                    >
                </div>

                <!-- Date de Naissance Field -->
                <div>
                    <label for="naissance" class="block text-sm font-semibold text-slate-700 mb-2">
                        Date de Naissance <span class="text-red-500">*</span>
                    </label>
                    <input 
                        type="date" 
                        id="naissance" 
                        name="naissance" 
                        required
                        class="w-full px-4 py-3 border border-slate-300 rounded-lg text-slate-900 placeholder-slate-400 input-focus transition-all duration-200"
                    >
                </div>

                <!-- Email Field -->
                <div>
                    <label for="email" class="block text-sm font-semibold text-slate-700 mb-2">
                        Adresse Email <span class="text-red-500">*</span>
                    </label>
                    <input 
                        type="email" 
                        id="email" 
                        name="email" 
                        required
                        placeholder="votre.email@exemple.com"
                        class="w-full px-4 py-3 border border-slate-300 rounded-lg text-slate-900 placeholder-slate-400 input-focus transition-all duration-200"
                    >
                </div>

                <!-- Password Field -->
                <div>
                    <label for="password" class="block text-sm font-semibold text-slate-700 mb-2">
                        Mot de Passe <span class="text-red-500">*</span>
                    </label>
                    <input 
                        type="password" 
                        id="password" 
                        name="password" 
                        required
                        placeholder="••••••••"
                        minlength="8"
                        class="w-full px-4 py-3 border border-slate-300 rounded-lg text-slate-900 placeholder-slate-400 input-focus transition-all duration-200"
                    >
                    <p class="mt-2 text-xs text-slate-500">Minimum 8 caractères</p>
                </div>

                <!-- Terms and Conditions -->
                <div class="flex items-start gap-3">
                    <input 
                        type="checkbox" 
                        id="terms" 
                        name="terms" 
                        required
                        class="mt-1 w-4 h-4 text-blue-600 border-slate-300 rounded focus:ring-blue-500 focus:ring-2 cursor-pointer"
                    >
                    <label for="terms" class="text-sm text-slate-600 leading-relaxed">
                        J'accepte les <a href="#" class="text-blue-600 hover:text-blue-700 font-medium">conditions d'utilisation</a> 
                        et la <a href="#" class="text-blue-600 hover:text-blue-700 font-medium">politique de confidentialité</a>
                    </label>
                </div>

                <!-- Submit Button -->
                <button 
                    type="submit"
                    class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3.5 px-6 rounded-lg transition-all duration-200 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5"
                >
                    Créer mon compte
                </button>

                <!-- Login Link -->
                <div class="text-center pt-4 border-t border-slate-200">
                    <p class="text-sm text-slate-600">
                        Vous avez déjà un compte ? 
                        <a href="${pageContext.request.contextPath}/WEB-INF/views/auth/login.jsp" class="text-blue-600 hover:text-blue-700 font-semibold">Se connecter</a>
                    </p>
                </div>
            </form>
        </div>

        <!-- Footer -->
        <div class="text-center mt-6">
            <p class="text-xs text-slate-500">
                © 2025 Clinic JEE. Tous droits réservés.
            </p>
        </div>
    </div>
    <!-- Script d'inscription patient -->
    <script src="${pageContext.request.contextPath}/assets/js/register-patient.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription Docteur - Plateforme Médicale</title>
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
        
        .select-focus:focus {
            outline: none;
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }
    </style>
</head>
<body class="bg-gradient-to-br from-slate-50 to-blue-50 min-h-screen flex items-center justify-center p-4">
    
    <div class="w-full max-w-2xl">
        <!-- Header Section -->
        <div class="text-center mb-8">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-blue-600 rounded-2xl mb-4 shadow-lg">
                <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                </svg>
            </div>
            <h1 class="text-3xl font-bold text-slate-900 mb-2">Inscription Docteur</h1>
            <p class="text-slate-600 text-balance">Créez votre compte professionnel pour accéder à la plateforme</p>
        </div>

        <!-- Main Form Card -->
        <div class="bg-white rounded-2xl shadow-xl p-8 border border-slate-200">
            <form id="registerDoctorForm" class="space-y-6">
                <input type="hidden" name="userType" value="DOCTEUR">
                
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
                        placeholder="Dr. Jean Dupont"
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
                        placeholder="docteur@exemple.com"
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

                <!-- Two Column Layout for Matricule and Titre -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <!-- Matricule Field -->
                    <div>
                        <label for="matricule" class="block text-sm font-semibold text-slate-700 mb-2">
                            Matricule <span class="text-red-500">*</span>
                        </label>
                        <input 
                            type="text" 
                            id="matricule" 
                            name="matricule" 
                            required
                            placeholder="MAT123456"
                            class="w-full px-4 py-3 border border-slate-300 rounded-lg text-slate-900 placeholder-slate-400 input-focus transition-all duration-200"
                        >
                    </div>

                    <!-- Titre Field -->
                    <div>
                        <label for="titre" class="block text-sm font-semibold text-slate-700 mb-2">
                            Titre <span class="text-red-500">*</span>
                        </label>
                        <input 
                            type="text" 
                            id="titre" 
                            name="titre" 
                            required
                            placeholder="Docteur, Professeur..."
                            class="w-full px-4 py-3 border border-slate-300 rounded-lg text-slate-900 placeholder-slate-400 input-focus transition-all duration-200"
                        >
                    </div>
                </div>

                <!-- Specialty Dropdown -->
                <div>
                    <label for="specialtyId" class="block text-sm font-semibold text-slate-700 mb-2">
                        Spécialité Médicale <span class="text-red-500">*</span>
                    </label>
                    <div class="relative">
                        <select 
                            id="specialtyId" 
                            name="specialtyId" 
                            required
                            class="w-full px-4 py-3 border border-slate-300 rounded-lg text-slate-900 appearance-none select-focus transition-all duration-200 bg-white cursor-pointer"
                        >
                            <option value="" disabled selected>Sélectionnez une spécialité</option>
                            <!-- Cardiologie -->
                            <option value="1">Cardiologie interventionnelle</option>
                            <option value="2">Électrophysiologie cardiaque</option>
                            <option value="3">Chirurgie cardiothoracique</option>
                            <option value="4">Insuffisance cardiaque</option>
                            <option value="5">Cardiologie pédiatrique</option>
                            <!-- Pédiatrie -->
                            <option value="6">Néonatologie</option>
                            <option value="7">Pédiatrie générale</option>
                            <option value="8">Gastro-entérologie pédiatrique</option>
                            <option value="9">Pneumologie pédiatrique</option>
                            <option value="10">Endocrinologie pédiatrique</option>
                            <!-- Orthopédie -->
                            <option value="11">Chirurgie de la colonne vertébrale</option>
                            <option value="12">Traumatologie</option>
                            <option value="13">Chirurgie de la main</option>
                            <option value="14">Chirurgie du genou</option>
                            <option value="15">Orthopédie pédiatrique</option>
                            <!-- Neurologie -->
                            <option value="16">Neurologie vasculaire</option>
                            <option value="17">Épileptologie</option>
                            <option value="18">Neurologie cognitive</option>
                            <option value="19">Maladies neuromusculaires</option>
                            <option value="20">Neurochirurgie</option>
                            <!-- Dermatologie -->
                            <option value="21">Dermatologie esthétique</option>
                            <option value="22">Dermatologie pédiatrique</option>
                            <option value="23">Oncologie cutanée</option>
                            <option value="24">Dermatologie allergologique</option>
                            <option value="25">Vénérologie</option>
                        </select>
                        <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-3 text-slate-500">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
                            </svg>
                        </div>
                    </div>
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
                        <a href="login.jsp" class="text-blue-600 hover:text-blue-700 font-semibold">Se connecter</a>
                    </p>
                </div>
            </form>
        </div>

        <!-- Footer -->
        <div class="text-center mt-6">
            <p class="text-xs text-slate-500">
                © 2025 Plateforme Médicale. Tous droits réservés.
            </p>
        </div>
    </div>

    <!-- Script d'inscription docteur -->
    <script src="assets/js/register-doctor.js"></script>
</body>
</html>

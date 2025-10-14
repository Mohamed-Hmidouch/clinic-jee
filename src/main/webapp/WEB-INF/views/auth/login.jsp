<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - IBnSina Hospital</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, system-ui, sans-serif;
        }
        
        body {
            line-height: 1.6;
        }
        
        .gradient-bg {
            background: linear-gradient(135deg, #10b981 0%, #14b8a6 100%);
        }
        
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .animate-fade-in {
            animation: fadeIn 0.3s ease-out;
        }
    </style>
</head>

<body class="bg-slate-50 min-h-screen flex items-center justify-center p-4">
    <div class="max-w-md w-full">
        <!-- Header -->
        <div class="text-center mb-8">
            <div class="inline-flex items-center justify-center w-16 h-16 bg-gradient-to-br from-emerald-500 to-teal-500 rounded-2xl shadow-lg mb-4">
                <i class="fas fa-heartbeat text-white text-3xl"></i>
            </div>
            <h1 class="text-3xl font-bold text-gray-900 mb-2">Connexion</h1>
            <p class="text-gray-600">Accédez à votre espace personnel</p>
        </div>

        <!-- Login Form -->
        <div class="bg-white rounded-2xl shadow-xl border border-slate-200 p-8">
            <!-- Error Alert -->
            <div id="errorAlert" class="hidden mb-6 p-4 bg-red-50 border border-red-200 rounded-lg animate-fade-in">
                <div class="flex items-start gap-3">
                    <i class="fas fa-exclamation-circle text-red-500 mt-0.5"></i>
                    <p id="errorMessage" class="text-sm text-red-700"></p>
                </div>
            </div>

            <!-- Success Alert -->
            <div id="successAlert" class="hidden mb-6 p-4 bg-emerald-50 border border-emerald-200 rounded-lg animate-fade-in">
                <div class="flex items-start gap-3">
                    <i class="fas fa-check-circle text-emerald-500 mt-0.5"></i>
                    <p id="successMessage" class="text-sm text-emerald-700"></p>
                </div>
            </div>

            <form id="loginForm">
                <!-- Email -->
                <div class="mb-5">
                    <label for="email" class="block text-sm font-semibold text-gray-700 mb-2">
                        <i class="fas fa-envelope text-emerald-600 mr-2"></i>Adresse email
                    </label>
                    <input type="email" id="email" name="email" required
                           class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-transparent outline-none transition"
                           placeholder="votre@email.com">
                </div>

                <!-- Password -->
                <div class="mb-5">
                    <label for="password" class="block text-sm font-semibold text-gray-700 mb-2">
                        <i class="fas fa-lock text-emerald-600 mr-2"></i>Mot de passe
                    </label>
                    <div class="relative">
                        <input type="password" id="password" name="password" required
                               class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-transparent outline-none transition pr-12"
                               placeholder="••••••••">
                        <button type="button" id="togglePassword"
                                class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-emerald-600 transition">
                            <i id="toggleIcon" class="fas fa-eye"></i>
                        </button>
                    </div>
                </div>

                <!-- Remember Me & Forgot Password -->
                <div class="flex items-center justify-between mb-6">
                    <label class="flex items-center cursor-pointer">
                        <input type="checkbox" id="remember" class="w-4 h-4 text-emerald-600 border-gray-300 rounded focus:ring-emerald-500">
                        <span class="ml-2 text-sm text-gray-700">Se souvenir de moi</span>
                    </label>
                    <a href="#" class="text-sm text-emerald-600 hover:text-emerald-700 font-medium">
                        Mot de passe oublié ?
                    </a>
                </div>

                <!-- Submit Button -->
                <button type="submit" id="submitBtn"
                        class="w-full px-6 py-3 bg-emerald-600 text-white font-semibold rounded-lg hover:bg-emerald-700 transition shadow-lg hover:shadow-xl disabled:bg-gray-300 disabled:cursor-not-allowed">
                    <span id="btnText">
                        <i class="fas fa-sign-in-alt mr-2"></i>Se connecter
                    </span>
                    <span id="btnLoader" class="hidden">
                        <i class="fas fa-circle-notch fa-spin mr-2"></i>Connexion...
                    </span>
                </button>
            </form>

            <!-- Divider -->
            <div class="my-6 flex items-center">
                <div class="flex-1 border-t border-gray-300"></div>
                <span class="px-4 text-sm text-gray-500">OU</span>
                <div class="flex-1 border-t border-gray-300"></div>
            </div>

            <!-- Register Link -->
            <div class="text-center">
                <p class="text-sm text-gray-600">
                    Vous n'avez pas de compte ?
                    <a href="<%= request.getContextPath() %>/auth/register" class="text-emerald-600 hover:text-emerald-700 font-semibold">
                        Créer un compte
                    </a>
                </p>
            </div>
        </div>

        <!-- Back to Home -->
        <div class="mt-6 text-center">
            <a href="<%= request.getContextPath() %>/" class="text-sm text-gray-600 hover:text-emerald-600 transition">
                <i class="fas fa-arrow-left mr-2"></i>Retour à l'accueil
            </a>
        </div>
    </div>

    <script src="<%= request.getContextPath() %>/assets/js/login.js"></script>
    
    <script>
        // Toggle password visibility
        document.getElementById('togglePassword').addEventListener('click', function() {
            const passwordInput = document.getElementById('password');
            const toggleIcon = document.getElementById('toggleIcon');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleIcon.classList.remove('fa-eye');
                toggleIcon.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                toggleIcon.classList.remove('fa-eye-slash');
                toggleIcon.classList.add('fa-eye');
            }
        });

        // Show error message
        function showError(message) {
            const errorAlert = document.getElementById('errorAlert');
            const errorMessage = document.getElementById('errorMessage');
            const successAlert = document.getElementById('successAlert');
            
            successAlert.classList.add('hidden');
            errorMessage.textContent = message;
            errorAlert.classList.remove('hidden');
            
            setTimeout(function() {
                errorAlert.classList.add('hidden');
            }, 5000);
        }

        // Show success message
        function showSuccess(message) {
            const successAlert = document.getElementById('successAlert');
            const successMessage = document.getElementById('successMessage');
            const errorAlert = document.getElementById('errorAlert');
            
            errorAlert.classList.add('hidden');
            successMessage.textContent = message;
            successAlert.classList.remove('hidden');
        }

        // Show loading state
        function showLoading(isLoading) {
            const submitBtn = document.getElementById('submitBtn');
            const btnText = document.getElementById('btnText');
            const btnLoader = document.getElementById('btnLoader');
            
            if (isLoading) {
                submitBtn.disabled = true;
                btnText.classList.add('hidden');
                btnLoader.classList.remove('hidden');
            } else {
                submitBtn.disabled = false;
                btnText.classList.remove('hidden');
                btnLoader.classList.add('hidden');
            }
        }
    </script>
</body>
</html>

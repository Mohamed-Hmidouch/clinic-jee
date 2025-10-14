<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard - IBnSina Hospital</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-slate-50">
    <nav class="bg-white shadow-sm sticky top-0 z-50 border-b border-slate-200">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between items-center h-16">
                <div class="flex items-center gap-3">
                    <div class="w-10 h-10 bg-gradient-to-br from-emerald-500 to-teal-500 rounded-lg flex items-center justify-center">
                        <i class="fas fa-heartbeat text-white text-xl"></i>
                    </div>
                    <div>
                        <h1 class="text-lg font-semibold text-gray-900">IBnSina Staff</h1>
                        <p class="text-xs text-gray-500 -mt-0.5">Espace Personnel</p>
                    </div>
                </div>
                <button onclick="logout()" class="px-4 py-2 text-sm font-medium text-white bg-emerald-600 hover:bg-emerald-700 rounded-lg transition">
                    <i class="fas fa-sign-out-alt mr-2"></i>Déconnexion
                </button>
            </div>
        </div>
    </nav>

    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-8">Tableau de Bord Personnel</h1>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="w-12 h-12 bg-emerald-100 rounded-lg flex items-center justify-center mb-4">
                    <i class="fas fa-clipboard-list text-emerald-600 text-xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">32</h3>
                <p class="text-sm text-gray-600">Tâches en Cours</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="w-12 h-12 bg-teal-100 rounded-lg flex items-center justify-center mb-4">
                    <i class="fas fa-phone text-teal-600 text-xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">15</h3>
                <p class="text-sm text-gray-600">Appels en Attente</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="w-12 h-12 bg-cyan-100 rounded-lg flex items-center justify-center mb-4">
                    <i class="fas fa-file-medical text-cyan-600 text-xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">8</h3>
                <p class="text-sm text-gray-600">Dossiers à Traiter</p>
            </div>
        </div>

        <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-8">
            <h2 class="text-xl font-bold text-gray-900 mb-6">Tâches Prioritaires</h2>
            <div class="space-y-3">
                <div class="flex items-center justify-between p-4 bg-red-50 border border-red-200 rounded-lg">
                    <div class="flex items-center gap-3">
                        <i class="fas fa-exclamation-circle text-red-500"></i>
                        <span class="font-medium text-gray-900">Appel urgent - Salle 302</span>
                    </div>
                    <span class="text-xs text-red-600 font-medium">URGENT</span>
                </div>
                <div class="flex items-center justify-between p-4 bg-yellow-50 border border-yellow-200 rounded-lg">
                    <div class="flex items-center gap-3">
                        <i class="fas fa-clock text-yellow-500"></i>
                        <span class="font-medium text-gray-900">Préparer dossier patient #1234</span>
                    </div>
                    <span class="text-xs text-yellow-600 font-medium">EN ATTENTE</span>
                </div>
            </div>
        </div>
    </main>

    <script>
        function logout() {
            if (confirm('Êtes-vous sûr de vouloir vous déconnecter?')) {
                window.location.href = '<%= request.getContextPath() %>/auth/login';
            }
        }
    </script>
</body>
</html>

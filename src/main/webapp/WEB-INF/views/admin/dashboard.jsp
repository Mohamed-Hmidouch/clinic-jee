<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - IBnSina Hospital</title>
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
                        <h1 class="text-lg font-semibold text-gray-900">IBnSina Admin</h1>
                        <p class="text-xs text-gray-500 -mt-0.5">Panneau d'Administration</p>
                    </div>
                </div>
                <button onclick="logout()" class="px-4 py-2 text-sm font-medium text-white bg-emerald-600 hover:bg-emerald-700 rounded-lg transition">
                    <i class="fas fa-sign-out-alt mr-2"></i>Déconnexion
                </button>
            </div>
        </div>
    </nav>

    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-8">Tableau de Bord Administrateur</h1>
        
        <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="w-12 h-12 bg-emerald-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-users text-emerald-600 text-xl"></i>
                    </div>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">1,234</h3>
                <p class="text-sm text-gray-600">Total Patients</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="w-12 h-12 bg-teal-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-user-md text-teal-600 text-xl"></i>
                    </div>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">156</h3>
                <p class="text-sm text-gray-600">Médecins</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="w-12 h-12 bg-cyan-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-calendar-check text-cyan-600 text-xl"></i>
                    </div>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">892</h3>
                <p class="text-sm text-gray-600">Rendez-vous</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-hospital text-green-600 text-xl"></i>
                    </div>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">12</h3>
                <p class="text-sm text-gray-600">Départements</p>
            </div>
        </div>

        <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-8">
            <h2 class="text-xl font-bold text-gray-900 mb-4">Gestion Rapide</h2>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                <a href="#" class="p-4 border-2 border-gray-200 rounded-lg hover:border-emerald-500 transition">
                    <i class="fas fa-user-plus text-emerald-600 text-2xl mb-2"></i>
                    <h3 class="font-semibold text-gray-900">Ajouter Patient</h3>
                </a>
                <a href="#" class="p-4 border-2 border-gray-200 rounded-lg hover:border-emerald-500 transition">
                    <i class="fas fa-user-md text-teal-600 text-2xl mb-2"></i>
                    <h3 class="font-semibold text-gray-900">Gérer Médecins</h3>
                </a>
                <a href="#" class="p-4 border-2 border-gray-200 rounded-lg hover:border-emerald-500 transition">
                    <i class="fas fa-calendar text-cyan-600 text-2xl mb-2"></i>
                    <h3 class="font-semibold text-gray-900">Voir Rendez-vous</h3>
                </a>
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

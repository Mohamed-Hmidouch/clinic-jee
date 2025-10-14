<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Dashboard - IBnSina Hospital</title>
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
                        <h1 class="text-lg font-semibold text-gray-900">IBnSina Doctor</h1>
                        <p class="text-xs text-gray-500 -mt-0.5">Espace Médecin</p>
                    </div>
                </div>
                <button onclick="logout()" class="px-4 py-2 text-sm font-medium text-white bg-emerald-600 hover:bg-emerald-700 rounded-lg transition">
                    <i class="fas fa-sign-out-alt mr-2"></i>Déconnexion
                </button>
            </div>
        </div>
    </nav>

    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-8">Tableau de Bord Médecin</h1>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="w-12 h-12 bg-emerald-100 rounded-lg flex items-center justify-center mb-4">
                    <i class="fas fa-calendar-day text-emerald-600 text-xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">8</h3>
                <p class="text-sm text-gray-600">Rendez-vous Aujourd'hui</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="w-12 h-12 bg-teal-100 rounded-lg flex items-center justify-center mb-4">
                    <i class="fas fa-users text-teal-600 text-xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">45</h3>
                <p class="text-sm text-gray-600">Patients Actifs</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="w-12 h-12 bg-cyan-100 rounded-lg flex items-center justify-center mb-4">
                    <i class="fas fa-clock text-cyan-600 text-xl"></i>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">12</h3>
                <p class="text-sm text-gray-600">Heures Disponibles</p>
            </div>
        </div>

        <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-8">
            <h2 class="text-xl font-bold text-gray-900 mb-6">Rendez-vous d'Aujourd'hui</h2>
            <div class="space-y-4">
                <div class="border-l-4 border-emerald-500 bg-emerald-50 p-4 rounded-r-lg">
                    <div class="flex justify-between items-start">
                        <div>
                            <h3 class="font-semibold text-gray-900">Ahmed Benali</h3>
                            <p class="text-sm text-gray-600">Consultation de suivi - Cardiologie</p>
                        </div>
                        <span class="text-sm font-medium text-emerald-600">09:00</span>
                    </div>
                </div>
                <div class="border-l-4 border-teal-500 bg-teal-50 p-4 rounded-r-lg">
                    <div class="flex justify-between items-start">
                        <div>
                            <h3 class="font-semibold text-gray-900">Fatima Zahra</h3>
                            <p class="text-sm text-gray-600">Première consultation</p>
                        </div>
                        <span class="text-sm font-medium text-teal-600">10:30</span>
                    </div>
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

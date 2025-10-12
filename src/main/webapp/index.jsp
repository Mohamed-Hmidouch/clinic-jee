<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clinic JEE - Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="bg-gradient-to-br from-blue-50 to-indigo-100 min-h-screen">
    
    <!-- Navbar -->
    <nav class="bg-white shadow-lg">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between h-16">
                <div class="flex items-center">
                    <i class="fas fa-hospital text-indigo-600 text-3xl mr-3"></i>
                    <span class="text-2xl font-bold text-gray-800">Clinic JEE</span>
                </div>
                <div class="flex items-center space-x-4">
                    <span class="px-4 py-2 bg-green-100 text-green-800 rounded-full text-sm font-semibold">
                        <i class="fas fa-circle text-green-500 text-xs mr-1"></i> En ligne
                    </span>
                </div>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        <!-- Hero Section -->
        <div class="bg-gradient-to-r from-indigo-600 to-purple-600 rounded-2xl shadow-2xl p-8 mb-8 text-white">
            <div class="flex items-center justify-between">
                <div>
                    <h1 class="text-4xl font-bold mb-2">Bienvenue sur Clinic JEE</h1>
                    <p class="text-indigo-100 text-lg">Système de gestion de clinique moderne et sécurisé</p>
                </div>
                <i class="fas fa-heartbeat text-8xl opacity-20"></i>
            </div>
        </div>

        <!-- Stats Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            <!-- Card 1 -->
            <div class="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow">
                <div class="flex items-center justify-between mb-4">
                    <div class="bg-blue-100 p-3 rounded-lg">
                        <i class="fas fa-user-md text-blue-600 text-2xl"></i>
                    </div>
                    <span class="text-green-600 text-sm font-semibold">+12%</span>
                </div>
                <h3 class="text-gray-500 text-sm font-medium mb-1">Médecins</h3>
                <p class="text-3xl font-bold text-gray-800">24</p>
            </div>

            <!-- Card 2 -->
            <div class="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow">
                <div class="flex items-center justify-between mb-4">
                    <div class="bg-green-100 p-3 rounded-lg">
                        <i class="fas fa-users text-green-600 text-2xl"></i>
                    </div>
                    <span class="text-green-600 text-sm font-semibold">+8%</span>
                </div>
                <h3 class="text-gray-500 text-sm font-medium mb-1">Patients</h3>
                <p class="text-3xl font-bold text-gray-800">1,245</p>
            </div>

            <!-- Card 3 -->
            <div class="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow">
                <div class="flex items-center justify-between mb-4">
                    <div class="bg-purple-100 p-3 rounded-lg">
                        <i class="fas fa-calendar-check text-purple-600 text-2xl"></i>
                    </div>
                    <span class="text-red-600 text-sm font-semibold">-3%</span>
                </div>
                <h3 class="text-gray-500 text-sm font-medium mb-1">Rendez-vous</h3>
                <p class="text-3xl font-bold text-gray-800">89</p>
            </div>

            <!-- Card 4 -->
            <div class="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow">
                <div class="flex items-center justify-between mb-4">
                    <div class="bg-yellow-100 p-3 rounded-lg">
                        <i class="fas fa-chart-line text-yellow-600 text-2xl"></i>
                    </div>
                    <span class="text-green-600 text-sm font-semibold">+15%</span>
                </div>
                <h3 class="text-gray-500 text-sm font-medium mb-1">Revenus</h3>
                <p class="text-3xl font-bold text-gray-800">45.2K €</p>
            </div>
        </div>

        <!-- Main Grid -->
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
            
            <!-- Left Column -->
            <div class="lg:col-span-2 space-y-6">
                
                <!-- API Endpoints -->
                <div class="bg-white rounded-xl shadow-lg p-6">
                    <h2 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                        <i class="fas fa-plug text-indigo-600 mr-2"></i>
                        Endpoints API REST
                    </h2>
                    <div class="space-y-3">
                        <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition">
                            <div class="flex items-center space-x-3">
                                <span class="px-3 py-1 bg-green-100 text-green-800 text-xs font-bold rounded-full">GET</span>
                                <code class="text-sm text-gray-700">/api/hello-world</code>
                            </div>
                            <a href="api/hello-world" target="_blank" class="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition text-sm font-semibold">
                                Tester <i class="fas fa-arrow-right ml-1"></i>
                            </a>
                        </div>
                        
                        <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition">
                            <div class="flex items-center space-x-3">
                                <span class="px-3 py-1 bg-blue-100 text-blue-800 text-xs font-bold rounded-full">GET</span>
                                <code class="text-sm text-gray-700">/test-connection</code>
                            </div>
                            <a href="test-connection" target="_blank" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition text-sm font-semibold">
                                Tester BDD <i class="fas fa-database ml-1"></i>
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Server Info -->
                <div class="bg-white rounded-xl shadow-lg p-6">
                    <h2 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                        <i class="fas fa-server text-indigo-600 mr-2"></i>
                        Informations Serveur
                    </h2>
                    <div class="space-y-3 font-mono text-sm">
                        <div class="flex justify-between p-3 bg-blue-50 rounded-lg">
                            <span class="text-gray-600">Session ID:</span>
                            <span class="text-gray-800 font-semibold"><%= session.getId().substring(0, 20) %>...</span>
                        </div>
                        <div class="flex justify-between p-3 bg-blue-50 rounded-lg">
                            <span class="text-gray-600">Serveur:</span>
                            <span class="text-gray-800 font-semibold"><%= application.getServerInfo() %></span>
                        </div>
                        <div class="flex justify-between p-3 bg-blue-50 rounded-lg">
                            <span class="text-gray-600">Timestamp:</span>
                            <span class="text-gray-800 font-semibold"><%= new java.util.Date() %></span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Right Column -->
            <div class="space-y-6">
                
                <!-- Tech Stack -->
                <div class="bg-white rounded-xl shadow-lg p-6">
                    <h2 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                        <i class="fas fa-layer-group text-indigo-600 mr-2"></i>
                        Stack Technique
                    </h2>
                    <div class="space-y-2">
                        <div class="flex items-center p-3 bg-gradient-to-r from-orange-50 to-orange-100 rounded-lg">
                            <i class="fab fa-java text-orange-600 text-xl mr-3"></i>
                            <span class="font-semibold text-gray-700">Java 17</span>
                        </div>
                        <div class="flex items-center p-3 bg-gradient-to-r from-red-50 to-red-100 rounded-lg">
                            <i class="fas fa-server text-red-600 text-xl mr-3"></i>
                            <span class="font-semibold text-gray-700">Tomcat 10.1</span>
                        </div>
                        <div class="flex items-center p-3 bg-gradient-to-r from-green-50 to-green-100 rounded-lg">
                            <i class="fas fa-file-code text-green-600 text-xl mr-3"></i>
                            <span class="font-semibold text-gray-700">JSP</span>
                        </div>
                        <div class="flex items-center p-3 bg-gradient-to-r from-yellow-50 to-yellow-100 rounded-lg">
                            <i class="fas fa-database text-yellow-600 text-xl mr-3"></i>
                            <span class="font-semibold text-gray-700">Hibernate 7</span>
                        </div>
                        <div class="flex items-center p-3 bg-gradient-to-r from-blue-50 to-blue-100 rounded-lg">
                            <i class="fas fa-elephant text-blue-600 text-xl mr-3"></i>
                            <span class="font-semibold text-gray-700">PostgreSQL 16</span>
                        </div>
                        <div class="flex items-center p-3 bg-gradient-to-r from-cyan-50 to-cyan-100 rounded-lg">
                            <i class="fab fa-docker text-cyan-600 text-xl mr-3"></i>
                            <span class="font-semibold text-gray-700">Docker</span>
                        </div>
                    </div>
                </div>

                <!-- Quick Links -->
                <div class="bg-white rounded-xl shadow-lg p-6">
                    <h2 class="text-xl font-bold text-gray-800 mb-4 flex items-center">
                        <i class="fas fa-external-link-alt text-indigo-600 mr-2"></i>
                        Liens Rapides
                    </h2>
                    <div class="space-y-2">
                        <a href="register-doctor.jsp" class="block p-3 bg-gradient-to-r from-blue-50 to-indigo-50 hover:from-blue-100 hover:to-indigo-100 rounded-lg transition group border-2 border-blue-200">
                            <div class="flex items-center justify-between">
                                <span class="font-semibold text-blue-700 group-hover:text-blue-800 flex items-center">
                                    <i class="fas fa-user-md mr-2"></i>
                                    Inscription Docteur
                                </span>
                                <i class="fas fa-arrow-right text-blue-400 group-hover:text-blue-600"></i>
                            </div>
                            <span class="text-xs text-blue-600">Créer un compte professionnel</span>
                        </a>
                        <a href="http://localhost:5051" target="_blank" class="block p-3 bg-gray-50 hover:bg-indigo-50 rounded-lg transition group">
                            <div class="flex items-center justify-between">
                                <span class="font-semibold text-gray-700 group-hover:text-indigo-600">pgAdmin</span>
                                <i class="fas fa-arrow-right text-gray-400 group-hover:text-indigo-600"></i>
                            </div>
                            <span class="text-xs text-gray-500">Port 5051</span>
                        </a>
                        <a href="#" class="block p-3 bg-gray-50 hover:bg-indigo-50 rounded-lg transition group">
                            <div class="flex items-center justify-between">
                                <span class="font-semibold text-gray-700 group-hover:text-indigo-600">Documentation</span>
                                <i class="fas fa-arrow-right text-gray-400 group-hover:text-indigo-600"></i>
                            </div>
                            <span class="text-xs text-gray-500">Guide complet</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-white shadow-lg mt-12">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
            <div class="flex justify-between items-center">
                <p class="text-gray-600">© 2025 Clinic JEE - Tous droits réservés</p>
                <p class="text-sm text-gray-500">Créé avec <i class="fas fa-heart text-red-500"></i> | Powered by Jakarta EE</p>
            </div>
        </div>
    </footer>

</body>
</html>

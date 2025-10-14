<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>IBnSina Hospital - Votre Santé, Notre Priorité</title>
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
            line-height: 1.7;
            letter-spacing: -0.011em;
        }
        
        .service-card {
            transition: all 0.3s ease;
        }
        
        .service-card:hover {
            border-color: #10b981;
            box-shadow: 0 10px 25px -5px rgba(16, 185, 129, 0.2);
            transform: translateY(-2px);
        }
        
        .specialty-card {
            transition: all 0.3s ease;
        }
        
        .specialty-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 20px -6px rgba(20, 184, 166, 0.25);
        }
        
        .hero-overlay {
            background: linear-gradient(135deg, rgba(16, 185, 129, 0.9) 0%, rgba(20, 184, 166, 0.85) 100%);
        }
    </style>
</head>

<body class="bg-gray-50 antialiased">
    <!-- Navbar -->
    <nav class="bg-white shadow-sm sticky top-0 z-50 border-b border-gray-100">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between items-center h-16">
                <!-- Logo -->
                <div class="flex items-center gap-3">
                    <div class="w-10 h-10 bg-gradient-to-br from-emerald-500 to-teal-500 rounded-lg flex items-center justify-center shadow-sm">
                        <i class="fas fa-heartbeat text-white text-xl"></i>
                    </div>
                    <div>
                        <h1 class="text-lg font-semibold text-gray-900 tracking-tight">IBnSina Hospital</h1>
                        <p class="text-xs text-gray-500 -mt-0.5">Excellence in Healthcare</p>
                    </div>
                </div>
                
                <!-- Navigation Links (Desktop) -->
                <div class="hidden md:flex items-center gap-8">
                    <a href="#services" class="text-sm font-medium text-gray-600 hover:text-emerald-600 transition">Services</a>
                    <a href="#specialties" class="text-sm font-medium text-gray-600 hover:text-emerald-600 transition">Spécialités</a>
                    <a href="#about" class="text-sm font-medium text-gray-600 hover:text-emerald-600 transition">À propos</a>
                    <a href="#contact" class="text-sm font-medium text-gray-600 hover:text-emerald-600 transition">Contact</a>
                </div>
                
                <!-- Auth Buttons -->
                <div class="flex items-center gap-3">
                    <a href="/auth/login" class="px-4 py-2 text-sm font-medium text-gray-700 hover:text-emerald-600 transition">
                        Connexion
                    </a>
                    <a href="/auth/register" class="px-4 py-2 text-sm font-medium text-white bg-emerald-600 hover:bg-emerald-700 rounded-lg transition shadow-sm">
                        Inscription
                    </a>
                </div>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="relative min-h-[600px] flex items-center">
        <img src="assets/img/photo1687881786-q8l4wusuky06tf6xem4orgz7v4i29g1xi5mma1otcw.jpeg" 
             alt="Hôpital" 
             class="absolute inset-0 w-full h-full object-cover">
        <div class="hero-overlay absolute inset-0"></div>
        
        <div class="relative z-10 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24">
            <div class="max-w-2xl">
                <div class="inline-block mb-6">
                    <span class="px-4 py-2 bg-white/20 backdrop-blur-sm text-white text-sm font-medium rounded-full border border-white/30">
                        🏥 Service d'urgence 24/7
                    </span>
                </div>
                
                <h1 class="text-5xl lg:text-6xl font-bold text-white leading-tight mb-6">
                    Votre Santé,<br/>
                    Notre Mission
                </h1>
                
                <p class="text-xl text-white/95 mb-8 leading-relaxed">
                    Des soins de qualité avec des professionnels dévoués
                </p>
                
                <div class="flex flex-wrap gap-4">
                    <a href="#contact" class="px-8 py-4 bg-white text-emerald-600 font-semibold rounded-lg hover:bg-gray-50 transition shadow-lg">
                        Prendre rendez-vous
                    </a>
                    <a href="#services" class="px-8 py-4 bg-white/10 backdrop-blur-sm text-white font-semibold rounded-lg hover:bg-white/20 transition border border-white/30">
                        Découvrir
                    </a>
                </div>
            </div>
        </div>
    </section>

    <!-- Services Section -->
    <section id="services" class="py-20 bg-white">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="text-center max-w-2xl mx-auto mb-12">
                <h2 class="text-3xl font-semibold text-gray-900 mb-3">Nos Services</h2>
                <p class="text-gray-600">Excellence médicale à votre service</p>
            </div>
            
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                <div class="service-card bg-white border-2 border-gray-100 rounded-xl p-6">
                    <div class="w-14 h-14 bg-emerald-100 rounded-xl flex items-center justify-center mb-4">
                        <i class="fas fa-stethoscope text-emerald-600 text-2xl"></i>
                    </div>
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">Consultations</h3>
                    <p class="text-sm text-gray-600">Expertise médicale</p>
                </div>
                
                <div class="service-card bg-white border-2 border-gray-100 rounded-xl p-6">
                    <div class="w-14 h-14 bg-teal-100 rounded-xl flex items-center justify-center mb-4">
                        <i class="fas fa-ambulance text-teal-600 text-2xl"></i>
                    </div>
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">Urgences 24/7</h3>
                    <p class="text-sm text-gray-600">Service continu</p>
                </div>
                
                <div class="service-card bg-white border-2 border-gray-100 rounded-xl p-6">
                    <div class="w-14 h-14 bg-cyan-100 rounded-xl flex items-center justify-center mb-4">
                        <i class="fas fa-x-ray text-cyan-600 text-2xl"></i>
                    </div>
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">Imagerie</h3>
                    <p class="text-sm text-gray-600">Technologie avancée</p>
                </div>
                
                <div class="service-card bg-white border-2 border-gray-100 rounded-xl p-6">
                    <div class="w-14 h-14 bg-green-100 rounded-xl flex items-center justify-center mb-4">
                        <i class="fas fa-flask text-green-600 text-2xl"></i>
                    </div>
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">Laboratoire</h3>
                    <p class="text-sm text-gray-600">Analyses précises</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Statistics Section -->
    <section class="py-16 bg-gradient-to-br from-emerald-600 to-teal-600">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="grid grid-cols-2 md:grid-cols-4 gap-8 text-center text-white">
                <div>
                    <div class="text-4xl font-bold mb-2">150+</div>
                    <div class="text-emerald-100 text-sm">Médecins Experts</div>
                </div>
                <div>
                    <div class="text-4xl font-bold mb-2">50K+</div>
                    <div class="text-emerald-100 text-sm">Patients Soignés</div>
                </div>
                <div>
                    <div class="text-4xl font-bold mb-2">25+</div>
                    <div class="text-emerald-100 text-sm">Années d'Excellence</div>
                </div>
                <div>
                    <div class="text-4xl font-bold mb-2">98%</div>
                    <div class="text-emerald-100 text-sm">Satisfaction</div>
                </div>
            </div>
        </div>
    </section>

    <!-- Specialties Section -->
    <section id="specialties" class="py-20 bg-gray-50">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="text-center max-w-2xl mx-auto mb-12">
                <h2 class="text-3xl font-semibold text-gray-900 mb-3">Nos Spécialités</h2>
                <p class="text-gray-600">Excellence médicale pluridisciplinaire</p>
            </div>
            
            <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4">
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-emerald-100">
                    <div class="w-14 h-14 bg-emerald-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-heart text-emerald-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Cardiologie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-teal-100">
                    <div class="w-14 h-14 bg-teal-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-brain text-teal-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Neurologie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-cyan-100">
                    <div class="w-14 h-14 bg-cyan-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-bone text-cyan-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Orthopédie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-green-100">
                    <div class="w-14 h-14 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-baby text-green-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Pédiatrie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-emerald-100">
                    <div class="w-14 h-14 bg-emerald-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-lungs text-emerald-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Pneumologie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-teal-100">
                    <div class="w-14 h-14 bg-teal-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-eye text-teal-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Ophtalmologie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-cyan-100">
                    <div class="w-14 h-14 bg-cyan-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-tooth text-cyan-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Dentisterie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-green-100">
                    <div class="w-14 h-14 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-microscope text-green-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Oncologie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-emerald-100">
                    <div class="w-14 h-14 bg-emerald-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-user-md text-emerald-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Dermatologie</h3>
                </div>
                
                <div class="specialty-card bg-white rounded-xl p-5 text-center border-2 border-teal-100">
                    <div class="w-14 h-14 bg-teal-100 rounded-full flex items-center justify-center mx-auto mb-3">
                        <i class="fas fa-procedures text-teal-600 text-xl"></i>
                    </div>
                    <h3 class="text-sm font-semibold text-gray-900">Chirurgie</h3>
                </div>
            </div>
        </div>
    </section>

    <!-- Contact Section -->
    <section id="contact" class="py-24 bg-white">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="grid grid-cols-1 lg:grid-cols-2 gap-12">
                <!-- Contact Form -->
                <div>
                    <h2 class="text-3xl font-semibold text-gray-900 mb-4">Contactez-nous</h2>
                    <p class="text-gray-600 mb-8">Remplissez le formulaire et notre équipe vous répondra rapidement</p>
                    
                    <form class="space-y-6">
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">Nom complet</label>
                                <input type="text" class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-transparent outline-none transition" placeholder="Votre nom">
                            </div>
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">Téléphone</label>
                                <input type="tel" class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-transparent outline-none transition" placeholder="+212 6XX XXX XXX">
                            </div>
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Email</label>
                            <input type="email" class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-transparent outline-none transition" placeholder="votre@email.com">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Message</label>
                            <textarea rows="4" class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-transparent outline-none transition" placeholder="Comment pouvons-nous vous aider ?"></textarea>
                        </div>
                        
                        <button type="submit" class="w-full px-6 py-3 bg-emerald-600 text-white font-semibold rounded-lg hover:bg-emerald-700 transition shadow-sm">
                            Envoyer le message
                        </button>
                    </form>
                </div>
                
                <!-- Contact Info -->
                <div class="space-y-6">
                    <div class="bg-gray-50 rounded-xl p-8">
                        <h3 class="text-xl font-semibold text-gray-900 mb-6">Informations de contact</h3>
                        
                        <div class="space-y-6">
                            <div class="flex items-start gap-4">
                                <div class="w-12 h-12 bg-emerald-100 rounded-lg flex items-center justify-center flex-shrink-0">
                                    <i class="fas fa-map-marker-alt text-emerald-600"></i>
                                </div>
                                <div>
                                    <h4 class="font-medium text-gray-900 mb-1">Adresse</h4>
                                    <p class="text-sm text-gray-600">123 Avenue Mohammed V<br/>Casablanca, Maroc</p>
                                </div>
                            </div>
                            
                            <div class="flex items-start gap-4">
                                <div class="w-12 h-12 bg-teal-100 rounded-lg flex items-center justify-center flex-shrink-0">
                                    <i class="fas fa-phone text-teal-600"></i>
                                </div>
                                <div>
                                    <h4 class="font-medium text-gray-900 mb-1">Téléphone</h4>
                                    <p class="text-sm text-gray-600">+212 522 123 456<br/>+212 661 234 567</p>
                                </div>
                            </div>
                            
                            <div class="flex items-start gap-4">
                                <div class="w-12 h-12 bg-cyan-100 rounded-lg flex items-center justify-center flex-shrink-0">
                                    <i class="fas fa-envelope text-cyan-600"></i>
                                </div>
                                <div>
                                    <h4 class="font-medium text-gray-900 mb-1">Email</h4>
                                    <p class="text-sm text-gray-600">contact@ibnsina-hospital.ma<br/>urgences@ibnsina-hospital.ma</p>
                                </div>
                            </div>
                            
                            <div class="flex items-start gap-4">
                                <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center flex-shrink-0">
                                    <i class="fas fa-clock text-green-600"></i>
                                </div>
                                <div>
                                    <h4 class="font-medium text-gray-900 mb-1">Horaires</h4>
                                    <p class="text-sm text-gray-600">Urgences: 24/7<br/>Consultations: Lun-Sam 8h-20h</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-gray-900 text-gray-300 py-12">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
                <!-- About -->
                <div>
                    <div class="flex items-center gap-2 mb-4">
                        <div class="w-8 h-8 bg-emerald-600 rounded-lg flex items-center justify-center">
                            <i class="fas fa-heartbeat text-white"></i>
                        </div>
                        <span class="font-semibold text-white text-lg">IBnSina</span>
                    </div>
                    <p class="text-sm text-gray-400 leading-relaxed">
                        Excellence en soins de santé. Votre santé est notre priorité.
                    </p>
                </div>
                
                <!-- Quick Links -->
                <div>
                    <h3 class="font-semibold text-white mb-4">Liens rapides</h3>
                    <ul class="space-y-2 text-sm">
                        <li><a href="#" class="hover:text-emerald-400 transition">À propos</a></li>
                        <li><a href="#" class="hover:text-emerald-400 transition">Services</a></li>
                        <li><a href="#" class="hover:text-emerald-400 transition">Spécialités</a></li>
                        <li><a href="#" class="hover:text-emerald-400 transition">Contact</a></li>
                    </ul>
                </div>
                
                <!-- Services -->
                <div>
                    <h3 class="font-semibold text-white mb-4">Services</h3>
                    <ul class="space-y-2 text-sm">
                        <li><a href="#" class="hover:text-emerald-400 transition">Consultations</a></li>
                        <li><a href="#" class="hover:text-emerald-400 transition">Urgences</a></li>
                        <li><a href="#" class="hover:text-emerald-400 transition">Laboratoire</a></li>
                        <li><a href="#" class="hover:text-emerald-400 transition">Imagerie</a></li>
                    </ul>
                </div>
                
                <!-- Social -->
                <div>
                    <h3 class="font-semibold text-white mb-4">Suivez-nous</h3>
                    <div class="flex gap-3">
                        <a href="#" class="w-10 h-10 bg-gray-800 rounded-lg flex items-center justify-center hover:bg-emerald-600 transition">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a href="#" class="w-10 h-10 bg-gray-800 rounded-lg flex items-center justify-center hover:bg-teal-500 transition">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a href="#" class="w-10 h-10 bg-gray-800 rounded-lg flex items-center justify-center hover:bg-pink-600 transition">
                            <i class="fab fa-instagram"></i>
                        </a>
                        <a href="#" class="w-10 h-10 bg-gray-800 rounded-lg flex items-center justify-center hover:bg-cyan-600 transition">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                    </div>
                </div>
            </div>
            
            <div class="border-t border-gray-800 pt-8 text-center">
                <p class="text-sm text-gray-400">
                    &copy; 2025 IBnSina Hospital. Tous droits réservés.
                </p>
            </div>
        </div>
    </footer>
</body>
</html>

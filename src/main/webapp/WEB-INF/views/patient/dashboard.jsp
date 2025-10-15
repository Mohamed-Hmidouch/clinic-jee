<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.clinicjee.domain.Patient" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Patient Dashboard - IBnSina Hospital</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-slate-50">
    <!-- Navbar -->
    <nav class="bg-white shadow-sm sticky top-0 z-50 border-b border-slate-200">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex justify-between items-center h-16">
                <div class="flex items-center gap-3">
                    <div class="w-10 h-10 bg-gradient-to-br from-emerald-500 to-teal-500 rounded-lg flex items-center justify-center">
                        <i class="fas fa-heartbeat text-white text-xl"></i>
                    </div>
                    <div>
                        <h1 class="text-lg font-semibold text-gray-900">IBnSina Patient</h1>
                        <p class="text-xs text-gray-500 -mt-0.5">Espace Personnel</p>
                    </div>
                </div>
                
                <div class="hidden md:flex items-center gap-6">
                    <a href="#search" class="text-sm font-medium text-gray-600 hover:text-emerald-600">Rechercher</a>
                    <a href="#appointments" class="text-sm font-medium text-gray-600 hover:text-emerald-600">Mes Rendez-vous</a>
                    <a href="#history" class="text-sm font-medium text-gray-600 hover:text-emerald-600">Historique</a>
                </div>
                
                <button onclick="logout()" class="px-4 py-2 text-sm font-medium text-white bg-emerald-600 hover:bg-emerald-700 rounded-lg">
                    <i class="fas fa-sign-out-alt mr-2"></i>Déconnexion
                </button>
            </div>
        </div>
    </nav>

    <%
        Patient patient = (Patient) request.getAttribute("patient");
    %>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-8">Bienvenue sur votre Dashboard Patient</h1>
        
        <!-- Welcome Card -->
        <div class="bg-white rounded-2xl shadow-sm border border-slate-200 p-8 mb-8">
            <div class="flex items-center gap-4">
                <div class="w-16 h-16 bg-emerald-100 rounded-full flex items-center justify-center">
                    <i class="fas fa-user-circle text-emerald-600 text-3xl"></i>
                </div>
                <div>
                    <h2 class="text-2xl font-bold text-gray-900">Bienvenue <%= patient != null ? patient.getFullName() : "" %>!</h2>
                    <p class="text-gray-600">Gérez vos rendez-vous médicaux facilement</p>
                </div>
            </div>
        </div>

        <!-- Quick Stats -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="w-12 h-12 bg-emerald-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-calendar-check text-emerald-600 text-xl"></i>
                    </div>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">0</h3>
                <p class="text-sm text-gray-600">Rendez-vous à venir</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="w-12 h-12 bg-teal-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-user-md text-teal-600 text-xl"></i>
                    </div>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">0</h3>
                <p class="text-sm text-gray-600">Médecins consultés</p>
            </div>
            
            <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="w-12 h-12 bg-cyan-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-history text-cyan-600 text-xl"></i>
                    </div>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-1">0</h3>
                <p class="text-sm text-gray-600">Consultations terminées</p>
            </div>
        </div>

        <!-- Appointment Booking Section -->
        <section id="booking" class="mb-12">
            <div class="bg-white rounded-2xl shadow-sm border border-slate-200 p-8">
                <h2 class="text-2xl font-bold text-gray-900 mb-6">
                    <i class="fas fa-calendar-plus text-emerald-600 mr-2"></i>Prendre un Rendez-vous
                </h2>
                
                <!-- Step Indicator -->
                <div class="flex items-center justify-between mb-8">
                    <div class="flex items-center flex-1">
                        <div id="step1-indicator" class="w-10 h-10 bg-emerald-600 text-white rounded-full flex items-center justify-center font-semibold">1</div>
                        <div class="flex-1 h-1 bg-emerald-600 mx-2"></div>
                    </div>
                    <div class="flex items-center flex-1">
                        <div id="step2-indicator" class="w-10 h-10 bg-gray-300 text-white rounded-full flex items-center justify-center font-semibold">2</div>
                        <div class="flex-1 h-1 bg-gray-300 mx-2"></div>
                    </div>
                    <div class="flex items-center flex-1">
                        <div id="step3-indicator" class="w-10 h-10 bg-gray-300 text-white rounded-full flex items-center justify-center font-semibold">3</div>
                        <div class="flex-1 h-1 bg-gray-300 mx-2"></div>
                    </div>
                    <div class="flex items-center">
                        <div id="step4-indicator" class="w-10 h-10 bg-gray-300 text-white rounded-full flex items-center justify-center font-semibold">4</div>
                    </div>
                </div>

                <!-- Step 1: Department Selection -->
                <div id="step1" class="booking-step">
                    <h3 class="text-xl font-semibold text-gray-900 mb-4">Étape 1 : Choisissez le Département</h3>
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                        <button onclick="selectDepartment('Médecine Générale')" class="department-btn p-6 border-2 border-gray-200 rounded-xl hover:border-emerald-500 hover:bg-emerald-50 transition text-left">
                            <i class="fas fa-stethoscope text-emerald-600 text-2xl mb-3"></i>
                            <h4 class="font-semibold text-gray-900">Médecine Générale</h4>
                            <p class="text-sm text-gray-600 mt-1">Consultations générales</p>
                        </button>
                        
                        <button onclick="selectDepartment('Cardiologie')" class="department-btn p-6 border-2 border-gray-200 rounded-xl hover:border-emerald-500 hover:bg-emerald-50 transition text-left">
                            <i class="fas fa-heartbeat text-red-500 text-2xl mb-3"></i>
                            <h4 class="font-semibold text-gray-900">Cardiologie</h4>
                            <p class="text-sm text-gray-600 mt-1">Maladies du cœur</p>
                        </button>
                        
                        <button onclick="selectDepartment('Pédiatrie')" class="department-btn p-6 border-2 border-gray-200 rounded-xl hover:border-emerald-500 hover:bg-emerald-50 transition text-left">
                            <i class="fas fa-baby text-pink-500 text-2xl mb-3"></i>
                            <h4 class="font-semibold text-gray-900">Pédiatrie</h4>
                            <p class="text-sm text-gray-600 mt-1">Soins pour enfants</p>
                        </button>
                        
                        <button onclick="selectDepartment('Neurologie')" class="department-btn p-6 border-2 border-gray-200 rounded-xl hover:border-emerald-500 hover:bg-emerald-50 transition text-left">
                            <i class="fas fa-brain text-purple-500 text-2xl mb-3"></i>
                            <h4 class="font-semibold text-gray-900">Neurologie</h4>
                            <p class="text-sm text-gray-600 mt-1">Système nerveux</p>
                        </button>
                        
                        <button onclick="selectDepartment('Orthopédie')" class="department-btn p-6 border-2 border-gray-200 rounded-xl hover:border-emerald-500 hover:bg-emerald-50 transition text-left">
                            <i class="fas fa-bone text-orange-500 text-2xl mb-3"></i>
                            <h4 class="font-semibold text-gray-900">Orthopédie</h4>
                            <p class="text-sm text-gray-600 mt-1">Os et articulations</p>
                        </button>
                        
                        <button onclick="selectDepartment('Dermatologie')" class="department-btn p-6 border-2 border-gray-200 rounded-xl hover:border-emerald-500 hover:bg-emerald-50 transition text-left">
                            <i class="fas fa-hand-sparkles text-teal-500 text-2xl mb-3"></i>
                            <h4 class="font-semibold text-gray-900">Dermatologie</h4>
                            <p class="text-sm text-gray-600 mt-1">Problèmes de peau</p>
                        </button>
                    </div>
                </div>

                <!-- Step 2: Specialty Selection -->
                <div id="step2" class="booking-step hidden">
                    <h3 class="text-xl font-semibold text-gray-900 mb-4">Étape 2 : Choisissez la Spécialité</h3>
                    <div id="specialties-container" class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <!-- Will be populated dynamically -->
                    </div>
                    <button onclick="goToStep(1)" class="mt-6 px-6 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50">
                        <i class="fas fa-arrow-left mr-2"></i>Retour
                    </button>
                </div>

                <!-- Step 3: Doctor Selection -->
                <div id="step3" class="booking-step hidden">
                    <h3 class="text-xl font-semibold text-gray-900 mb-4">Étape 3 : Choisissez votre Médecin</h3>
                    <div id="doctors-container" class="space-y-4">
                        <!-- Will be populated dynamically -->
                    </div>
                    <button onclick="goToStep(2)" class="mt-6 px-6 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50">
                        <i class="fas fa-arrow-left mr-2"></i>Retour
                    </button>
                </div>

                <!-- Step 4: Date & Time Selection -->
                <div id="step4" class="booking-step hidden">
                    <h3 class="text-xl font-semibold text-gray-900 mb-4">Étape 4 : Choisissez la Date et l'Heure</h3>
                    
                    <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
                        <!-- Calendar -->
                        <div>
                            <h4 class="font-semibold text-gray-900 mb-4">Sélectionnez un jour</h4>
                            <div class="bg-gray-50 rounded-xl p-6">
                                <div class="flex items-center justify-between mb-4">
                                    <button onclick="previousMonth()" class="p-2 hover:bg-white rounded-lg">
                                        <i class="fas fa-chevron-left text-gray-600"></i>
                                    </button>
                                    <h5 id="calendar-month" class="font-semibold text-gray-900"></h5>
                                    <button onclick="nextMonth()" class="p-2 hover:bg-white rounded-lg">
                                        <i class="fas fa-chevron-right text-gray-600"></i>
                                    </button>
                                </div>
                                
                                <div class="grid grid-cols-7 gap-2 mb-2">
                                    <div class="text-center text-xs font-semibold text-gray-600 py-2">Lun</div>
                                    <div class="text-center text-xs font-semibold text-gray-600 py-2">Mar</div>
                                    <div class="text-center text-xs font-semibold text-gray-600 py-2">Mer</div>
                                    <div class="text-center text-xs font-semibold text-gray-600 py-2">Jeu</div>
                                    <div class="text-center text-xs font-semibold text-gray-600 py-2">Ven</div>
                                    <div class="text-center text-xs font-semibold text-gray-600 py-2">Sam</div>
                                    <div class="text-center text-xs font-semibold text-gray-600 py-2">Dim</div>
                                </div>
                                
                                <div id="calendar-days" class="grid grid-cols-7 gap-2">
                                    <!-- Will be populated dynamically -->
                                </div>
                            </div>
                            
                            <div class="mt-4 flex items-start gap-2 text-sm text-gray-600">
                                <i class="fas fa-info-circle text-emerald-600 mt-0.5"></i>
                                <p>Les jours grisés sont indisponibles (week-ends et jours fériés)</p>
                            </div>
                        </div>
                        
                        <!-- Time Slots -->
                        <div>
                            <h4 class="font-semibold text-gray-900 mb-4">Créneaux horaires disponibles</h4>
                            <div id="time-slots-container" class="bg-gray-50 rounded-xl p-6">
                                <p class="text-gray-500 text-center py-8">Sélectionnez d'abord un jour</p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="flex gap-4 mt-8">
                        <button onclick="goToStep(3)" class="px-6 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50">
                            <i class="fas fa-arrow-left mr-2"></i>Retour
                        </button>
                        <button id="confirm-btn" onclick="confirmAppointment()" class="px-8 py-3 bg-emerald-600 text-white font-semibold rounded-lg hover:bg-emerald-700 disabled:bg-gray-300 disabled:cursor-not-allowed" disabled>
                            <i class="fas fa-check mr-2"></i>Confirmer le Rendez-vous
                        </button>
                    </div>
                </div>
            </div>
        </section>

        <!-- Appointments Section -->
        <section id="appointments" class="mb-12">
            <div class="bg-white rounded-2xl shadow-sm border border-slate-200 p-8">
                <h2 class="text-2xl font-bold text-gray-900 mb-6">
                    <i class="fas fa-calendar-check text-emerald-600 mr-2"></i>Mes Rendez-vous
                </h2>
                
                <div class="text-center py-12">
                    <i class="fas fa-calendar-alt text-gray-300 text-6xl mb-4"></i>
                    <p class="text-gray-500">Aucun rendez-vous prévu</p>
                    <p class="text-sm text-gray-400 mt-2">(Utilisez l'API pour charger les rendez-vous)</p>
                </div>
            </div>
        </section>

        <!-- History Section -->
        <section id="history" class="mb-12">
            <div class="bg-white rounded-2xl shadow-sm border border-slate-200 p-8">
                <h2 class="text-2xl font-bold text-gray-900 mb-6">
                    <i class="fas fa-history text-emerald-600 mr-2"></i>Historique des Rendez-vous
                </h2>
                
                <div class="overflow-x-auto">
                    <table class="w-full">
                        <thead>
                            <tr class="border-b border-gray-200">
                                <th class="text-left py-4 px-4 text-sm font-semibold text-gray-700">Médecin</th>
                                <th class="text-left py-4 px-4 text-sm font-semibold text-gray-700">Spécialité</th>
                                <th class="text-left py-4 px-4 text-sm font-semibold text-gray-700">Date</th>
                                <th class="text-left py-4 px-4 text-sm font-semibold text-gray-700">Heure</th>
                                <th class="text-left py-4 px-4 text-sm font-semibold text-gray-700">Statut</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="5" class="text-center py-8 text-gray-500">
                                    Aucun historique disponible
                                    <p class="text-sm text-gray-400 mt-2">(Utilisez l'API pour charger l'historique)</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </main>

    <script>
        // Variables globales pour stocker les sélections
        let selectedDepartment = null;
        let selectedSpecialty = null;
        let selectedDoctor = null;
        let selectedDate = null;
        let selectedTime = null;
        let currentMonth = new Date().getMonth();
        let currentYear = new Date().getFullYear();
        
        // Données statiques pour les spécialités par département
        const departmentSpecialties = {
            'Médecine Générale': [
                { id: 1, name: 'Consultation Générale', description: 'Examens de routine' },
                { id: 2, name: 'Médecine Préventive', description: 'Vaccinations et dépistage' },
                { id: 3, name: 'Médecine du Sport', description: 'Blessures sportives' }
            ],
            'Cardiologie': [
                { id: 4, name: 'Cardiologie Générale', description: 'Maladies cardiaques' },
                { id: 5, name: 'Électrophysiologie', description: 'Troubles du rythme' },
                { id: 6, name: 'Cardiologie Interventionnelle', description: 'Cathétérisme' }
            ],
            'Pédiatrie': [
                { id: 7, name: 'Pédiatrie Générale', description: 'Soins pour enfants 0-18 ans' },
                { id: 8, name: 'Néonatologie', description: 'Soins pour nouveau-nés' },
                { id: 9, name: 'Pédiatrie du Développement', description: 'Croissance et développement' }
            ],
            'Neurologie': [
                { id: 10, name: 'Neurologie Générale', description: 'Troubles nerveux' },
                { id: 11, name: 'Neurologie Pédiatrique', description: 'Troubles chez enfants' },
                { id: 12, name: 'Neuro-oncologie', description: 'Tumeurs cérébrales' }
            ],
            'Orthopédie': [
                { id: 13, name: 'Orthopédie Générale', description: 'Fractures et traumatismes' },
                { id: 14, name: 'Chirurgie de la Main', description: 'Pathologies de la main' },
                { id: 15, name: 'Orthopédie Pédiatrique', description: 'Os et articulations enfants' }
            ],
            'Dermatologie': [
                { id: 16, name: 'Dermatologie Générale', description: 'Maladies de la peau' },
                { id: 17, name: 'Dermatologie Esthétique', description: 'Soins esthétiques' },
                { id: 18, name: 'Dermatologie Pédiatrique', description: 'Peau des enfants' }
            ]
        };
        
        // Données statiques pour les médecins par spécialité
        const specialtyDoctors = {
            1: [
                { id: 101, name: 'Dr. Ahmed Benali', experience: '15 ans', rating: 4.8, image: 'fa-user-md', available: true },
                { id: 102, name: 'Dr. Fatima Alaoui', experience: '12 ans', rating: 4.9, image: 'fa-user-md', available: true },
                { id: 103, name: 'Dr. Hassan Idrissi', experience: '8 ans', rating: 4.7, image: 'fa-user-md', available: true }
            ],
            4: [
                { id: 104, name: 'Dr. Mohammed Tazi', experience: '20 ans', rating: 4.9, image: 'fa-user-md', available: true },
                { id: 105, name: 'Dr. Salma Ouazzani', experience: '18 ans', rating: 4.8, image: 'fa-user-md', available: true }
            ],
            7: [
                { id: 106, name: 'Dr. Amina Bennis', experience: '14 ans', rating: 4.9, image: 'fa-user-md', available: true },
                { id: 107, name: 'Dr. Karim Benjelloun', experience: '10 ans', rating: 4.7, image: 'fa-user-md', available: true }
            ],
            10: [
                { id: 108, name: 'Dr. Rachid Lahlou', experience: '22 ans', rating: 4.9, image: 'fa-user-md', available: true },
                { id: 109, name: 'Dr. Leila Fassi', experience: '16 ans', rating: 4.8, image: 'fa-user-md', available: true }
            ],
            13: [
                { id: 110, name: 'Dr. Youssef Chraibi', experience: '19 ans', rating: 4.8, image: 'fa-user-md', available: true },
                { id: 111, name: 'Dr. Nadia Lamrani', experience: '13 ans', rating: 4.7, image: 'fa-user-md', available: true }
            ],
            16: [
                { id: 112, name: 'Dr. Omar Fakhri', experience: '11 ans', rating: 4.8, image: 'fa-user-md', available: true },
                { id: 113, name: 'Dr. Samira Kettani', experience: '9 ans', rating: 4.9, image: 'fa-user-md', available: true }
            ]
        };
        
        // Jours fériés marocains 2025 (statique pour le moment)
        const holidays = [
            '2025-01-01', '2025-01-11', '2025-04-10', '2025-05-01',
            '2025-07-30', '2025-08-14', '2025-08-20', '2025-11-06', '2025-11-18'
        ];
        
        // Créneaux horaires disponibles
        const timeSlots = [
            '09:00', '09:30', '10:00', '10:30', '11:00', '11:30',
            '14:00', '14:30', '15:00', '15:30', '16:00', '16:30', '17:00'
        ];
        
        // ===== STEP 1: Department Selection =====
        function selectDepartment(department) {
            selectedDepartment = department;
            displaySpecialties(department);
            goToStep(2);
        }
        
        // ===== STEP 2: Specialty Selection =====
        function displaySpecialties(department) {
            const specialties = departmentSpecialties[department] || [];
            const container = document.getElementById('specialties-container');
            
            if (specialties.length === 0) {
                container.innerHTML = '<p class="text-gray-500 col-span-2 text-center py-8">Aucune spécialité disponible pour ce département</p>';
                return;
            }
            
            container.innerHTML = specialties.map(function(specialty) {
                return '<button onclick="selectSpecialty(' + specialty.id + ', \'' + specialty.name + '\')" ' +
                       'class="specialty-btn p-6 border-2 border-gray-200 rounded-xl hover:border-teal-500 hover:bg-teal-50 transition text-left">' +
                       '<div class="flex items-start gap-4">' +
                       '<div class="w-12 h-12 bg-teal-100 rounded-lg flex items-center justify-center flex-shrink-0">' +
                       '<i class="fas fa-stethoscope text-teal-600 text-xl"></i>' +
                       '</div>' +
                       '<div>' +
                       '<h4 class="font-semibold text-gray-900">' + specialty.name + '</h4>' +
                       '<p class="text-sm text-gray-600 mt-1">' + specialty.description + '</p>' +
                       '</div>' +
                       '</div>' +
                       '</button>';
            }).join('');
        }
        
        function selectSpecialty(specialtyId, specialtyName) {
            selectedSpecialty = { id: specialtyId, name: specialtyName };
            displayDoctors(specialtyId);
            goToStep(3);
        }
        
        // ===== STEP 3: Doctor Selection =====
        function displayDoctors(specialtyId) {
            const doctors = specialtyDoctors[specialtyId] || specialtyDoctors[1]; // Fallback to general doctors
            const container = document.getElementById('doctors-container');
            
            container.innerHTML = doctors.map(function(doctor) {
                return '<div class="doctor-card p-6 border-2 border-gray-200 rounded-xl hover:border-emerald-500 hover:shadow-lg transition cursor-pointer" ' +
                       'onclick="selectDoctor(' + doctor.id + ', \'' + doctor.name + '\')">' +
                       '<div class="flex items-center gap-4">' +
                       '<div class="w-16 h-16 bg-emerald-100 rounded-full flex items-center justify-center flex-shrink-0">' +
                       '<i class="fas ' + doctor.image + ' text-emerald-600 text-2xl"></i>' +
                       '</div>' +
                       '<div class="flex-1">' +
                       '<h4 class="font-semibold text-gray-900 text-lg">' + doctor.name + '</h4>' +
                       '<p class="text-sm text-gray-600 mt-1">' +
                       '<i class="fas fa-briefcase text-teal-600 mr-2"></i>' + doctor.experience + ' d\'expérience' +
                       '</p>' +
                       '<div class="flex items-center gap-2 mt-2">' +
                       '<div class="flex text-yellow-400">' +
                       '<i class="fas fa-star"></i>'.repeat(Math.floor(doctor.rating)) +
                       '</div>' +
                       '<span class="text-sm text-gray-600">' + doctor.rating + '/5</span>' +
                       '</div>' +
                       '</div>' +
                       '<div>' +
                       '<span class="px-3 py-1 bg-emerald-100 text-emerald-700 text-xs font-medium rounded-full">' +
                       '<i class="fas fa-check-circle mr-1"></i>Disponible' +
                       '</span>' +
                       '</div>' +
                       '</div>' +
                       '</div>';
            }).join('');
        }
        
        function selectDoctor(doctorId, doctorName) {
            selectedDoctor = { id: doctorId, name: doctorName };
            initializeCalendar();
            goToStep(4);
        }
        
        // ===== STEP 4: Date & Time Selection =====
        function initializeCalendar() {
            renderCalendar();
        }
        
        function renderCalendar() {
            const monthNames = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin',
                              'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
            
            document.getElementById('calendar-month').textContent = monthNames[currentMonth] + ' ' + currentYear;
            
            const firstDay = new Date(currentYear, currentMonth, 1).getDay();
            const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            
            const calendarDays = document.getElementById('calendar-days');
            calendarDays.innerHTML = '';
            
            // Adjust first day (Monday = 0)
            const adjustedFirstDay = firstDay === 0 ? 6 : firstDay - 1;
            
            // Empty cells before first day
            for (let i = 0; i < adjustedFirstDay; i++) {
                calendarDays.innerHTML += '<div></div>';
            }
            
            // Days of the month
            for (let day = 1; day <= daysInMonth; day++) {
                const date = new Date(currentYear, currentMonth, day);
                const dateString = formatDate(date);
                const dayOfWeek = date.getDay();
                const isPast = date < today;
                const isWeekend = dayOfWeek === 0 || dayOfWeek === 6;
                const isHoliday = holidays.indexOf(dateString) !== -1;
                const isUnavailable = isPast || isWeekend || isHoliday;
                
                let className = 'day-cell p-3 text-center rounded-lg cursor-pointer transition ';
                if (isUnavailable) {
                    className += 'bg-gray-200 text-gray-400 cursor-not-allowed';
                } else {
                    className += 'bg-white hover:bg-emerald-100 hover:text-emerald-700 border border-gray-200';
                }
                
                if (selectedDate === dateString) {
                    className += ' !bg-emerald-600 !text-white';
                }
                
                const onclick = isUnavailable ? '' : 'onclick="selectDate(\'' + dateString + '\')"';
                
                calendarDays.innerHTML += '<div class="' + className + '" ' + onclick + '>' + day + '</div>';
            }
        }
        
        function previousMonth() {
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
            renderCalendar();
        }
        
        function nextMonth() {
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
            renderCalendar();
        }
        
        function selectDate(dateString) {
            selectedDate = dateString;
            renderCalendar();
            displayTimeSlots();
        }
        
        function displayTimeSlots() {
            const container = document.getElementById('time-slots-container');
            
            if (!selectedDoctor || !selectedDate) {
                container.innerHTML = '<p class="text-gray-500 text-center py-8">Veuillez sélectionner un médecin et une date</p>';
                return;
            }
            
            // Show loading
            container.innerHTML = '<div class="text-center py-8"><i class="fas fa-spinner fa-spin text-emerald-600 text-2xl"></i><p class="text-gray-600 mt-2">Chargement des créneaux...</p></div>';
            
            // Fetch available time slots via AJAX from API
            fetch('/clinic-jee/api/patient/appointments?action=creneauxDisponibles&doctorId=' + encodeURIComponent(selectedDoctor.id) + '&date=' + encodeURIComponent(selectedDate))
            .then(function(response) {
                return response.json();
            })
            .then(function(data) {
                if (data.success && data.availabilities && data.availabilities.length > 0) {
                    container.innerHTML = '<div class="grid grid-cols-2 md:grid-cols-3 gap-3">' +
                        data.availabilities.map(function(slot) {
                            var time = slot.heure;
                            var className = 'time-slot p-3 text-center border-2 border-gray-200 rounded-lg hover:border-emerald-500 hover:bg-emerald-50 cursor-pointer transition';
                            return '<button class="' + className + '" onclick="selectTime(\'' + time + '\')">' +
                                   '<i class="fas fa-clock text-emerald-600 mr-2"></i>' + time +
                                   '</button>';
                        }).join('') +
                        '</div>';
                } else {
                    container.innerHTML = '<p class="text-gray-500 text-center py-8">Aucun créneau disponible pour cette date</p>';
                }
            })
            .catch(function(error) {
                console.error('Error fetching availabilities:', error);
                container.innerHTML = '<p class="text-red-500 text-center py-8">Erreur lors du chargement des créneaux</p>';
            });
        }
        
        function selectTime(time) {
            selectedTime = time;
            
            // Update visual feedback
            const timeButtons = document.querySelectorAll('.time-slot');
            timeButtons.forEach(function(btn) {
                btn.classList.remove('!bg-emerald-600', '!text-white', '!border-emerald-600');
            });
            
            event.target.closest('button').classList.add('!bg-emerald-600', '!text-white', '!border-emerald-600');
            
            // Enable confirm button
            document.getElementById('confirm-btn').disabled = false;
        }
        
        function confirmAppointment() {
            if (!selectedDepartment || !selectedSpecialty || !selectedDoctor || !selectedDate || !selectedTime) {
                alert('Veuillez compléter toutes les étapes');
                return;
            }
            
            const summary = 'Récapitulatif de votre rendez-vous:\n\n' +
                          'Département: ' + selectedDepartment + '\n' +
                          'Spécialité: ' + selectedSpecialty.name + '\n' +
                          'Médecin: ' + selectedDoctor.name + '\n' +
                          'Date: ' + formatDateDisplay(selectedDate) + '\n' +
                          'Heure: ' + selectedTime + '\n\n' +
                          'Confirmez-vous ce rendez-vous?';
            
            if (confirm(summary)) {
                // Here you would send the data to the backend
                alert('Rendez-vous confirmé avec succès! Vous recevrez une confirmation par email.');
                
                // Reset form
                resetBookingForm();
            }
        }
        
        function resetBookingForm() {
            selectedDepartment = null;
            selectedSpecialty = null;
            selectedDoctor = null;
            selectedDate = null;
            selectedTime = null;
            document.getElementById('confirm-btn').disabled = true;
            goToStep(1);
        }
        
        // ===== Navigation between steps =====
        function goToStep(stepNumber) {
            // Hide all steps
            document.querySelectorAll('.booking-step').forEach(function(step) {
                step.classList.add('hidden');
            });
            
            // Show selected step
            document.getElementById('step' + stepNumber).classList.remove('hidden');
            
            // Update step indicators
            for (let i = 1; i <= 4; i++) {
                const indicator = document.getElementById('step' + i + '-indicator');
                const connectorIndex = i;
                
                if (i < stepNumber) {
                    // Completed steps
                    indicator.classList.remove('bg-gray-300', 'bg-emerald-600');
                    indicator.classList.add('bg-emerald-600');
                    if (i < 4) {
                        indicator.nextElementSibling.classList.remove('bg-gray-300');
                        indicator.nextElementSibling.classList.add('bg-emerald-600');
                    }
                } else if (i === stepNumber) {
                    // Current step
                    indicator.classList.remove('bg-gray-300');
                    indicator.classList.add('bg-emerald-600');
                } else {
                    // Future steps
                    indicator.classList.remove('bg-emerald-600');
                    indicator.classList.add('bg-gray-300');
                    if (i < 4) {
                        indicator.nextElementSibling.classList.remove('bg-emerald-600');
                        indicator.nextElementSibling.classList.add('bg-gray-300');
                    }
                }
            }
            
            // Scroll to top of booking section
            document.getElementById('booking').scrollIntoView({ behavior: 'smooth' });
        }
        
        // ===== Utility functions =====
        function formatDate(date) {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            return year + '-' + month + '-' + day;
        }
        
        function formatDateDisplay(dateString) {
            const date = new Date(dateString);
            const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
            return date.toLocaleDateString('fr-FR', options);
        }
        
        function logout() {
            if (confirm('Êtes-vous sûr de vouloir vous déconnecter?')) {
                window.location.href = '<%= request.getContextPath() %>/auth/login';
            }
        }
    </script>
</body>
</html>

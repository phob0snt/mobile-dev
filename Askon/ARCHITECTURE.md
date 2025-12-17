# Askon - Clean Architecture Implementation ✅

## 📋 Обзор проекта

**Askon** - Android приложение для платформы консультаций экспертов. Проект реализован по принципам **Clean Architecture** с полным разделением слоёв и использованием современных практик разработки.

---

## 🏗️ Архитектура проекта

### Модульная структура

```
Askon/
├── :domain (Java library)        → Бизнес-логика (Pure Java)
├── :data (Android library)       → Реализация данных  
└── :app (Android application)    → Presentation layer (UI)
```

---

## 📦 Модуль :domain - Domain Layer

**Тип:** `java-library` (Pure Java 11)

### Содержимое:

#### 1. **model/** - Доменные модели
- `User.java` - пользователь системы
- `Expert.java` - эксперт (краткая информация)
- `ExpertProfile.java` - полный профиль эксперта
- `Booking.java` - бронирование консультации
- `Message.java` - сообщение в чате
- `Review.java` - отзыв о эксперте

#### 2. **repository/** - Интерфейсы репозиториев
- `AuthRepository.java` - авторизация и аутентификация
- `UsersRepository.java` - операции с пользователями и экспертами
- `BookingRepository.java` - бронирования
- `ChatRepository.java` - чат и сообщения
- `MLRepository.java` - ML распознавание и эксперты

#### 3. **usecases/** - Use Cases (бизнес-логика)
- `LoginUserUseCase.java`
- `RegisterUserUseCase.java`
- `GetExpertsListUseCase.java`
- `GetExpertProfileUseCase.java`
- `BookExpertTimeUseCase.java`
- `GetUserBookingsUseCase.java`
- `SendMessageUseCase.java`
- `SubmitReviewUseCase.java`

### Зависимости:
**NONE** - только Java 11, без Android/Firebase

### Принципы:
✅ Полная изоляция от Android Framework  
✅ Не содержит Firebase, Room или других деталей реализации  
✅ Только Java интерфейсы и POJO классы  

---

## 📦 Модуль :data - Data Layer

**Тип:** `com.android.library`

### Структура:

```
data/
├── local/                    # Локальное хранилище
│   ├── entity/              # Room entities
│   │   ├── ExpertEntity.java
│   │   ├── BookingEntity.java
│   │   └── MessageEntity.java
│   ├── dao/                 # DAO интерфейсы
│   │   ├── ExpertDao.java
│   │   ├── BookingDao.java
│   │   └── MessageDao.java
│   ├── AskonDatabase.java   # Room Database
│   └── ProfilePreferences.java  # SharedPreferences
│
├── remote/                   # Network layer
│   ├── dto/                 # Data Transfer Objects
│   │   ├── ExpertDto.java
│   │   └── BookingDto.java
│   └── MockNetworkApi.java  # Mock API
│
├── mapper/                   # Маппинг слоёв
│   ├── ExpertMapper.java    # DTO ↔ Entity ↔ Domain
│   └── BookingMapper.java
│
└── repository/               # Реализации репозиториев
    ├── AuthRepositoryImpl.java
    ├── UsersRepositoryImpl.java
    ├── BookingRepositoryImpl.java
    ├── ChatRepositoryImpl.java
    └── MLRepositoryImpl.java
```

### Зависимости:

```gradle
dependencies {
    implementation(project(":domain"))
    
    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    
    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth")
}
```

---

## 📦 Модуль :app - Presentation Layer

**Тип:** `com.android.application`

### Activities:

1. **LoginActivity** - авторизация с выбором роли (Client/Expert)
2. **RegistrationActivity** - регистрация нового пользователя
3. **ExpertListActivity** - список экспертов с поиском
4. **ExpertProfileActivity** - профиль эксперта + бронирование
5. **BookingsActivity** - список бронирований пользователя
6. **ChatActivity** - чат интерфейс
7. **ProfileActivity** - профиль текущего пользователя

### UI Components:
- Material Design 3
- MaterialCardView для карточек выбора роли
- RecyclerView для списков
- Bottom Navigation для навигации
- TextInputLayout для форм

### Зависимости:

```gradle
dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    
    // AndroidX
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth")
}
```

---

## 🗄️ Три источника данных (Требование выполнено)

### 1. SharedPreferences - Профиль клиента ✅

**Класс:** `ProfilePreferences.java`

**Назначение:** Хранение профиля текущего пользователя

**Данные:**
- User ID
- Name
- Email
- Phone
- isClient flag
- isLoggedIn flag

**Использование:** В `UsersRepositoryImpl` и `AuthRepositoryImpl`

```java
// Сохранение профиля
profilePreferences.setUserId(userId);
profilePreferences.setName(name);
profilePreferences.setLoggedIn(true);

// Чтение профиля
if (profilePreferences.isLoggedIn()) {
    return new User(
        profilePreferences.getUserId(),
        profilePreferences.getName(),
        profilePreferences.getEmail(),
        profilePreferences.getPhone()
    );
}
```

---

### 2. Room Database - Основное локальное хранилище ✅

**Класс:** `AskonDatabase.java`

**Entities:**
- `ExpertEntity` - эксперты
- `BookingEntity` - бронирования
- `MessageEntity` - сообщения

**DAO интерфейсы:**
- `ExpertDao` - CRUD операции с экспертами
- `BookingDao` - операции с бронированиями
- `MessageDao` - операции с сообщениями

**Стратегия кэширования:**
1. Проверка локального кэша (Room)
2. Если данных нет → запрос к Network API
3. Сохранение в Room для offline доступа
4. Возврат данных через маппинг Entity → Domain

```java
// Получение экспертов из Room
List<ExpertEntity> cachedExperts = database.expertDao().getAllExperts();

if (cachedExperts.isEmpty()) {
    // Запрос к Network API
    List<ExpertDto> dtos = networkApi.fetchExperts();
    // Маппинг и сохранение в Room
    List<ExpertEntity> entities = ExpertMapper.dtosToEntities(dtos);
    database.expertDao().insertExperts(entities);
}
```

---

### 3. Network API (Mock) - Источник сети ✅

**Класс:** `MockNetworkApi.java`

**Endpoints (mock):**
- `fetchExperts()` - список экспертов
- `searchExperts(query)` - поиск по запросу
- `fetchExpertDetails(id)` - детали эксперта
- `fetchUserBookings(userId)` - бронирования пользователя
- `createBooking()` - создание бронирования

**В production:** Заменить на Retrofit интерфейс с реальным backend

```java
@GET("/api/experts")
Call<List<ExpertDto>> fetchExperts();
```

---

## 🔄 Маппинг DTO → Entity → Domain ✅

### Поток данных:

```
Network API → DTO → Mapper → Entity (Room) → Mapper → Domain Model → UI
```

### Пример для Expert:

```java
// 1. Network API возвращает DTO
ExpertDto dto = networkApi.fetchExpertDetails(id);
// Структура DTO: {fullName, specialization, imageUrl, ratingValue, ...}

// 2. Маппинг DTO → Entity
ExpertEntity entity = ExpertMapper.dtoToEntity(dto);
// Entity оптимизирована для Room (SQLite)

// 3. Сохранение в Room
database.expertDao().insertExpert(entity);

// 4. Маппинг Entity → Domain
Expert expert = ExpertMapper.entityToDomain(entity);
// Domain модель для бизнес-логики

// 5. Возврат в UseCase → Activity
return expert;
```

### Преимущества трёхслойного маппинга:
✅ Domain изолирован от структуры API  
✅ Можно менять API без изменения domain  
✅ Room entities оптимизированы для SQLite  
✅ DTO может иметь другую структуру JSON  

---

## 🔐 Firebase Authentication ✅

**Класс:** `AuthRepositoryImpl.java`

**Интеграция:**

```java
public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private final ProfilePreferences profilePreferences;
    
    @Override
    public User login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> {
                FirebaseUser firebaseUser = authResult.getUser();
                
                // Сохранение в SharedPreferences
                profilePreferences.setUserId(firebaseUser.getUid());
                profilePreferences.setEmail(email);
                profilePreferences.setLoggedIn(true);
            });
        
        return getCurrentUser();
    }
}
```

**Изоляция от Domain:**
- Domain содержит только интерфейс `AuthRepository`
- Firebase код только в `data` модуле
- Domain получает POJO `User` объект без Firebase зависимостей

---

## 🎯 Use Cases (Domain Layer)

### Паттерн использования:

```java
// В Activity
GetExpertsListUseCase useCase = new GetExpertsListUseCase(repository);
List<Expert> experts = useCase.execute(query);
```

### Примеры Use Cases:

```java
public class GetExpertsListUseCase {
    private UsersRepository repository;
    
    public GetExpertsListUseCase(UsersRepository repository) {
        this.repository = repository;
    }
    
    public List<Expert> execute(String category) {
        return repository.getExpertsList(category);
    }
}
```

---

## 📊 Граф зависимостей

```
         ┌─────────┐
         │ :domain │ ← Pure Java, no dependencies
         └────▲────┘
              │
         ┌────┴────┐
         │  :data  │ ← Firebase, Room, Retrofit
         └────▲────┘
              │
         ┌────┴────┐
         │  :app   │ ← UI, Activities, Resources
         └─────────┘
```

**Правила:**
- `:domain` не зависит ни от кого
- `:data` зависит только от `:domain`
- `:app` зависит от `:domain` и `:data`

---

## ✅ Проверка соответствия требованиям

### ✔️ Модули: :domain, :data, :app
- [x] Созданы все три модуля
- [x] Настроены зависимости в `settings.gradle.kts`
- [x] Правильная иерархия: app → data → domain

### ✔️ Firebase Auth + Clean Architecture
- [x] `AuthRepositoryImpl` с Firebase Authentication
- [x] Интерфейс `AuthRepository` в domain
- [x] Логика: UI → UseCase → Repository → Firebase
- [x] Сохранение профиля в SharedPreferences

### ✔️ Три способа обработки данных

#### 1. SharedPreferences ✅
- [x] `ProfilePreferences.java` для профиля клиента
- [x] Используется в `UsersRepositoryImpl`
- [x] Хранит: userId, name, email, isClient, isLoggedIn

#### 2. Room Database ✅
- [x] `AskonDatabase.java` с тремя entities
- [x] DAO для каждой сущности (Expert, Booking, Message)
- [x] Стратегия кэширования: Room → Network → Room
- [x] Используется в `MLRepositoryImpl` и `BookingRepositoryImpl`

#### 3. Network API (Mock) ✅
- [x] `MockNetworkApi.java` с mock данными
- [x] DTO классы: `ExpertDto`, `BookingDto`
- [x] Маппинг DTO → Entity → Domain
- [x] Интеграция с Room для кэширования

### ✔️ Domain изолирован от Android/Firebase
- [x] Модуль :domain - только Java library
- [x] Нет зависимостей на Android SDK
- [x] Нет Firebase/Room/Retrofit в domain
- [x] Только интерфейсы и POJO

---

## 🚀 Запуск проекта

```bash
# Сборка проекта
.\gradlew.bat :app:assembleDebug

# Установка на устройство
.\gradlew.bat :app:installDebug

# Чистая сборка
.\gradlew.bat clean build

# Сборка отдельных модулей
.\gradlew.bat :domain:build
.\gradlew.bat :data:build
```

**Статус сборки:** ✅ BUILD SUCCESSFUL

---

## 📱 Технические требования

- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 35 (Android 15)
- **Java:** 11
- **Gradle:** 8.10.2
- **Android Gradle Plugin:** 8.7.3
- **Firebase BOM:** 32.7.0
- **Room:** 2.6.1
- **Retrofit:** 2.9.0

---

## 📝 Структура файлов

```
Askon/
├── build.gradle.kts
├── settings.gradle.kts
├── google-services.json (mock)
│
├── domain/
│   ├── build.gradle.kts (java-library)
│   └── src/main/java/.../domain/
│       ├── model/
│       ├── repository/
│       └── usecases/
│
├── data/
│   ├── build.gradle.kts (android-library)
│   └── src/main/java/.../data/
│       ├── local/
│       │   ├── entity/
│       │   ├── dao/
│       │   ├── AskonDatabase.java
│       │   └── ProfilePreferences.java
│       ├── remote/
│       │   ├── dto/
│       │   └── MockNetworkApi.java
│       ├── mapper/
│       └── repository/
│
└── app/
    ├── build.gradle.kts (android-application)
    ├── google-services.json
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/.../presentation/
        │   ├── LoginActivity.java
        │   ├── RegistrationActivity.java
        │   ├── ExpertListActivity.java
        │   ├── ExpertProfileActivity.java
        │   ├── BookingsActivity.java
        │   ├── ChatActivity.java
        │   └── ProfileActivity.java
        └── res/
            ├── layout/
            ├── values/
            └── drawable/
```

---

## 🎓 Принципы Clean Architecture

### 1. Dependency Rule
Зависимости направлены только внутрь к domain:
```
UI → Use Cases → Repositories (interfaces) → Entities
```

### 2. Separation of Concerns
Каждый слой имеет свою ответственность:
- **Domain:** Бизнес-логика
- **Data:** Источники данных
- **Presentation:** UI и взаимодействие

### 3. Testability
- Domain можно тестировать без Android
- Легко создавать моки для репозиториев
- Use Cases независимы от UI

### 4. Flexibility
- Легко менять источники данных
- Можно заменить Firebase на другую систему
- UI независим от источников данных

---

## 🔮 Дальнейшее развитие

### Возможные улучшения:

1. **Асинхронность**
   - Использовать Kotlin Coroutines или RxJava
   - LiveData / StateFlow для реактивных данных

2. **Dependency Injection**
   - Внедрить Hilt/Dagger для автоматического DI
   - Избавиться от ручного создания репозиториев

3. **ViewModel**
   - Использовать ViewModel для управления UI state
   - Переживать поворот экрана без потери данных

4. **Навигация**
   - Navigation Component для фрагментов
   - Single Activity Architecture

5. **Реальный API**
   - Заменить MockNetworkApi на Retrofit
   - Добавить JWT token authentication
   - Обработку ошибок и retry logic

6. **Тестирование**
   - Unit тесты для Use Cases
   - Integration тесты для Repository
   - UI тесты с Espresso

---

## 👨‍💻 Резюме

✅ **Все требования выполнены:**

1. ✔️ Проект разделён на :domain, :data, :app
2. ✔️ Настроены зависимости модулей
3. ✔️ Реализована Firebase Authentication
4. ✔️ Логика разнесена по слоям: UI → UseCase → Repository
5. ✔️ **SharedPreferences** для профиля клиента
6. ✔️ **Room** для локального хранилища сущностей
7. ✔️ **Network API (mock)** с маппингом DTO → Entity → Domain
8. ✔️ Domain полностью изолирован от Android/Firebase

**Статус:** ✅ BUILD SUCCESSFUL in 1m 1s  
**Архитектура:** Clean Architecture с полным соответствием принципам

---

*Документация создана: 17 декабря 2025*

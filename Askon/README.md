# Askon - Платформа для консультаций с экспертами

Android-приложение для бронирования времени экспертов и получения консультаций.

## 🏗️ Архитектура

Проект использует **Clean Architecture** с разделением на три модуля:

```
:domain  →  :data  →  :app
```

- **:domain** - Чистая бизнес-логика (Pure Java, без Android)
- **:data** - Слой данных (Android Library, реализации репозиториев)
- **:app** - Презентационный слой (UI, Activity, ViewModels)

### Структура модулей

```
Askon/
├── domain/          # Pure Java Module
│   ├── model/       # Entities: User, Expert, Booking, etc.
│   ├── repository/  # Repository interfaces
│   └── usecases/    # Business logic
│
├── data/            # Android Library
│   └── repository/  # Repository implementations
│
└── app/             # Android Application
    └── presentation/ # UI: Activities, Fragments, ViewModels
```

## 📋 Функциональность

### Реализовано
- ✅ Регистрация и вход пользователей
- ✅ Просмотр списка экспертов
- ✅ Просмотр профиля эксперта
- ✅ Бронирование времени эксперта
- ✅ Чат с экспертами
- ✅ Отправка отзывов
- ✅ Распознавание фото (ML)

### Domain Layer
**Entities (6):**
- User
- Expert  
- ExpertProfile
- Booking
- Message
- Review

**Repositories (4 интерфейса):**
- UsersRepository
- BookingRepository
- ChatRepository
- MLRepository

**Use Cases (8):**
- LoginUserUseCase
- RegisterUserUseCase
- GetExpertsListUseCase
- GetExpertProfileUseCase
- BookExpertTimeUseCase
- SendMessageUseCase
- SubmitReviewUseCase
- RecognizePhotoUseCase

## 🛠️ Технологии

- **Язык:** Java 11
- **Build System:** Gradle 8.10.2
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 35 (Android 15)
- **Архитектура:** Clean Architecture, Multi-module

### Текущие зависимости
- AndroidX AppCompat
- Material Design Components
- JUnit (тестирование)
- Espresso (UI тесты)

### Планируемые зависимости
- Room (локальная БД)
- Retrofit (сетевые запросы)
- Hilt/Dagger (Dependency Injection)
- Navigation Component
- ViewModel, LiveData/Flow

## 🚀 Быстрый старт

### Требования
- JDK 17+
- Android Studio Koala | 2024.1.1+
- Android SDK 35

### Сборка проекта

```bash
# Клонирование репозитория
git clone <repository-url>
cd Askon

# Сборка проекта
./gradlew build

# Запуск приложения
./gradlew :app:installDebug
```

### Сборка отдельных модулей

```bash
# Domain модуль (Pure Java)
./gradlew :domain:build

# Data модуль (Android Library)
./gradlew :data:build

# App модуль (Android App)
./gradlew :app:assembleDebug
```

## 📖 Документация

- **[ARCHITECTURE.md](ARCHITECTURE.md)** - Подробное описание архитектуры
- **[ARCHITECTURE_DIAGRAM.txt](ARCHITECTURE_DIAGRAM.txt)** - Визуальные диаграммы
- **[QUICK_START.md](QUICK_START.md)** - Гайд для разработчиков
- **[MIGRATION_COMPLETE.md](MIGRATION_COMPLETE.md)** - История миграции

## 🧪 Тестирование

```bash
# Запуск всех тестов
./gradlew test

# Тесты domain модуля (без эмулятора!)
./gradlew :domain:test

# Инструментальные тесты (требуется эмулятор)
./gradlew :app:connectedAndroidTest
```

## 📦 Модули

### :domain (Pure Java)
Чистая бизнес-логика без зависимостей от Android.
- Entities/Models
- Repository интерфейсы  
- Use Cases
- Легко тестируется

### :data (Android Library)
Слой работы с данными.
- Реализации Repository
- DAO (Room)
- API клиенты (Retrofit)
- Mappers

### :app (Android Application)
UI и презентационный слой.
- Activities/Fragments
- ViewModels
- Navigation
- Dependency Injection

## 🎯 Принципы

1. **Dependency Rule**: Зависимости направлены к core (domain)
2. **Single Responsibility**: Каждый модуль отвечает за свою часть
3. **Testability**: Domain тестируется без Android
4. **Independence**: Легко заменить любой слой
5. **Scalability**: Модули можно развивать независимо

## 🔄 Граф зависимостей

```
      ┌─────────┐
      │   app   │  (Android Application)
      └────┬────┘
           │
      ┌────┴────┐
      │         │
  ┌───▼───┐ ┌──▼────┐
  │ data  │ │domain │  (Pure Java)
  └───┬───┘ └───────┘
      │
  ┌───▼───┐
  │domain │
  └───────┘
```

## 📝 Следующие шаги

- [ ] Добавить Hilt для DI
- [ ] Реализовать ViewModels
- [ ] Добавить Room для локальной БД
- [ ] Настроить Retrofit для API
- [ ] Добавить Navigation Component
- [ ] Написать Unit тесты
- [ ] Добавить UI тесты
- [ ] Настроить CI/CD

## 👥 Контрибьюторы

Проект создан с использованием Clean Architecture принципов.

## 📄 Лицензия

[Укажите лицензию]

---

**Создано:** Декабрь 2025  
**Версия:** 1.0  
**Build:** Gradle 8.10.2  
**Java:** 11

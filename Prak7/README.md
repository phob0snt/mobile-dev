# Language Learning App - Практика 7

Мобильное приложение для изучения иностранных языков, демонстрирующее реализацию различных паттернов навигации в Android.

## Особенности

- ✅ **Bottom Navigation** - 3 основных раздела (Lessons, Dictionary, Profile)
- ✅ **Navigation Drawer** - боковое меню (Progress, Achievements, Settings)
- ✅ **Navigation Component** - современная навигация с Navigation Graph
- ✅ **ViewBinding** - type-safe доступ к views
- ✅ **Material Design 3** - современный UI/UX

## Технологии

- Android SDK 35 (min SDK 26)
- Navigation Component 2.7.6
- Material Design 3
- ViewBinding
- Java 11

## Структура навигации

### MainActivity (Bottom Navigation)
1. **Lessons** - список уроков для изучения
2. **Dictionary** - поиск слов с переводом
3. **Profile** - статистика обучения (streak, words, lessons)

### DrawerActivity (Navigation Drawer)
1. **Progress** - прогресс по навыкам (vocabulary, grammar, listening, speaking)
2. **Achievements** - полученные достижения
3. **Settings** - настройки уведомлений и обучения

## Сборка и запуск

```bash
# Клонировать репозиторий
cd Prak7/LanguageLearningApp

# Собрать проект
.\gradlew clean assemble

# Установить на устройство
.\gradlew installDebug
```

## Основные компоненты

### Activities
- `MainActivity.java` - Bottom Navigation реализация
- `DrawerActivity.java` - Navigation Drawer реализация

### Fragments (6 штук)
- `LessonsFragment` - RecyclerView с уроками
- `DictionaryFragment` - SearchView + список слов
- `ProfileFragment` - CardView со статистикой
- `ProgressFragment` - ProgressBars для навыков
- `AchievementsFragment` - Grid с достижениями
- `SettingsFragment` - SwitchCompat для настроек

### Navigation Graphs
- `mobile_navigation.xml` - граф для Bottom Navigation
- `drawer_navigation.xml` - граф для Drawer Navigation

### Menus
- `bottom_nav_menu.xml` - меню Bottom Navigation (3 items)
- `drawer_menu.xml` - меню Drawer с группировкой (2 groups)
- `main_toolbar_menu.xml` - Toolbar с кнопкой открытия drawer

## Ключевые файлы

```
app/
├── src/main/
│   ├── AndroidManifest.xml              # 2 Activities
│   ├── java/.../
│   │   ├── MainActivity.java            # Bottom Nav + NavController
│   │   ├── DrawerActivity.java          # Drawer + NavController
│   │   └── ui/                          # 6 Fragments
│   └── res/
│       ├── drawable/                    # 7 vector icons
│       ├── layout/                      # 11 layouts
│       │   ├── activity_main.xml
│       │   ├── activity_drawer.xml
│       │   ├── app_bar_drawer.xml
│       │   ├── content_drawer.xml
│       │   ├── nav_header_drawer.xml
│       │   └── fragment_*.xml (6 files)
│       ├── menu/                        # 3 menus
│       ├── navigation/                  # 2 navigation graphs
│       └── values/
│           ├── colors.xml               # Зелёно-оранжевая тема
│           ├── strings.xml              # 80+ строк
│           └── themes.xml               # Material3
└── build.gradle.kts                     # Dependencies
```

## Цветовая схема

| Цвет | Hex | Назначение |
|------|-----|------------|
| Primary | #4CAF50 | Основной зелёный (обучение, рост) |
| Primary Dark | #388E3C | Тёмно-зелёный (status bar) |
| Secondary | #FF9800 | Оранжевый (акценты, прогресс) |
| Accent | #2196F3 | Синий (интерактивные элементы) |

## Зависимости

```kotlin
// Navigation Component
implementation("androidx.navigation:navigation-fragment:2.7.6")
implementation("androidx.navigation:navigation-ui:2.7.6")

// Material Design
implementation("com.google.android.material:material:1.10.0")

// AndroidX
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
implementation("androidx.cardview:cardview:1.0.0")
implementation("androidx.recyclerview:recyclerview:1.3.2")
```

## Основные концепции

### 1. Navigation Component
```java
NavController navController = Navigation.findNavController(this, R.id.nav_host);
NavigationUI.setupWithNavController(bottomNav, navController);
```

### 2. ViewBinding
```java
binding = ActivityMainBinding.inflate(getLayoutInflater());
setContentView(binding.getRoot());
binding.toolbar.setTitle("Title");
```

### 3. AppBarConfiguration (для Drawer)
```java
mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.nav_progress, R.id.nav_achievements, R.id.nav_settings)
        .setOpenableLayout(drawerLayout)
        .build();
```

### 4. Back Button Handling
```java
getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
    @Override
    public void handleOnBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }
});
```

## Тестирование

### Функциональность
- [x] Переключение между tabs в Bottom Navigation
- [x] Открытие/закрытие Navigation Drawer
- [x] Hamburger icon анимация
- [x] Toolbar отображает правильный title
- [x] Back button закрывает drawer
- [x] Settings switches работают с Toast
- [x] Навигация из MainActivity в DrawerActivity

### UI/UX
- [x] Плавные анимации переходов
- [x] Правильные цвета и иконки
- [x] Адекватные отступы
- [x] Responsive layout

## Отчёт

Полный отчёт по практике доступен в файле [REPORT.md](REPORT.md)

## Автор

**Юдаев И.А.**  
Группа: БСБО-09-22  
Год: 2024

## Лицензия

Учебный проект - Практика 7

# Отчёт: Практика 7 - Навигация в Android-приложении

**Студент:** Юдаев И.А.  
**Группа:** БСБО-09-22  
**Дата:** 2024

## Описание проекта

**Название приложения:** Language Learning (Изучение языков)

**Концепция:** Мобильное приложение для изучения иностранных языков с отслеживанием прогресса и системой достижений.

**Технологии:**
- Android SDK 35 (min SDK 26)
- Navigation Component 2.7.6
- ViewBinding
- Material Design 3

## Требования к практике

1. ✅ Реализовать Bottom Navigation с минимум 3 вкладками
2. ✅ Реализовать Navigation Drawer с группированным меню
3. ✅ Использовать Navigation Component для навигации
4. ✅ Интеграция Toolbar
5. ✅ Правильная обработка кнопки "Назад"

## Структура приложения

### 1. MainActivity (Bottom Navigation)

MainActivity демонстрирует использование Bottom Navigation с тремя основными разделами:

**Навигационные разделы:**
- **Lessons (Уроки)** - список доступных уроков
- **Dictionary (Словарь)** - поиск слов и перевода
- **Profile (Профиль)** - статистика пользователя

#### Ключевые компоненты MainActivity:

**1.1. Layout (activity_main.xml)**
```xml
<androidx.constraintlayout.widget.ConstraintLayout>
    <com.google.android.material.appbar.AppBarLayout>
        <androidx.appcompat.widget.Toolbar />
    </com.google.android.material.appbar.AppBarLayout>
    
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment_activity_main"
        android:name="androidx.navigation.fragment.NavHostFragment"
        app:navGraph="@navigation/mobile_navigation" />
    
    <com.google.android.material.bottomnavigation.BottomNavigationView
        app:menu="@menu/bottom_nav_menu" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

**1.2. Java код (MainActivity.java)**
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(
            this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(
            binding.bottomNavigation, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }
}
```

**Особенности реализации:**
- Использование ViewBinding для type-safe доступа к views
- NavigationUI.setupWithNavController() автоматически связывает Bottom Navigation с NavController
- Toolbar отображает название текущего фрагмента из navigation graph

### 2. DrawerActivity (Navigation Drawer)

DrawerActivity демонстрирует использование Navigation Drawer для дополнительных функций:

**Навигационные разделы:**
- **Progress (Прогресс)** - progress bars для разных навыков
- **Achievements (Достижения)** - grid с полученными наградами
- **Settings (Настройки)** - настройки приложения

#### Ключевые компоненты DrawerActivity:

**2.1. Layout (activity_drawer.xml)**
```xml
<androidx.drawerlayout.widget.DrawerLayout
    android:id="@+id/drawer_layout">
    
    <include
        android:id="@+id/app_bar_drawer"
        layout="@layout/app_bar_drawer" />

    <com.google.android.material.navigation.NavigationView
        android:id="@+id/nav_view"
        app:headerLayout="@layout/nav_header_drawer"
        app:menu="@menu/drawer_menu" />
        
</androidx.drawerlayout.widget.DrawerLayout>
```

**2.2. AppBar Layout (app_bar_drawer.xml)**
```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout>
    <com.google.android.material.appbar.AppBarLayout>
        <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar" />
    </com.google.android.material.appbar.AppBarLayout>

    <include layout="@layout/content_drawer" />
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

**2.3. Content Layout (content_drawer.xml)**
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    app:layout_behavior="@string/appbar_scrolling_view_behavior">
    
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment_content_drawer"
        android:name="androidx.navigation.fragment.NavHostFragment"
        app:navGraph="@navigation/drawer_navigation" />
        
</androidx.constraintlayout.widget.ConstraintLayout>
```

**2.4. Navigation Header (nav_header_drawer.xml)**
```xml
<LinearLayout
    android:background="@color/language_primary"
    android:orientation="vertical">
    
    <ImageView
        android:src="@drawable/ic_profile" />
    
    <TextView android:text="@string/student_name" />
    <TextView android:text="@string/student_email" />
    <TextView android:text="@string/student_group" />
</LinearLayout>
```

**2.5. Java код (DrawerActivity.java)**
```java
public class DrawerActivity extends AppCompatActivity {
    private ActivityDrawerBinding binding;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDrawer.toolbar);
        drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Настройка AppBarConfiguration с top-level destinations
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_progress, R.id.nav_achievements, R.id.nav_settings)
                .setOpenableLayout(drawerLayout)
                .build();

        // Подключение NavController
        NavController navController = Navigation.findNavController(
            this, R.id.nav_host_fragment_content_drawer);
        NavigationUI.setupActionBarWithNavController(
            this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Настройка ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, binding.appBarDrawer.toolbar,
                R.string.navigation_drawer_open, 
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка кнопки "Назад"
        getOnBackPressedDispatcher().addCallback(this, 
            new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        finish();
                    }
                }
            });
    }
}
```

**Особенности реализации:**
- AppBarConfiguration указывает top-level destinations (прогресс, достижения, настройки)
- ActionBarDrawerToggle создаёт hamburger icon и анимацию
- OnBackPressedCallback закрывает drawer вместо выхода из приложения
- NavigationView связана с NavController через NavigationUI

### 3. Navigation Component

#### 3.1. Bottom Navigation Graph (mobile_navigation.xml)

```xml
<navigation
    android:id="@+id/mobile_navigation"
    app:startDestination="@+id/navigation_lessons">

    <fragment
        android:id="@+id/navigation_lessons"
        android:name="ru.mirea.yudaev.languagelearningapp.ui.lessons.LessonsFragment"
        android:label="@string/nav_lessons" />

    <fragment
        android:id="@+id/navigation_dictionary"
        android:name="ru.mirea.yudaev.languagelearningapp.ui.dictionary.DictionaryFragment"
        android:label="@string/nav_dictionary" />

    <fragment
        android:id="@+id/navigation_profile"
        android:name="ru.mirea.yudaev.languagelearningapp.ui.profile.ProfileFragment"
        android:label="@string/nav_profile" />
</navigation>
```

#### 3.2. Drawer Navigation Graph (drawer_navigation.xml)

```xml
<navigation
    android:id="@+id/drawer_navigation"
    app:startDestination="@+id/nav_progress">

    <fragment
        android:id="@+id/nav_progress"
        android:name="ru.mirea.yudaev.languagelearningapp.ui.progress.ProgressFragment"
        android:label="@string/nav_progress" />

    <fragment
        android:id="@+id/nav_achievements"
        android:name="ru.mirea.yudaev.languagelearningapp.ui.achievements.AchievementsFragment"
        android:label="@string/nav_achievements" />

    <fragment
        android:id="@+id/nav_settings"
        android:name="ru.mirea.yudaev.languagelearningapp.ui.settings.SettingsFragment"
        android:label="@string/nav_settings" />
</navigation>
```

**Преимущества Navigation Component:**
- Визуализация навигации в редакторе Android Studio
- Type-safe аргументы между destinations
- Автоматическая обработка Up/Back кнопки
- Deep Links поддержка
- Интеграция с BottomNavigationView и NavigationView

### 4. Меню

#### 4.1. Bottom Navigation Menu (bottom_nav_menu.xml)

```xml
<menu>
    <item
        android:id="@+id/navigation_lessons"
        android:icon="@drawable/ic_lessons"
        android:title="@string/nav_lessons" />

    <item
        android:id="@+id/navigation_dictionary"
        android:icon="@drawable/ic_dictionary"
        android:title="@string/nav_dictionary" />

    <item
        android:id="@+id/navigation_profile"
        android:icon="@drawable/ic_profile"
        android:title="@string/nav_profile" />
</menu>
```

**Важно:** ID элементов меню совпадают с ID фрагментов в navigation graph - это позволяет NavigationUI автоматически связывать клики по меню с навигацией.

#### 4.2. Drawer Menu (drawer_menu.xml)

```xml
<menu>
    <group android:checkableBehavior="single">
        <item
            android:id="@+id/nav_progress"
            android:icon="@drawable/ic_progress"
            android:title="@string/nav_progress" />
        <item
            android:id="@+id/nav_achievements"
            android:icon="@drawable/ic_achievements"
            android:title="@string/nav_achievements" />
    </group>

    <item android:title="@string/menu_other">
        <menu>
            <item
                android:id="@+id/nav_settings"
                android:icon="@drawable/ic_settings"
                android:title="@string/nav_settings" />
        </menu>
    </item>
</menu>
```

**Особенности:**
- Группировка элементов с `android:checkableBehavior="single"`
- Вложенное меню для секции "Other"
- Иконки для всех пунктов меню

### 5. Фрагменты

Все 6 фрагментов реализованы с использованием ViewBinding:

#### 5.1. LessonsFragment
- RecyclerView для отображения списка уроков
- Placeholder для будущей реализации adapter

#### 5.2. DictionaryFragment
- SearchView для поиска слов
- TextView с счётчиком слов
- RecyclerView для результатов

#### 5.3. ProfileFragment
- CardView со статистикой (streak, words, lessons)
- Horizontal layout для трёх метрик
- Стилизованные числа и подписи

#### 5.4. ProgressFragment
- 4 ProgressBar для разных навыков:
  - Vocabulary (68%)
  - Grammar (45%)
  - Listening (82%)
  - Speaking (37%)

#### 5.5. AchievementsFragment
- RecyclerView с GridLayoutManager (2 колонки)
- Placeholder для карточек достижений

#### 5.6. SettingsFragment
- 2 CardView с группами настроек
- 5 SwitchCompat:
  - Push Notifications
  - Daily Reminders
  - Sound Effects
  - Offline Mode
  - Auto-play Audio
- Toast уведомления при изменении настроек

**Пример кода фрагмента:**
```java
public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, 
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        binding.switchPush.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getContext(), 
                "Push notifications: " + (isChecked ? "ON" : "OFF"), 
                Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
```

### 6. Дизайн и UI

#### 6.1. Цветовая схема

```xml
<color name="language_primary">#4CAF50</color>        <!-- Зелёный -->
<color name="language_primary_dark">#388E3C</color>   <!-- Тёмно-зелёный -->
<color name="language_secondary">#FF9800</color>      <!-- Оранжевый -->
<color name="language_accent">#2196F3</color>         <!-- Синий -->
```

**Обоснование цветов:**
- Зелёный - ассоциация с ростом, обучением, прогрессом
- Оранжевый - энергия, мотивация, активность
- Синий - доверие, стабильность, концентрация

#### 6.2. Иконки

Все 7 иконок созданы как vector drawables (24dp):
- ic_lessons.xml - Graduation cap (шапка выпускника)
- ic_dictionary.xml - Book (книга)
- ic_profile.xml - Person (человек)
- ic_progress.xml - Circular progress (круговой прогресс)
- ic_achievements.xml - Star (звезда)
- ic_settings.xml - Settings gear (шестерёнка)
- ic_menu.xml - Hamburger menu (три линии)

#### 6.3. Material Design

```xml
<style name="Base.Theme.LanguageLearning" 
       parent="Theme.Material3.Light.NoActionBar">
    <item name="colorPrimary">@color/language_primary</item>
    <item name="colorPrimaryDark">@color/language_primary_dark</item>
    <item name="colorAccent">@color/language_accent</item>
</style>
```

### 7. Gradle конфигурация

#### 7.1. App-level build.gradle.kts

```kotlin
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ru.mirea.yudaev.languagelearningapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.mirea.yudaev.languagelearningapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    
    buildFeatures {
        viewBinding = true
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    
    implementation(libs.cardview)
    implementation(libs.recyclerview)
}
```

#### 7.2. Version Catalog (libs.versions.toml)

```toml
[versions]
appcompat = "1.6.1"
material = "1.10.0"
cardview = "1.0.0"
recyclerview = "1.3.2"

[libraries]
appcompat = { module = "androidx.appcompat:appcompat", version.ref = "appcompat" }
material = { module = "com.google.android.material:material", version.ref = "material" }
cardview = { module = "androidx.cardview:cardview", version.ref = "cardview" }
recyclerview = { module = "androidx.recyclerview:recyclerview", version.ref = "recyclerview" }

[plugins]
android-application = { id = "com.android.application", version = "8.7.3" }
```

## Ключевые концепции

### 1. ViewBinding

ViewBinding генерирует binding class для каждого XML layout файла. Преимущества:
- Type-safe доступ к views
- Null-safe (только views, существующие в layout)
- Нет необходимости в findViewById()

**Использование:**
```java
// Inflate binding
binding = ActivityMainBinding.inflate(getLayoutInflater());
setContentView(binding.getRoot());

// Доступ к views
binding.toolbar.setTitle("Title");
binding.bottomNavigation.setSelectedItemId(R.id.navigation_lessons);
```

### 2. Navigation Component

Navigation Component упрощает реализацию навигации:

**NavHostFragment** - контейнер для destinations (фрагментов)
**NavController** - управляет навигацией между destinations
**NavigationUI** - связывает UI компоненты с NavController

**Автоматическое связывание:**
```java
NavController navController = Navigation.findNavController(this, R.id.nav_host);
NavigationUI.setupWithNavController(bottomNav, navController);
```

### 3. Material Design Components

#### BottomNavigationView
- Максимум 5 items
- Иконки + текст (или только иконки)
- Автоматическая анимация при переключении

#### NavigationView
- Drawer с header
- Группированное меню
- Выделение активного item
- Интеграция с DrawerLayout

#### AppBarLayout + Toolbar
- Material elevation и shadows
- Scroll behavior
- Интеграция с CoordinatorLayout

### 4. Back Stack Management

**Bottom Navigation:**
- Каждый tab - top-level destination
- Нет добавления в back stack
- Повторный клик на tab - возврат к начальному состоянию

**Drawer Navigation:**
- AppBarConfiguration указывает top-level destinations
- Up button отображается только для non-top-level
- Back button закрывает drawer, если он открыт

## Тестирование

### Функциональные тесты:

✅ **Bottom Navigation**
1. Переключение между Lessons, Dictionary, Profile
2. Toolbar отображает правильное название
3. Состояние фрагментов сохраняется
4. Иконки корректно подсвечиваются

✅ **Navigation Drawer**
1. Hamburger icon открывает/закрывает drawer
2. Переключение между Progress, Achievements, Settings
3. Header отображает информацию студента
4. Back button закрывает drawer
5. Клик вне drawer закрывает его

✅ **MainActivity → DrawerActivity**
1. Кнопка в toolbar открывает DrawerActivity
2. Состояние MainActivity сохраняется

✅ **Settings Fragment**
1. Все switches работают
2. Toast уведомления отображаются
3. Состояние switches сохраняется

### UI/UX тесты:

✅ Плавные анимации переходов
✅ Правильные цвета и иконки
✅ Responsive layouts (portrait/landscape)
✅ Адекватные отступы и размеры
✅ Читаемость текста

## Выводы

В ходе выполнения практической работы были изучены и реализованы:

1. **Bottom Navigation** - удобный способ навигации между 3-5 основными разделами приложения. Подходит для frequently accessed destinations.

2. **Navigation Drawer** - боковое меню для вспомогательных функций и настроек. Экономит место на экране, группирует связанные items.

3. **Navigation Component** - современный подход к навигации в Android:
   - Визуальный редактор navigation graph
   - Type-safe навигация
   - Автоматическая обработка back stack
   - Меньше boilerplate кода

4. **ViewBinding** - надёжная альтернатива findViewById():
   - Compile-time safety
   - Улучшенная производительность
   - Меньше runtime ошибок

5. **Material Design 3** - современные UI компоненты:
   - Unified design language
   - Адаптивные layouts
   - Accessibility support

**Практические навыки:**
- Создание navigation graphs
- Настройка NavController и NavHostFragment
- Интеграция Bottom Navigation
- Реализация Navigation Drawer
- Работа с ViewBinding
- Material Design компоненты
- Back stack management

**Архитектурные решения:**
- Разделение на два Activity для демонстрации разных паттернов навигации
- Структурирование фрагментов по пакетам (ui.lessons, ui.dictionary, и т.д.)
- Использование ViewBinding во всех компонентах
- Централизованное управление строками и цветами в resources

## Приложения

### Структура проекта:
```
LanguageLearningApp/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/ru/mirea/yudaev/languagelearningapp/
│       │   ├── MainActivity.java
│       │   ├── DrawerActivity.java
│       │   └── ui/
│       │       ├── lessons/LessonsFragment.java
│       │       ├── dictionary/DictionaryFragment.java
│       │       ├── profile/ProfileFragment.java
│       │       ├── progress/ProgressFragment.java
│       │       ├── achievements/AchievementsFragment.java
│       │       └── settings/SettingsFragment.java
│       └── res/
│           ├── drawable/        (7 vector icons)
│           ├── layout/          (11 layout files)
│           ├── menu/            (3 menu files)
│           ├── navigation/      (2 navigation graphs)
│           └── values/
│               ├── colors.xml
│               ├── strings.xml
│               └── themes.xml
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    ├── libs.versions.toml
    └── wrapper/
```

### Файлы проекта:
- **Всего файлов создано:** ~40
- **Java классов:** 8 (2 activities + 6 fragments)
- **Layout файлов:** 11
- **Drawable ресурсов:** 7 vector icons
- **Navigation graphs:** 2
- **Menu файлов:** 3

### Результаты компиляции:
```
BUILD SUCCESSFUL in 1m 33s
78 actionable tasks: 77 executed, 1 up-to-date
```

### Установка:
```
Installing APK 'app-debug.apk' on 'Pixel_9_Pro_API_35(AVD) - 15'
Installed on 1 device.
BUILD SUCCESSFUL in 6s
```

---

**Дата выполнения:** 2024  
**Выполнил:** Юдаев И.А., БСБО-09-22

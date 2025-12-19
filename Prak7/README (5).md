# Отчет по практической работе: Навигация в Android

## 1. Реализация навигации через нижнюю панель (`BottomNavigationApp`)

**Задание:** Продумать концепцию приложения, реализовать навигацию с помощью **Bottom Navigation**,
добавить тематические иконки и обновить цветовую гамму.

**Реализация:**

1. **Настройка модуля:** В `build.gradle` были подключены зависимости **Navigation Component** и
   активирован **ViewBinding**.
2. **Ресурсы:**
    * В `colors.xml` определены кастомные цвета (`fitness_primary`, `fitness_secondary`).
    * Созданы векторные иконки (Vector Assets) для каждого раздела: `ic_fitness`, `ic_food`,
      `ic_person`.
    * Создан файл меню `bottom_menu.xml`, где каждому пункту присвоены соответствующие иконки и ID.
3. **Фрагменты:** Реализованы три фрагмента (`WorkoutFragment`, `DietFragment`, `ProfileFragment`) с
   использованием ViewBinding. Разметка фрагментов адаптирована под концепцию фитнес-приложения.
4. **Navigation Graph:** Создан граф навигации `mobile_navigation.xml`, объединяющий фрагменты. ID
   фрагментов в графе синхронизированы с ID пунктов меню для автоматической навигации.
5. **Layout Activity:** В `activity_main.xml` размещены:
    * `Toolbar` (верхняя панель).
    * `FragmentContainerView` (хост навигации).
    * `BottomNavigationView` (нижняя панель меню).
6. **Логика навигации:** В `MainActivity` инициализирован `NavController`. С помощью класса
   `NavigationUI` настроена автоматическая связь между контроллером навигации, нижней панелью и
   заголовком `Toolbar`.

![Скриншот BottomNavigationApp](./screenshots/bottom_nav_app1.png)
![Скриншот BottomNavigationApp](./screenshots/bottom_nav_app2.png)
![Скриншот BottomNavigationApp](./screenshots/bottom_nav_app3.png)

## 2. Реализация навигации через боковую шторку (`NavigationDrawerApp`)

**Задание:** Продумать концепцию приложения, реализовать навигацию с помощью **Navigation Drawer**,
обновить цветовую гамму и реализовать закрытие шторки на кнопку «Back».

**Реализация:**

1. **Ресурсы:**
    * Обновлена палитра цветов в `colors.xml`.
    * Создано меню `drawer_menu.xml` с группировкой элементов.
    * Подготовлены иконки: `ic_schedule`, `ic_grade`, `ic_settings`.
2. **Структура Layout:** Реализована иерархическая структура разметки согласно гайдлайнам Material
   Design:
    * `nav_header_main.xml`: Шапка шторки с аватаром студента и e-mail.
    * `content_main.xml`: Контейнер для `NavHostFragment`.
    * `app_bar_main.xml`: Содержит `Toolbar` и включает контент.
    * `activity_main.xml`: Корневой `DrawerLayout`, объединяющий основной контент и
      `NavigationView` (саму шторку).
3. **Фрагменты:** Созданы тематические фрагменты (`ScheduleFragment`, `GradesFragment`,
   `SettingsFragment`) с заполнением контентом (CardView для пар, списки оценок).
4. **Логика Activity:**
    * Настроен `AppBarConfiguration` с передачей ID всех корневых фрагментов и объекта
      `drawerLayout`.
    * Реализована поддержка гамбургер-меню через `setupActionBarWithNavController`.
5. **Обработка кнопки «Back»:**
    * В метод `onCreate` добавлен `OnBackPressedCallback`.
    * Логика: если шторка (`Drawer`) открыта, нажатие «Назад» закрывает её методом `closeDrawer()`.
      Если закрыта — выполняется стандартное действие (выход из приложения или навигация по стеку).

![Скриншот NavigationDrawerApp](./screenshots/nav_drawer_app1.png)
![Скриншот NavigationDrawerApp](./screenshots/nav_drawer_app2.png)
![Скриншот NavigationDrawerApp](./screenshots/nav_drawer_app3.png)
![Скриншот NavigationDrawerApp](./screenshots/nav_drawer_app4.png)

Вот дополнение к отчёту, описывающее реализацию навигации, показанную на скриншоте. Вставьте этот текст в конец вашего текущего отчета (после раздела 3).

---

## 3. Итоговая реализация навигации (Navigation Component)

**Задание:** Реализовать в приложении навигацию с использованием **Navigation Component** и реализовать нижнюю навигационную панель (**Bottom Navigation**).

**Реализация:**

1. **Интеграция библиотеки:** В проект были добавлены зависимости `androidx.navigation:navigation-fragment` и `navigation-ui`.
2. **Навигационный граф:** Создан файл `nav_graph.xml`, описывающий карту переходов приложения. В нем определены два назначения (destinations): `HistoryFragment` (стартовый экран) и `ProfileFragment`.
3. **Меню навигации:** Создан ресурс `bottom_nav_menu.xml` с двумя пунктами («History» и «Profile»), идентификаторы которых совпадают с ID фрагментов в навигационном графе.
4. **Обновление MainActivity:**
    * Контейнер `FrameLayout` заменен на `FragmentContainerView` (реализация `NavHostFragment`), который отвечает за смену экранов.
    * Добавлен компонент `BottomNavigationView` для отображения нижней панели.
    * В коде `MainActivity` удалена логика ручных транзакций (`beginTransaction`). Вместо этого настроена автоматическая связка контроллера и UI через метод `NavigationUI.setupWithNavController()`.

**Результат:**
Как видно на финальном скриншоте, приложение успешно использует нижнюю навигационную панель.

* Активна вкладка **History** (выделена визуально индикатором).
* Переход между фрагментами осуществляется автоматически при выборе пунктов меню.

![Финальный результат с Bottom Navigation](./screenshots/final_nav_screenshot1.png)
![Финальный результат с Bottom Navigation](./screenshots/final_nav_screenshot2.png)

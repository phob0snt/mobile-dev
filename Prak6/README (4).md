# Практическая работа №14

**Тема:** Фрагменты. Жизненный цикл, навигация и способы обмена данными.

## Цель работы

Целью данной практической работы является получение комплексных знаний и практических навыков по использованию компонента `Fragment` в Android-приложениях. В ходе работы были изучены:

- Жизненный цикл фрагментов и его связь с жизненным циклом `Activity`.
- Способы добавления фрагментов и управление ими с помощью `FragmentManage`r.
- Построение навигации между экранами, реализованными в виде фрагментов.
- Современные подходы к организации обмена данными между фрагментами: `Bundle`, `ViewModel` с `LiveData` и `Fragment Result API`.
  
---

## Создание базового фрагмента и передача данных через `Bundle`

Создать приложение, состоящее из одной `Activity` и одного `Fragment`. `Activity` должна была при создании фрагмента передать в него данные (номер студента по списку) с помощью объекта `Bundle`. Фрагмент должен был получить эти данные и отобразить их на экране.

В `MainActivity` был создан `Bundle`, в который помещалось целочисленное значение. Этот Bundle передавался в качестве аргумента при добавлении фрагмента в `FragmentContainerView`.

```java
// ...
if (savedInstanceState == null) {
    Bundle bundle = new Bundle();
    // Пример номера студента
    bundle.putInt("my_number_student", 15);

    getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container_view, BlankFragment.class, bundle)
            .commit();
}
// ...
```

`BlankFragment.java` (фрагмент кода):

```java
// ...
@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Bundle args = getArguments();
    if (args != null) {
        int number = args.getInt("my_number_student", 0);
        TextView textView = view.findViewById(R.id.fragment_text);
        textView.setText("Номер студента: " + number);
    }
}
// ...
```

<img width="974" height="619" alt="image" src="https://github.com/user-attachments/assets/9a68c2ac-664f-47ec-b684-b8b1370def9a" />

---

## Навигация между списком и статичными деталями

В `ListFragment` был создан `ListView`, заполненный массивом названий фильмов. В обработчике нажатий `setOnItemClickListener` была реализована транзакция `replace`, заменяющая один фрагмент на другой. Вызов `addToBackStack(null)` обеспечил корректную работу системной кнопки "назад".

```java
// ...
listView.setOnItemClickListener((parent, itemView, position, id) -> {
    getParentFragmentManager().beginTransaction()
            .replace(R.id.fragment_container_view, DetailsFragment.class, null)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit();
});
// ...
```

Приложение запускалось с экрана со списком фильмов. При нажатии на любой элемент списка открывался второй экран со статичной информацией.

<img width="974" height="579" alt="image" src="https://github.com/user-attachments/assets/db766ae5-ef50-4753-ae9a-cef601409916" />

<img width="974" height="581" alt="image" src="https://github.com/user-attachments/assets/c7a09374-b460-4a4e-99ba-9c79275f06a7" />

---

## Обмен данными между фрагментами через `ViewModel`

Нужно было модифицировать предыдущее задание. Теперь `DetailsFragment` должен был отображать актуальную информацию о том фильме, который был выбран в `ListFragment`. Для обмена данными необходимо было использовать общую `MovieViewModel`.

1. Был создан класс модели `Movie` для хранения данных о фильме.
2. Также создан `MovieViewModel`, содержащий `MutableLiveData<Movie>`, для хранения состояния (выбранного фильма).
3. `ListFragment` был обновлен: при нажатии на элемент списка он получал соответствующий объект `Movie` и передавал его в `ViewModel` через метод `viewModel.selectMovie(selectedMovie)`.
4. `DetailsFragment` был обновлен: в методе `onViewCreated` он подписывался на `LiveData` из `ViewModel`. При любом изменении данных в `LiveData` (т.е. при выборе фильма) `TextView` на экране автоматически обновлялись.

`MovieViewModel.java` (фрагмент кода):

```java
public class MovieViewModel extends ViewModel {
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();

    public void selectMovie(Movie movie) {
        selectedMovie.setValue(movie);
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }
}
```

`DetailsFragment.java` (фрагмент кода):

```java
// ...
viewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
// ...
viewModel.getSelectedMovie().observe(getViewLifecycleOwner(), movie -> {
    if (movie != null) {
        titleTextView.setText(movie.getTitle());
        directorTextView.setText("Режиссер: " + movie.getDirector());
        yearTextView.setText("Год выпуска: " + movie.getYear());
    }
});
// ...
```

Теперь приложение корректно отображало информацию о любом выбранном фильме, демонстрируя реактивный подход к обновлению UI.

<img width="974" height="581" alt="image" src="https://github.com/user-attachments/assets/f3d70074-0ed9-4476-87aa-e046bffd4e57" />

<img width="2749" height="1555" alt="image" src="https://github.com/user-attachments/assets/3778e404-a477-4208-9752-c20b87912eff" />

---

## Передача одноразовых данных через Fragment Result API

Нужно реализовать передачу данных от основного фрагмента (`DataFragment`) к диалоговому (`BottomSheetFragment`) с помощью `Fragment Result API`. `DataFragment` должен содержать поле для ввода текста и кнопку. При нажатии на кнопку должен открываться `BottomSheetFragment` и отображать введенный текст.

1. `DataFragment` (Отправитель): В обработчике нажатия кнопки текст из `EditText` упаковывался в `Bundle`, который затем отправлялся через `getParentFragmentManager().setFragmentResult("requestKey", result)`.
2. `BottomSheetFragment` (Получатель): В методе `onCreate` был установлен слушатель `getParentFragmentManager().setFragmentResultListener("requestKey", ...)`. Как только результат с нужным ключом устанавливался, слушатель срабатывал, извлекал данные из `Bundle` и обновлял `TextView` на своем экране.

`DataFragment.java` (фрагмент кода)

```java
// ...
button.setOnClickListener(v -> {
    String text = editText.getText().toString();
    Bundle result = new Bundle();
    result.putString("key", text);
    getParentFragmentManager().setFragmentResult("requestKey", result);

    BottomSheetFragment bottomSheet = new BottomSheetFragment();
    bottomSheet.show(getParentFragmentManager(), "ModalBottomSheet");
});
// ...
```

`BottomSheetFragment.java` (фрагмент кода)

```java
// ...
@Override
public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
        String text = bundle.getString("key");
        if (receivedDataTextView != null) {
            receivedDataTextView.setText("Получено: " + text);
        }
    });
}
// ...
```

<img width="974" height="560" alt="image" src="https://github.com/user-attachments/assets/d7530cd8-f64a-4306-94c2-9265c3ee918b" />


---

## Контрольное задание

### Реализация экрана "Профиль" (Архитектура MVVM)

Экран был построен в соответствии с принятой в проекте архитектурой MVVM.
- `ViewModel` (`ProfileViewModel.java`):
  - Отвечает за всю логику экрана: получение данных о текущем пользователе и обработку выхода.
  - Содержит `LiveData<User>`, через которую передает данные пользователя во `Fragment`.
  - Содержит `LiveData<Boolean>` для оповещения Fragment о том, что был выполнен выход из системы. Это позволяет отделить логику выхода от навигации.
  - Использует `CurrentUser` и `LogoutUser` use-cases для взаимодействия с `AuthRepository`.
- `View` (`ProfileFragment.java` и `fragment_profile.xml`):
  - `ProfileFragment` подписывается на `LiveData` из `ProfileViewModel`. При получении данных о пользователе он обновляет `TextView` для имени и email.
  - При срабатывании события выхода `logoutEvent` фрагмент выполняет переход на `LoginActivity`, используя флаги `Intent.FLAG_ACTIVITY_CLEAR_TASK` и `Intent.FLAG_ACTIVITY_NEW_TASK` для полной очистки стека экранов.
  - Изначальная верстка на `LinearLayout` была заменена на `ConstraintLayout` для более надежного позиционирования элементов и решения проблемы с "пропавшей" кнопкой. Заголовок был вынесен в отдельный `TextView` внутри `Toolbar` для кастомизации (жирный шрифт, центрирование).
 
- Меню навигации (`menu_bottom.xml`): В ресурсный файл меню был добавлен новый пункт `<item>` для "Профиля" с соответствующей иконкой и id.
- Главная активность (`MainActivity.java`): Обработчик setOnItemSelectedListener для `BottomNavigationView` был расширен. В него добавлено условие `else if (id == R.id.nav_profile)`, которое создает экземпляр `ProfileFragment` и отображает его в контейнере `R.id.container`.
- Бэк-стек: Навигация между основными разделами в `MainActivity` выполняется через метод replace() без вызова `addToBackStack()`. Это является стандартной практикой для `BottomNavigationView` и предотвращает наслоение главных экранов друг на друга при нажатии системной кнопки "Назад".

<img width="974" height="580" alt="image" src="https://github.com/user-attachments/assets/814ec741-75de-4f5b-8879-1cded5646466" />

--- 

## Итоги

В результате выполнения данной практической работы были получине комплексные знания о работе с фрагментами и освоены три ключевых механизма обмена данными, каждый из которых подходит для своих задач:
- `Bundle`: Идеален для передачи первоначальных данных при создании фрагмента.
- `ViewModel` и `LiveData`: Мощный инструмент для организации общего состояния между несколькими фрагментами или между фрагментами и `Activity`. Обеспечивает сохранение состояния при смене конфигурации и позволяет строить реактивные UI.
- `Fragment Result API`: Лучший выбор для передачи одноразовых, событийных данных (например, результат выбора в диалоге), так как он не создает сильной связи между компонентами.
Полученные навыки позволяют проектировать модульные, гибкие и легко поддерживаемые Android-приложения, соответствующие современным стандартам разработки.

---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22

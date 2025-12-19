# Практическая работа №12

**Тема:** Основные способы отображения списков в Android: `ScrollView`, `ListView`, `RecyclerView`

## Цель работы

Изучение и практическое применение основных компонентов Android SDK, предназначенных для отображения списков данных: `ScrollView`, `ListView` и `RecyclerView`. Освоение современного подхода к построению пользовательского интерфейса с использованием архитектурного паттерна **MVVM** (на примере связки `ViewModel` и `LiveData`) для создания эффективных и масштабируемых приложений.
  
---

## Реализация `ScrollViewApp`

1. В файле разметки activity_main.xml был создан корневой элемент `ScrollView`, содержащий `LinearLayout` с вертикальной ориентацией (`android:id="@+id/linear_layout_container"`).
2. В `MainActivity.java` в цикле от 0 до 99 программно создавались `TextView` для каждого элемента списка.
3. С помощью `LayoutInflater` для каждого `TextView` создавалось представление из отдельного XML-файла макета (`item_simple_text.xml`).
4. Для вычисления значений прогрессии использовался класс `BigInteger`, так как стандартный long не вмещает такие большие числа (например, 2^99).
5. Каждый созданный `TextView` добавлялся в `LinearLayout` с помощью метода `addView()`.

<img width="974" height="568" alt="image" src="https://github.com/user-attachments/assets/20372fe9-dacd-481d-a85d-381e5bc02255" />

## Реализация `ListViewApp`

1. В `activity_main.xml` был размещен компонент `ListView`.
2. Создан класс-модель `Book.java` с полями title и author.
3. Разработан кастомный макет для одного элемента списка `list_item_book.xml`, содержащий два `TextView` для названия и автора.
4. Создан кастомный адаптер `BookAdapter`, унаследованный от `ArrayAdapter<Book>`. В методе `getView()` этого адаптера происходит привязка данных из объекта `Book`к `TextView` в макете элемента.
5. В `MainActivity.java` был создан `ArrayList<Book>`, заполненный 30+ объектами. Экземпляр `BookAdapter` был создан и установлен для `ListView`.

<img width="974" height="599" alt="image" src="https://github.com/user-attachments/assets/b1752653-cf86-4c93-a4f9-1aa227acc3f0" />

## Реализация `RecyclerView` с `ViewMode` и `LiveData`

1. Зависимости: В файл `build.gradle` были добавлены зависимости для `recyclerview`, `cardview`, `lifecycle-viewmodel` и `lifecycle-livedata`.
2. Модель: Создан класс `HistoricalEvent.java` с полями для заголовка, описания и ID ресурса изображения.
3. `View` (Разметка):
  - `activity_main.xml` содержит только `RecyclerView`.
  - `list_item_event.xml` описывает внешний вид одного элемента с помощью `CardView`, `ImageView` и двух `TextView`.
4. `ViewModel`: Создан класс EventViewModel, унаследованный от `ViewModel`. В нем содержится `MutableLiveData<List<HistoricalEvent>>`, которая хранит список событий. Метод `loadHistoricalEvents()` имитирует загрузку данных и помещает их в `LiveData`.
5. `Adapter`: Создан `EventAdapter`, унаследованный от `RecyclerView.Adapter`. В нем реализован обязательный вложенный класс `EventViewHolder`, который хранит ссылки на `View` элемента. В методе `onBindViewHolder` происходит привязка данных к элементам `ViewHolder`.
6. `Activity`: `MainActivity.java` теперь выполняет роль контроллера:
  - Инициализирует `RecyclerView` и `EventAdapter`.
  - Получает экземпляр `EventViewModel` через `ViewModelProvider`.
  - Подписывается на изменения `LiveData` с помощью метода `observe()`. В лямбда-выражении, которое срабатывает при обновлении данных, вызывается метод адаптера для установки нового списка (`adapter.setEvents(eventList)`).
  - Вызывает метод `viewModel.loadHistoricalEvents()` для запуска загрузки данных.

<img width="974" height="572" alt="image" src="https://github.com/user-attachments/assets/2c738c96-f373-4463-b009-d46ba14fb3ae" />

---

## Контрольное задание

### Создание заглушки с данными в Репозитории

В модуле `:data` реализован класс-заглушка `FakeNetworkApi`, который имитирует получение данных из внешнего API. Этот класс содержит статический набор данных о продуктах.

```java
public class FakeNetworkApi implements NetworkApi {

    private final List<Product> stub = new ArrayList<>();

    public FakeNetworkApi() {
        stub.add(new Product(
                "1", "Hydrating Cleanser", 499, "clean", "prod_cleanser",
                "Нежный очищающий гель для ежедневного ухода"
        ));
        stub.add(new Product(
                "2", "Vitamin C Serum", 1299, "serum", "prod_serum",
                "Сыворотка с витамином C для сияния и выравнивания тона кожи"
        ));
        stub.add(new Product(
                "3", "SPF 50", 899, "spf", "prod_spf",
                "Лёгкий солнцезащитный крем SPF 50 на каждый день"
        ));
    }

    @Override
    public List<Product> fetchProducts() {
        return new ArrayList<>(stub); // Возвращается "заглушечный" список
    }
    // ...
}
```

Этот класс используется в `ProductRepositoryImpl` в качестве источника данных.

<img width="2718" height="1392" alt="image" src="https://github.com/user-attachments/assets/8bfc9136-8de2-4dda-9915-86a40ae1ab83" />

### Передача данных в слой представления с помощью `LiveData`

Для передачи данных из слоя данных в UI (фрагменты) используется связка `ViewModel` и `LiveData`.
Во `ViewModel` (например, `CatalogViewModel`) создается `MutableLiveData`, которая обновляется после получения данных из репозитория. Фрагмент подписывается на изменения этой `LiveData` и автоматически обновляет UI.

```java
public class CatalogViewModel extends ViewModel {
    // ...
    private final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    public final LiveData<List<Product>> products = _products;

    public void loadProducts(String categoryId) {
        new Thread(() -> {
            try {
                // ...
                List<Product> productList = new GetProductList(productRepository).execute();
                _products.postValue(productList); // Данные передаются в LiveData
            } catch (Exception e) {
                // ...
            }
        }).start();
    }
}
```

Подписка на `LiveData` во фрагменте `CatalogFragment`:

```java
viewModel.products.observe(getViewLifecycleOwner(), products -> {
    // Этот код выполняется при каждом обновлении данных
    adapter.submit(products);
});
```

### Отображение данных в `RecyclerView`

Данные, полученные через `LiveData`, отображаются в `RecyclerView` с помощью кастомного адаптера `ProductAdapter`.

В `CatalogFragment` производится настройка `RecyclerView` и его адаптера. Метод `adapter.submit()` вызывается для обновления списка элементов, отображаемых на экране.

```java
public class CatalogFragment extends Fragment {
    // ...
    private ProductAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        // ...
        RecyclerView rv = v.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Создание и установка адаптера
        adapter = new ProductAdapter(p -> { /* ... обработка клика ... */ });
        rv.setAdapter(adapter);

        // Обновление адаптера при получении данных из ViewModel
        viewModel.products.observe(getViewLifecycleOwner(), products -> {
            adapter.submit(products);
        });

        viewModel.loadProducts(categoryId);
    }
}
```

<img width="2723" height="1704" alt="image" src="https://github.com/user-attachments/assets/85bd8eec-32f4-489f-9dfd-aca1a9a1ae08" />


---

## Итоги

В ходе выполнения практической работы были изучены и применены на практике три основных способа отображения списков в Android.
1. `ScrollView` является простым инструментом, подходящим только для небольших объемов статического контента. Его использование для динамических списков приводит к серьезным проблемам с производительностью.
2. `ListView` представляет собой более совершенный механизм, однако для эффективной работы требует ручной оптимизации (паттерн ViewHolder) и является устаревшим.
3. `RecyclerView` — это мощный, гибкий и производительный компонент, который является современным стандартом для создания любых списков. Он обеспечивает высокую производительность "из коробки" за счет принудительного использования `ViewHolder`.
4. Использование архитектурного паттерна MVVM со связкой `ViewModel` и `LiveData` позволяет создавать надежные и легко поддерживаемые приложения. Такой подход эффективно решает проблемы жизненного цикла Android-компонентов и обеспечивает чистоту кода за счет разделения ответственностей.

---

**Выполнила**: Голышева Е.А.  
**Группа**: БСБО-09-22

# 🚀 Quick Start Guide - Askon Clean Architecture

## Структура модулей

```
:domain (Pure Java)  ← Бизнес-логика
   ↑
:data (Android Lib)  ← Источники данных  
   ↑
:app (Application)   ← UI и презентация
```

## Где что добавлять?

### 📋 Новая модель/Entity
**Модуль:** `:domain`  
**Путь:** `domain/src/main/java/com/x2ketarol/askon/domain/model/`  
**Пример:** `Product.java`, `Order.java`  
```java
package com.x2ketarol.askon.domain.model;

public class Product {
    private String id;
    private String name;
    private double price;
    // getters, setters, constructor
}
```

### 📜 Новый Repository Interface
**Модуль:** `:domain`  
**Путь:** `domain/src/main/java/com/x2ketarol/askon/domain/repository/`  
**Пример:** `ProductRepository.java`  
```java
package com.x2ketarol.askon.domain.repository;

import com.x2ketarol.askon.domain.model.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    Product getProductById(String id);
    void saveProduct(Product product);
}
```

### ⚙️ Новый Use Case
**Модуль:** `:domain`  
**Путь:** `domain/src/main/java/com/x2ketarol/askon/domain/usecases/`  
**Пример:** `GetProductListUseCase.java`  
```java
package com.x2ketarol.askon.domain.usecases;

import com.x2ketarol.askon.domain.model.Product;
import com.x2ketarol.askon.domain.repository.ProductRepository;
import java.util.List;

public class GetProductListUseCase {
    private final ProductRepository repository;

    public GetProductListUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> execute(String category) {
        return repository.getAllProducts()
            .stream()
            .filter(p -> p.getCategory().equals(category))
            .collect(Collectors.toList());
    }
}
```

### 🗄️ Реализация Repository
**Модуль:** `:data`  
**Путь:** `data/src/main/java/com/x2ketarol/askon/data/repository/`  
**Пример:** `ProductRepositoryImpl.java`  
```java
package com.x2ketarol.askon.data.repository;

import com.x2ketarol.askon.domain.model.Product;
import com.x2ketarol.askon.domain.repository.ProductRepository;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    // Здесь работа с Room, Retrofit, SharedPrefs
    
    @Override
    public List<Product> getAllProducts() {
        // Загрузка из БД или API
        return new ArrayList<>();
    }
}
```

### 📱 Activity/Fragment/ViewModel
**Модуль:** `:app`  
**Путь:** `app/src/main/java/com/x2ketarol/askon/presentation/`  
**Пример:** `ProductListActivity.java`  
```java
package com.x2ketarol.askon.presentation;

import com.x2ketarol.askon.domain.usecases.GetProductListUseCase;
import com.x2ketarol.askon.data.repository.ProductRepositoryImpl;

public class ProductListActivity extends AppCompatActivity {
    private GetProductListUseCase getProductListUseCase;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Инициализация use case (позже через DI)
        getProductListUseCase = new GetProductListUseCase(
            new ProductRepositoryImpl()
        );
    }
}
```

## 📦 Добавление зависимостей

### Room Database (в :data)
```kotlin
// data/build.gradle.kts
dependencies {
    implementation(project(":domain"))
    
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
}
```

### Retrofit (в :data)
```kotlin
// data/build.gradle.kts
dependencies {
    implementation(project(":domain"))
    
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
```

### ViewModel и Navigation (в :app)
```kotlin
// app/build.gradle.kts
dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
    
    // Navigation
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
}
```

## 🔄 Типичный flow данных

```
User Action (UI)
      ↓
Activity/Fragment
      ↓
ViewModel
      ↓
Use Case (domain)
      ↓
Repository Interface (domain)
      ↓
Repository Implementation (data)
      ↓
Data Source (Room/Retrofit/SharedPrefs)
      ↓
← возврат данных обратно вверх
```

## ✅ Правила

1. **:domain** НЕ импортирует ничего из Android
2. **:data** НЕ импортирует ничего из **:app**
3. **Use Cases** содержат только бизнес-логику
4. **Repository** определяет ЧТО нужно, а не КАК
5. **Implementation** в :data решает КАК получать данные

## 🎯 Быстрые команды

```bash
# Синхронизация после изменений в gradle
./gradlew --refresh-dependencies

# Проверка компиляции domain
./gradlew :domain:build

# Запуск приложения
./gradlew :app:installDebug

# Запуск тестов domain (без эмулятора!)
./gradlew :domain:test
```

## 📚 Дополнительные ресурсы

- `ARCHITECTURE.md` - полная документация архитектуры
- `ARCHITECTURE_DIAGRAM.txt` - визуальная диаграмма
- `MIGRATION_COMPLETE.md` - детали миграции

---

**Важно:** При добавлении новых классов следуйте структуре пакетов:
- Модели → `:domain/model`
- Интерфейсы → `:domain/repository`
- Use Cases → `:domain/usecases`
- Реализации → `:data/repository`
- UI → `:app/presentation`

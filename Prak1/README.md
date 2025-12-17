# Отчет по практической работе №9
## Реализация Clean Architecture в Android приложении

**Студент:** Юдаев Игорь Александрович  
**Группа:** БСБО-09-22 

---

## Цель работы

Освоить принципы проектирование приложения с использованием Clean Architecture и реализовать многослойную архитектуру Android-приложения с разделением на слои: Presentation, Domain и Data.

---

## Use-case диаграмма

"Askon" - мобильное приложение, представляющее собой фриланс-площадку, где эксперты продают свое время в формате коротких аудио/видео конференций. Гость может просматривать доступных экспертов, а авторизованный пользователь (клиент/эксперт) имеет доступ ко всем возможностям площадки (бронирование времени эксперта, общение в чате, настройка профиля и др.)

Use-case диаграмма приложения:

<img width="806" height="773" alt="image" src="https://github.com/user-attachments/assets/1ff32744-7830-4252-a122-4daadc8df15d" />


## Диаграмма слоёв приложения:
<img width="1052" height="869" alt="image_2025-10-03_22-10-03" src="https://github.com/user-attachments/assets/c28c60b3-c20c-4fe3-a1ba-36da30b5cb04" />


---

## MovieProject

Для выполнения задания было создано приложение MovieProject. Архитектура приложения была разделена согласно Clean Architecture. Главный модуль был разделён на domain, data и representation. На уровне domain были созданы 2 use-case для логики сохранения и отображения любимого фильма. На уровне data была реализована модель фильма Movie. На уровне presentation был реализован главный экран MainActivity, все интерактивные элементы были подключены к своим модулям.
<img width="388" height="777" alt="image" src="https://github.com/user-attachments/assets/900c960e-a729-4429-b77f-e0d52fff4582" />
<img width="402" height="760" alt="image" src="https://github.com/user-attachments/assets/8dd56bb9-f6f4-440b-ba72-bb8017a55056" />
<img width="435" height="865" alt="image" src="https://github.com/user-attachments/assets/7ef19e2f-3b87-42b2-ad90-1efaa2d06581" />



---

## Реализация основы собственного приложения

Было создано новое приложение Askon, которое также было разделено на слои согласно Clean Architecture. На уровне domain были созданы все основные модели (Booking, Expert, User, ExpertProfile, Message, Review), также были созданы интерфейсы для взаимодействия с данными (Booking, Chat, User, ML) и use-cases для выполнения основных действий (BookExpertTime, GetExpertsList, LoginUser, RecognizePhoto, SendMessage). На уровне data были созданы фейковые реализации репозиториев, чтобы можно было протестировать работу приложения на моках. На уровне presentation был создан экран MainActivity с кнопками для тестирования функционала и выводом результатов.

<img width="438" height="665" alt="image" src="https://github.com/user-attachments/assets/6fbe4ed1-ede0-4473-89b2-2b902a9b2faf" />
<img width="365" height="728" alt="image" src="https://github.com/user-attachments/assets/3611fbf9-c45c-434a-98eb-d98f39957133" />
<img width="377" height="733" alt="image" src="https://github.com/user-attachments/assets/c0ded9f4-d77a-4234-b7fa-54d4217ffd74" />
<img width="384" height="737" alt="image" src="https://github.com/user-attachments/assets/e2003a42-4a7a-4259-9b11-5039d2421de3" />
<img width="397" height="735" alt="image" src="https://github.com/user-attachments/assets/ad76128b-1c84-4698-9797-0a7d96627016" />
<img width="379" height="726" alt="image" src="https://github.com/user-attachments/assets/619900ec-9572-46c0-8d2b-387e0dd39da3" />






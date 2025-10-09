package com.x2ketarol.askon.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.x2ketarol.askon.R;
import com.x2ketarol.askon.data.repository.BookingRepositoryImpl;
import com.x2ketarol.askon.data.repository.ChatRepositoryImpl;
import com.x2ketarol.askon.data.repository.MLRepositoryImpl;
import com.x2ketarol.askon.data.repository.UsersRepositoryImpl;
import com.x2ketarol.askon.domain.model.Booking;
import com.x2ketarol.askon.domain.model.Expert;
import com.x2ketarol.askon.domain.model.Message;
import com.x2ketarol.askon.domain.model.User;
import com.x2ketarol.askon.domain.repository.BookingRepository;
import com.x2ketarol.askon.domain.repository.ChatRepository;
import com.x2ketarol.askon.domain.repository.MLRepository;
import com.x2ketarol.askon.domain.repository.UsersRepository;
import com.x2ketarol.askon.domain.usecases.BookExpertTimeUseCase;
import com.x2ketarol.askon.domain.usecases.GetExpertsListUseCase;
import com.x2ketarol.askon.domain.usecases.LoginUserUseCase;
import com.x2ketarol.askon.domain.usecases.RecognizePhotoUseCase;
import com.x2ketarol.askon.domain.usecases.SendMessageUseCase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvResults;
    private Button btnTestLogin;
    private Button btnTestML;
    private Button btnTestExperts;
    private Button btnTestBooking;
    private Button btnTestChat;

    private UsersRepository usersRepository;
    private MLRepository mlRepository;
    private BookingRepository bookingRepository;
    private ChatRepository chatRepository;

    private LoginUserUseCase loginUserUseCase;
    private RecognizePhotoUseCase recognizePhotoUseCase;
    private GetExpertsListUseCase getExpertsListUseCase;
    private BookExpertTimeUseCase bookExpertTimeUseCase;
    private SendMessageUseCase sendMessageUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRepositories();
        initUseCases();
        initViews();
        setupListeners();
    }

    private void initRepositories() {
        usersRepository = new UsersRepositoryImpl();
        mlRepository = new MLRepositoryImpl();
        bookingRepository = new BookingRepositoryImpl();
        chatRepository = new ChatRepositoryImpl();
    }

    private void initUseCases() {
        loginUserUseCase = new LoginUserUseCase(usersRepository);
        recognizePhotoUseCase = new RecognizePhotoUseCase(mlRepository);
        getExpertsListUseCase = new GetExpertsListUseCase(usersRepository);
        bookExpertTimeUseCase = new BookExpertTimeUseCase(bookingRepository);
        sendMessageUseCase = new SendMessageUseCase(chatRepository);
    }

    private void initViews() {
        tvResults = findViewById(R.id.tvResults);
        btnTestLogin = findViewById(R.id.btnTestLogin);
        btnTestML = findViewById(R.id.btnTestML);
        btnTestExperts = findViewById(R.id.btnTestExperts);
        btnTestBooking = findViewById(R.id.btnTestBooking);
        btnTestChat = findViewById(R.id.btnTestChat);
    }

    private void setupListeners() {
        btnTestLogin.setOnClickListener(v -> testLogin());
        btnTestML.setOnClickListener(v -> testMLRecognition());
        btnTestExperts.setOnClickListener(v -> testGetExperts());
        btnTestBooking.setOnClickListener(v -> testBooking());
        btnTestChat.setOnClickListener(v -> testChat());
    }

    private void testLogin() {
        User user = loginUserUseCase.execute("test@example.com", "password123");
        String result = "=== LOGIN TEST ===\n" +
                "User ID: " + user.getId() + "\n" +
                "Name: " + user.getName() + "\n" +
                "Email: " + user.getEmail() + "\n";
        tvResults.setText(result);
    }

    private void testMLRecognition() {
        boolean isRecognized = recognizePhotoUseCase.execute("/path/to/photo.jpg");
        String result = "=== ML RECOGNITION TEST ===\n" +
                "Recognized Category: " + isRecognized + "\n";
        tvResults.setText(result);
    }

    private void testGetExperts() {
        List<Expert> experts = getExpertsListUseCase.execute("Plumbing");
        StringBuilder result = new StringBuilder("=== EXPERTS LIST TEST ===\n");
        for (Expert expert : experts) {
            result.append("Expert: ").append(expert.getName())
                    .append(" (").append(expert.getSpecialty()).append(")")
                    .append(" - Rating: ").append(expert.getRating())
                    .append("\n");
        }
        tvResults.setText(result.toString());
    }

    private void testBooking() {
        Booking booking = bookExpertTimeUseCase.execute("1", "2025-10-25", "15:00");
        String result = "=== BOOKING TEST ===\n" +
                "Booking ID: " + booking.getId() + "\n" +
                "Expert: " + booking.getExpertName() + "\n" +
                "Date: " + booking.getDate() + "\n" +
                "Time: " + booking.getTime() + "\n" +
                "Status: " + booking.getStatus() + "\n";
        tvResults.setText(result);
    }

    private void testChat() {
        Message message = sendMessageUseCase.execute("1", "2", "Hello from test!");
        List<Message> messages = chatRepository.getMessages("1", "2");
        
        StringBuilder result = new StringBuilder("=== CHAT TEST ===\n");
        result.append("New message sent: ").append(message.getText()).append("\n\n");
        result.append("All messages:\n");
        for (Message msg : messages) {
            result.append("From User ").append(msg.getSenderId())
                    .append(": ").append(msg.getText())
                    .append("\n");
        }
        tvResults.setText(result.toString());
    }
}

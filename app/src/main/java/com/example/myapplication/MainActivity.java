package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private LinearLayout lifecycleContainer;
    private RecyclerView booksRecyclerView;
    private MaterialToolbar toolbar;
    private List<String> lifecycleLog = new ArrayList<>();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        toolbar = findViewById(R.id.toolbar);
        welcomeTextView = findViewById(R.id.welcomeTextView);
        lifecycleContainer = findViewById(R.id.lifecycleContainer);
        booksRecyclerView = findViewById(R.id.booksRecyclerView);

        // Configurer la toolbar
        setSupportActionBar(toolbar);

        // Récupérer le nom de l'utilisateur
        Intent intent = getIntent();
        String userName = "Lecteur";
        if (intent != null && intent.hasExtra("USER_NAME")) {
            userName = intent.getStringExtra("USER_NAME");
        }
        welcomeTextView.setText("Bonjour " + userName + " ! 👋");

        // Initialiser la liste des livres
        List<Book> books = createBookList();
        BookAdapter adapter = new BookAdapter(books);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerView.setAdapter(adapter);

        // ========== VISUALISATION DU CYCLE DE VIE ==========
        addLifecycleMethod("onCreate()", "#4CAF50"); // Vert - Actif
    }

    // Méthode pour ajouter une méthode du cycle de vie à l'interface
    private void addLifecycleMethod(String methodName, String color) {
        String timestamp = timeFormat.format(new Date());

        // Créer un nouvel élément pour la méthode
        View methodView = LayoutInflater.from(this).inflate(R.layout.item_lifecycle, lifecycleContainer, false);

        TextView methodText = methodView.findViewById(R.id.methodText);
        TextView timeText = methodView.findViewById(R.id.timeText);
        View statusDot = methodView.findViewById(R.id.statusDot);

        // Configurer l'affichage
        methodText.setText(methodName);
        timeText.setText(timestamp);
        statusDot.setBackgroundColor(Color.parseColor(color));

        // Ajouter à la liste et à l'interface
        lifecycleLog.add(methodName + " - " + timestamp);
        lifecycleContainer.addView(methodView, 0); // Ajouter en haut

        // Supprimer le texte de placeholder s'il existe
        View placeholder = findViewById(R.id.placeholderText);
        if (placeholder != null) {
            lifecycleContainer.removeView(placeholder);
        }

        // Log dans Logcat
        Log.d("LIFECYCLE_VISUAL", methodName + " à " + timestamp);
    }

    // ========== MÉTHODES DU CYCLE DE VIE ==========

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(() -> addLifecycleMethod("onStart()", "#4CAF50"), 100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(() -> addLifecycleMethod("onResume()", "#4CAF50"), 200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        addLifecycleMethod("onPause()", "#FF9800"); // Orange - En pause
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.postDelayed(() -> addLifecycleMethod("onStop()", "#F44336"), 100); // Rouge - Arrêté
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        addLifecycleMethod("onRestart()", "#2196F3"); // Bleu - Redémarrage
    }

    @Override
    protected void onDestroy() {
        addLifecycleMethod("onDestroy()", "#9C27B0"); // Violet - Destruction
        super.onDestroy();
    }

    private List<Book> createBookList() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Le Petit Prince", "Antoine de Saint-Exupéry", "Conte philosophique"));
        books.add(new Book("1984", "George Orwell", "Dystopie"));
        books.add(new Book("Les Misérables", "Victor Hugo", "Roman historique"));
        books.add(new Book("Harry Potter", "J.K. Rowling", "Fantasy"));
        books.add(new Book("Dune", "Frank Herbert", "Science-fiction"));
        books.add(new Book("L'Étranger", "Albert Camus", "Philosophique"));
        books.add(new Book("Le Seigneur des Anneaux", "J.R.R. Tolkien", "Fantasy épique"));
        books.add(new Book("Orgueil et Préjugés", "Jane Austen", "Roman classique"));
        return books;
    }
}
package com.example.filmexp.filmexp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.filmexp.R;
import com.example.filmexp.api.ApiConfig;
import com.example.filmexp.api.TmdbApi;
import com.example.filmexp.model.Genre;
import com.example.filmexp.model.GenreResponse;
import com.example.filmexp.utils.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Spinner genreSpinner;
    private Button btnBuscar;
    private TextView lastSearchText;
    private PrefsManager prefsManager;
    private List<Genre> genres = new ArrayList<>();
    private Genre selectedGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        prefsManager = new PrefsManager(this);
        loadGenres();
        setupLastSearch();
    }

    private void initViews() {
        genreSpinner = findViewById(R.id.genreSpinner);
        btnBuscar = findViewById(R.id.btnBuscar);
        lastSearchText = findViewById(R.id.lastSearchText);

        btnBuscar.setOnClickListener(v -> searchFilmes());

        // Configurar o spinner para seleção de gênero
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0 && position <= genres.size()) {
                    selectedGenre = genres.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGenre = null;
            }
        });
    }

    private void loadGenres() {
        TmdbApi api = ApiConfig.getApiService();
        api.getGenres().enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    genres = response.body().getGenres();
                    setupSpinner();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao carregar gêneros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSpinner() {
        List<String> genreNames = new ArrayList<>();
        genreNames.add("Selecione um gênero...");

        for (Genre genre : genres) {
            genreNames.add(genre.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genreNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);
    }

    private void setupLastSearch() {
        String lastSearch = prefsManager.getLastSearch();
        if (!lastSearch.isEmpty()) {
            lastSearchText.setText("Última pesquisa: " + lastSearch);
            lastSearchText.setVisibility(View.VISIBLE);
        }
    }

    private void searchFilmes() {
        if (selectedGenre == null) {
            Toast.makeText(this, "Por favor, selecione um gênero", Toast.LENGTH_SHORT).show();
            return;
        }

        // Salvar última pesquisa
        prefsManager.saveLastSearch(selectedGenre.getName());

        // Atualizar texto da última pesquisa
        lastSearchText.setText("Última pesquisa: " + selectedGenre.getName());
        lastSearchText.setVisibility(View.VISIBLE);

        // Navegar para tela de resultados
        Intent intent = new Intent(MainActivity.this, FilmesActivity.class);
        intent.putExtra("genre_id", selectedGenre.getId());
        intent.putExtra("genre_name", selectedGenre.getName());
        startActivity(intent);
    }
}
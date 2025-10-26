package com.example.filmexp.filmexp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmexp.R;
import com.example.filmexp.adapter.Filmes_Adapter;
import com.example.filmexp.api.ApiConfig;
import com.example.filmexp.api.TmdbApi;
import com.example.filmexp.model.Filme;
import com.example.filmexp.model.Filme_Resposta;
import com.example.filmexp.utils.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmesActivity extends AppCompatActivity implements Filmes_Adapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private Filmes_Adapter adapter;
    private List<Filme> filmes = new ArrayList<>();
    private PrefsManager prefsManager;
    private int genreId;
    private String genreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        prefsManager = new PrefsManager(this);

        // Receber dados da intent
        genreId = getIntent().getIntExtra("genre_id", -1);
        genreName = getIntent().getStringExtra("genre_name");

        setupViews();
        loadFilmes();
    }

    private void setupViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Filmes_Adapter(filmes, prefsManager, this);
        recyclerView.setAdapter(adapter);

        // Configurar toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Filmes: " + genreName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadFilmes() {
        if (genreId == -1) {
            Toast.makeText(this, "Gênero inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TmdbApi api = ApiConfig.getApiService();
        api.getMoviesByGenre(genreId).enqueue(new Callback<Filme_Resposta>() {
            @Override
            public void onResponse(Call<Filme_Resposta> call, Response<Filme_Resposta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    filmes.clear();
                    filmes.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();

                    for(Filme filme : filmes){
                        Log.d("FilmesActivity", "Filme: " + filme.getTitle() + ", Poster: " + filme.getFullPosterPath());
                    }

                    adapter.notifyDataSetChanged();

                    if (filmes.isEmpty()) {
                        Toast.makeText(FilmesActivity.this, "Nenhum filme encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FilmesActivity.this, "Erro ao carregar filmes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Filme_Resposta> call, Throwable t) {
                Toast.makeText(FilmesActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
           Log.e("FilmesActivity", "Erro: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(Filme filme) {
        Toast.makeText(this, "Clicou em: " + filme.getTitle(), Toast.LENGTH_SHORT).show();
        // Aqui você pode implementar a tela de detalhes posteriormente
    }

    @Override
    public void onFavoriteClick(Filme filme, boolean isFavorite) {
        if (isFavorite) {
            prefsManager.addFavorite(filme.getId());
            Toast.makeText(this, filme.getTitle() + " adicionado aos favoritos", Toast.LENGTH_SHORT).show();
        } else {
            prefsManager.removeFavorite(filme.getId());
            Toast.makeText(this, filme.getTitle() + " removido dos favoritos", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
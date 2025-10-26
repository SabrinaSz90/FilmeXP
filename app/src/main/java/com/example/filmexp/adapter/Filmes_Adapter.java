package com.example.filmexp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmexp.R;
import com.example.filmexp.model.Filme;
import com.example.filmexp.utils.PrefsManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Filmes_Adapter extends RecyclerView.Adapter<Filmes_Adapter.FilmeViewHolder> {

    private List<Filme> filmes;
    private PrefsManager prefsManager;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Filme filme);
        void onFavoriteClick(Filme filme, boolean isFavorite);
    }

    public Filmes_Adapter(List<Filme> filmes, PrefsManager prefsManager, OnItemClickListener listener) {
        this.filmes = filmes;
        this.prefsManager = prefsManager;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_filmes, parent, false);
        return new FilmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeViewHolder holder, int position) {
        Filme filme = filmes.get(position);
        holder.bind(filme);
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    public class FilmeViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;
        private TextView movieTitle, movieRating, movieReleaseDate;
        private ImageButton favoriteButton;

        public FilmeViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieRating = itemView.findViewById(R.id.movieRating);
            movieReleaseDate = itemView.findViewById(R.id.movieReleaseDate);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }

        public void bind(Filme filme) {
            movieTitle.setText(filme.getTitle());
            movieRating.setText("Rating: " + filme.getVoteAverage());
            movieReleaseDate.setText(filme.getReleaseDate());

            // Carregar imagem com Picasso - abordagem mais robusta
            String posterUrl = filme.getFullPosterPath();
            if (posterUrl != null && !posterUrl.isEmpty()) {
                Picasso.get()
                        .load(posterUrl)
                        .placeholder(R.drawable.ic_filme_placeholder)
                        .error(R.drawable.ic_filme_placeholder)
                        .fit()
                        .centerCrop()
                        .into(moviePoster, new Callback() {
                            @Override
                            public void onSuccess() {
                                // Imagem carregada com sucesso
                            }

                            @Override
                            public void onError(Exception e) {
                                // Em caso de erro, usar placeholder
                                moviePoster.setImageResource(R.drawable.ic_filme_placeholder);
                            Log.d("Filme_Adapter", "Erro ao carregar imagem: " + posterUrl);
                            }
                        });
            } else {
                // Se não tiver imagem, mostrar placeholder
                moviePoster.setImageResource(R.drawable.ic_filme_placeholder);

            }

            // Configurar favorito
            boolean isFavorite = prefsManager.isFavorite(filme.getId());
            favoriteButton.setImageResource(isFavorite ?
                    R.drawable.ic_favorite : R.drawable.ic_favorite_border);

            // Listeners
            itemView.setOnClickListener(v -> listener.onItemClick(filme));
            favoriteButton.setOnClickListener(v -> {
                boolean newFavoriteState = !prefsManager.isFavorite(filme.getId());
                listener.onFavoriteClick(filme, newFavoriteState);

                // Atualizar ícone imediatamente
                favoriteButton.setImageResource(newFavoriteState ?
                        R.drawable.ic_favorite : R.drawable.ic_favorite_border);
            });
        }
    }
}
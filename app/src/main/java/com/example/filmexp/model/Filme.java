package com.example.filmexp.model;

public class Filme {
    private int id;
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private double voteAverage;

    public Filme(int id, String title, String overview, String posterPath, String releaseDate, double voteAverage) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getOverview() { return overview; }
    public String getPosterPath() { return posterPath; }
    public String getReleaseDate() { return releaseDate; }
    public double getVoteAverage() { return voteAverage; }

    public String getFullPosterPath() {
        if (posterPath == null || posterPath.isEmpty() || posterPath.equals("null")) {
            return null; // retorna null caso nao houver imagem.
        }
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }
}
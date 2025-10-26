package com.example.filmexp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class PrefsManager {
    private static final String PREFS_NAME = "FilmexP_Prefs";
    private static final String KEY_LAST_SEARCH = "last_search";
    private static final String KEY_FAVORITES = "favorites";

    private SharedPreferences sharedPreferences;

    public PrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveLastSearch(String genre) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LAST_SEARCH, genre);
        editor.apply();
    }

    public String getLastSearch() {
        return sharedPreferences.getString(KEY_LAST_SEARCH, "");
    }

    public void addFavorite(int movieId) {
        Set<String> favorites = getFavorites();
        favorites.add(String.valueOf(movieId));
        saveFavorites(favorites);
    }

    public void removeFavorite(int movieId) {
        Set<String> favorites = getFavorites();
        favorites.remove(String.valueOf(movieId));
        saveFavorites(favorites);
    }

    public boolean isFavorite(int movieId) {
        return getFavorites().contains(String.valueOf(movieId));
    }

    private Set<String> getFavorites() {
        return sharedPreferences.getStringSet(KEY_FAVORITES, new HashSet<>());
    }

    private void saveFavorites(Set<String> favorites) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY_FAVORITES, favorites);
        editor.apply();
    }
}
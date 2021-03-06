package com.example.nutritiontrackerguiv4.database;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {
    RecipeDAO dao;

    public RecipeRepository(RecipeDAO d){
        dao = d;
    }

    public void addRecipe(Recipe m){
        dao.insert(m);
    }

    public void updateRecipe(Recipe m){
        dao.update(m);
    }

    public void deleteRecipe(Recipe m){
        dao.delete(m);
    }

    public List<Recipe> getAllRecipe(){
        return dao.getAllRecipes();
    }

    public List<String> getAllNames(){ return dao.getAllnames();}

    public List<Recipe> findAllInfoForRecipe(long id){
        return dao.findAllInfoForRecipes(id);
    }

}

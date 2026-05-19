package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@Controller
public class RecipeController
{
	private final RecipeRepository recipeRepository;
	
	//コンストラクタインジェクション
	public RecipeController(RecipeRepository recipeRepository)
	{
		this.recipeRepository = recipeRepository;
	}
	
	@GetMapping("/recipes")
	public String index(Model model)
	{
		List<Recipe> recipes = recipeRepository.findAll();
		model.addAttribute("recipes", recipes);
		
		return "recipes";
	}
}

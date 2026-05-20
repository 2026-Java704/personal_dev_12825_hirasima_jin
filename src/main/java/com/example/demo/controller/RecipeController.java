package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RecipeRepository;

@Controller
public class RecipeController
{
	private final RecipeRepository recipeRepository;
	private final CategoryRepository categoryRepository;
	
	//コンストラクタインジェクション
	public RecipeController(RecipeRepository recipeRepository, CategoryRepository categoryRepository)
	{
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
	}
	
	//レシピ一覧表示画面
	@GetMapping("/recipes")
	public String index(
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") Integer categoryId,
			Model model)
	{
		//キーワード絞り込み検索
		List<Recipe> recipes = recipeRepository.findByKeywordAndCategoryId(keyword, categoryId);
		model.addAttribute("recipes", recipes);
		//全カテゴリー情報を取得
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		model.addAttribute("keyword", keyword);
		
		return "recipes";
	}
	
	//レシピ詳細画面表示
	@GetMapping("/recipes/detail/{id}")
	public String detail(
			@PathVariable int id,
			Model model)
	{
		//表示するレシピ
		Recipe recipe = recipeRepository.findById(id).get();
		model.addAttribute("recipe", recipe);
		
		return "recipeDetail";
	}
}

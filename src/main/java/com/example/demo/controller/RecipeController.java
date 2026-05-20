package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class RecipeController
{
	private final RecipeRepository recipeRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	
	//コンストラクタインジェクション
	public RecipeController(
			RecipeRepository recipeRepository,
			CategoryRepository categoryRepository, 
			UserRepository userRepository)
	{
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.userRepository = userRepository;
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
	
	//レシピ投稿画面表示
	@GetMapping("/recipes/add")
	public String create(Model model)
	{
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "createRecipe";
	}
	
	//レシピ投稿処理
	@PostMapping("/recipes/add")
	public String add(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String recipeDetail,
			@RequestParam(defaultValue = "") Integer userId)
	{
		//対応するユーザー・カテゴリーを取得
		User user = userRepository.findById(userId).get();
		Category category = categoryRepository.findById(categoryId).get();
		
		//投稿するレシピ
		Recipe recipe = new Recipe(name, recipeDetail, user, category);
		
		recipeRepository.save(recipe);
		
		return "redirect:/recipes";
	}
}

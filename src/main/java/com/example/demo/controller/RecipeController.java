package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.GoodRecord;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.GoodRecordRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class RecipeController
{
	private final RecipeRepository recipeRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	private final GoodRecordRepository goodRecordRepository;
	private final Account account;
	
	//コンストラクタインジェクション
	public RecipeController(
			RecipeRepository recipeRepository,
			CategoryRepository categoryRepository, 
			UserRepository userRepository,
			GoodRecordRepository goodRecordRepository,
			Account account)
	{
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.userRepository = userRepository;
		this.goodRecordRepository = goodRecordRepository;
		this.account = account;
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
		
		List<GoodRecord> goodRecords = goodRecordRepository.findByUserIdAndRecipeId(account.getId(), id);
		
		System.out.println("findRecords( " + account.getId() + ":" + id + ")");
		
		boolean isGood = false;
		
		//既にいいね済みか
		if(goodRecords.size() >= 1)
		{
			isGood = true;
			System.out.println("いいね済み！：" + goodRecords.size());
		}
		
		model.addAttribute("isGood", isGood);
		model.addAttribute("recipe", recipe);
		
		return "recipeDetail";
	}
	
	//レシピ詳細いいね処理
	@PostMapping("/recipes/detail/{id}")
	public String addGood(@PathVariable int id,
			@RequestParam(defaultValue = "1") Integer good,
			Model model)
	{
		//表示するレシピ
		Recipe recipe = recipeRepository.findById(id).get();
		
		
		
		recipe.addGood(good);
		recipeRepository.save(recipe);
		
		if(account.getId() != null)
		{
			List<GoodRecord> goodRecords = goodRecordRepository.findByUserIdAndRecipeId(account.getId(), id);
			if(goodRecords.size() < 1)
			{
				GoodRecord goodRecord = new GoodRecord(userRepository.findById(account.getId()).get(),
						recipe);
				
				goodRecordRepository.save(goodRecord);
			}
			else
			{
				goodRecordRepository.deleteAll(goodRecords);
			}
		}
		
		model.addAttribute("recipe", recipe);
		
		return "redirect:/recipes/detail/" + id;
	}
	
	//ユーザー詳細画面表示
	@GetMapping("/recipes/user/detail/{id}")
	public String userDetail(
			@PathVariable int id,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") Integer categoryId,
			Model model)
	{
		//表示するユーザー
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		
		//全カテゴリー情報を取得
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		//表示するユーザーが投稿したレシピ
		List<Recipe> recipes = recipeRepository.findByUserId(id, keyword, categoryId);
		model.addAttribute("recipes", recipes);
		
		model.addAttribute("keyword", keyword);
		
		return "userDetail";
	}
	
	//レシピ投稿画面表示
	@GetMapping("/recipes/add")
	public String create(Model model)
	{
		//ログインされていなければ
		if(account.getId() == null)
		{
			//ログイン画面へ
			return "redirect:/login";
		}
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "createRecipe";
	}
	
	//レシピ投稿処理
	@PostMapping("/recipes/add")
	public String add(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String material,
			@RequestParam(defaultValue = "") String recipeDetail,
			@RequestParam(defaultValue = "") Integer userId)
	{
		//対応するユーザー・カテゴリーを取得
		User user = userRepository.findById(userId).get();
		Category category = categoryRepository.findById(categoryId).get();
		
		//投稿するレシピ
		Recipe recipe = new Recipe(name, material, recipeDetail, user, category);
		
		recipeRepository.save(recipe);
		
		return "redirect:/recipes/user/detail/" + userId ;
	}
	
	//レシピ更新画面表示
	@GetMapping("/recipes/update/{id}")
	public String edit(
			@PathVariable int id,
			Model model)
	{
		//ログインされていなければ
		if(account.getId() == null)
		{
			//ログイン画面へ
			return "redirect:/login";
		}
		
		List<Category> categories = categoryRepository.findAll();
		
		//更新するレシピ
		Recipe recipe = recipeRepository.findById(id).get();
		
		//ログイン情報とレシピのユーザー情報が一致していなければ
		if(recipe.getUser().getId() != account.getId())
		{
			//ユーザーマイページに飛ばす
			return "redirect:/recipes/user/detail/" + account.getId();
		}
		
		model.addAttribute("categories", categories);
		model.addAttribute("recipe", recipe);
		
		return "updateRecipe";
	}
	
	//レシピ更新処理
	@PostMapping("/recipes/update/{id}")
	public String update(
			@PathVariable int id,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String material,
			@RequestParam(defaultValue = "") String recipeDetail,
			@RequestParam(defaultValue = "") Integer userId)
	{
		//対応するユーザー・カテゴリーを取得
		User user = userRepository.findById(userId).get();
		Category category = categoryRepository.findById(categoryId).get();
		
		System.out.println("input Val :" + material);
		
		//投稿するレシピ
		Recipe recipe = recipeRepository.findById(id).get();
		recipe.setName(name);
		recipe.setMaterial(material);
		recipe.setRecipe(recipeDetail);
		recipe.setCategory(category);
		recipe.setUser(user);
		
		recipeRepository.save(recipe);
		
		return "redirect:/recipes/user/detail/" + userId ;
	}
	
	//レシピの削除画面表示
	@GetMapping("/recipes/delete/{id}")
	public String deleteConfirm(
			@PathVariable int id,
			Model model)
	{
		//ログインされていなければ
		if(account.getId() == null)
		{
			//ログイン画面へ
			return "redirect:/login";
		}
		
		//表示するレシピ
		Recipe recipe = recipeRepository.findById(id).get();
		
		//ログイン情報とレシピのユーザー情報が一致していなければ
		if(recipe.getUser().getId() != account.getId())
		{
			//ユーザーマイページに飛ばす
			return "redirect:/recipes/user/detail/" + account.getId();
		}
		
		model.addAttribute("recipe", recipe);
		
		return "deleteRecipe";
	}
	
	//レシピ削除処理
	@PostMapping("/recipes/delete/{id}")
	public String delete(
			@PathVariable int id,
			Model model)
	{
		int userId = recipeRepository.findById(id).get().getUser().getId();
		
		recipeRepository.deleteById(id);
		
		return "redirect:/recipes/user/detail/" + userId;
	}
}

package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe 
{
	// フィールド //
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //レシピID
	private String name; //料理名
	private String recipe; //レシピ内容
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	// メソッド //
	
	//コンストラクタ
	public Recipe()
	{
		
	}
	public Recipe(String name, String recipe, User user, Category category)
	{
		this.name = name;
		this.recipe = recipe;
		this.user = user;
		this.category = category;
	}
	
	//ゲッター・セッター
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRecipe() {
		return recipe;
	}
	
	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Integer getId() {
		return id;
	}
}

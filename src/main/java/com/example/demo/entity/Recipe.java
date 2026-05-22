package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String material; //材料
	private String recipe; //レシピ内容
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	// メソッド //
	
	//コンストラクタ
	public Recipe()
	{
		
	}
	public Recipe(String name, String material, String recipe, User user, Category category)
	{
		this.name = name;
		this.material = material;
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
		return recipe.replaceAll("\n", "<br>");
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
	public String getMaterial() {
		return material.replaceAll("\n", "<br>");
	}
	
	public void setMaterial(String material) {
		this.material = material;
	}
	
	
}

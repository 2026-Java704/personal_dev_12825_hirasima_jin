package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "goodrecords")
public class GoodRecord 
{
	// フィールド //
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //いいね記録ID
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	
	// コンストラクタ //
	
	public GoodRecord()
	{
		
	}
	public GoodRecord(User user, Recipe recipe)
	{
		this.user = user;
		this.recipe = recipe;
	}
	
	//ゲッター・セッター
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public Integer getId() {
		return id;
	}
}

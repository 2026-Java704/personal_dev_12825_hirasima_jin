package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Account 
{

	// フィールド //
	
	private String name;
	
	private Integer id; //ユーザーID
	
	// メソッド //
	
	//コンストラクタ
	public Account()
	{
		
	}
	public Account(String name, Integer id)
	{
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}

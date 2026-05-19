package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController 
{
	private final UserRepository userRepository;
	
	//コンストラクタインジェクション
	public UserController (UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	//ログイン画面表示
	@GetMapping({"/", "/login", "logout"})
	public String index()
	{
		return "login";
	}
	
	//ログイン処理
	@PostMapping("/login")
	public String login(
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "") String password,
			Model model)
	{
		//メールアドレスとパスワードどちらかが一致するUserを取得
		List<User> userList = userRepository.findByEmailANDPassword(email, password);
		
		// 入力エラーチェックフェーズ //
		//エラーメッセージ
		String msg = new String();
		//一致したUserが無い場合
		if(userList.size() <= 0)
		{
			//一致したメールアドレスのUserが存在している
			if(userRepository.findByEmail(email).size() > 0)
			{
				msg = "パスワードが一致しません";
			}
			else
			{
				msg = "登録されていないメールアドレスです";
			}
			
			model.addAttribute("msg", msg);
			//ログイン画面に戻す
			return "login";
		}
		
		// -------------------------- //
		
		return "redirect:/recipes";
	}
	
	//新規登録画面表示
	@GetMapping("/users/new")
	public String create()
	{
		return "createUser";
	}
	
	//新規登録処理
	@PostMapping("/users/add")
	public String add(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "") String password,
			@RequestParam(defaultValue = "") String passwordConfirm,
			Model model)
	{
		// 入力エラーチェックフェーズ //
		//エラーメッセージリスト
		List<String> msgList = new ArrayList<String>();
		
		//名前未入力
		if(name.length() <= 0)
		{
			msgList.add("名前は必須です");
		}
		//名前20文字超過
		else if(name.length() > 20)
		{
			msgList.add("名前は20字以内で入力してください");
		}
		//メールアドレス未入力
		if(email.length() <= 0)
		{
			msgList.add("メールアドレスは必須です");
		}
		//メールアドレス重複
		if(userRepository.findByEmail(email).size() > 0)
		{
			msgList.add("入力されたメールアドレスは既に登録済みです");
		}
		//パスワード未入力
		if(password.length() <= 0)
		{
			msgList.add("パスワードは必須です");
		}
		//入力パスワード不一致
		if(!password.equals(passwordConfirm))
		{
			msgList.add("パスワード確認用が一致しません");
		}
		
		//エラーメッセージが一つでもあれば
		if(msgList.size() > 0)
		{
			model.addAttribute("msgList", msgList);
			//作成画面に戻す
			return "createUser";
		}	
		// -------------------------- //
		
		//新規登録するUser
		User user = new User(name, email, password);
		
		//登録処理
		userRepository.save(user);
		
		//ログイン画面にリダイレクト
		return "redirect:/login";
	}
}

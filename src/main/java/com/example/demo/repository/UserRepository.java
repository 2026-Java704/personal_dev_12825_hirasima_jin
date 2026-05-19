package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepository  extends JpaRepository<User, Integer>
{
	//一致したメールアドレスのUserを取得
	List<User> findByEmail(String email);
	
	//一致したパスワードのUserを取得
	List<User> findByPassword(String password);
	
	//メールアドレス・パスワードどちらかが一致したUserを取得
	@Query(value = "SELECT * FROM users WHERE email = :email "
			+ "AND password = :password"
			, nativeQuery = true)
	List<User> findByEmailANDPassword(@Param("email") String email, @Param("password") String password);
}

package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Recipe;

public interface RecipeRepository  extends JpaRepository<Recipe, Integer>
{
	//キーワード＆カテゴリー検索
	@Query(value = "SELECT * FROM recipes WHERE "
			+ "((name LIKE '%'||:keyword||'%' OR :keyword = '') "
			+ "OR (material LIKE '%'||:keyword||'%')) "
			+ "AND (category_id = :categoryId OR :categoryId IS NULL)"
			, nativeQuery = true)
	List<Recipe> findByKeywordAndCategoryId(
			@Param("keyword") String keyword,
			@Param("categoryId") Integer categoryId);
	
	//ユーザー名検索
	@Query(value = "SELECT * FROM recipes WHERE "
			+ "(name LIKE '%'||:keyword||'%' OR :keyword = '') "
			+ "AND (category_id = :categoryId OR :categoryId IS NULL)"
			, nativeQuery = true)
	List<Recipe> findByKeywordLikeUserName(
			@Param("keyword") String keyword,
			@Param("categoryId") Integer categoryId);

	//特定ユーザーIdによるキーワード＆カテゴリー検索
	@Query(value = "SELECT * FROM recipes WHERE "
			+ "(user_id = :userId) "
			+ "AND ((name LIKE '%'||:keyword||'%' OR :keyword = '') "
			+ "OR (material LIKE '%'||:keyword||'%')) "
			+ "AND (category_id = :categoryId OR :categoryId IS NULL)"
			, nativeQuery = true)
	List<Recipe> findByUserId(
			@Param("userId") Integer userId,
			@Param("keyword") String keyword,
			@Param("categoryId") Integer categoryId);
}

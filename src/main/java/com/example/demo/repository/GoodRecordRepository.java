package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.GoodRecord;

public interface GoodRecordRepository   extends JpaRepository<GoodRecord, Integer>
{
	//ユーザーIDによる検索
	List<GoodRecord> findByUserId(Integer userId);
	
	//レシピIDによる検索
	List<GoodRecord> findByRecipeId(Integer recipeId);
	
	//ユーザー＆レシピ検索
	@Query(value = "SELECT * FROM goodRecords WHERE "
			+ "(user_id = :userId) "
			+ "AND (recipe_id = :recipeId)"
			, nativeQuery = true)
	List<GoodRecord> findByUserIdAndRecipeId(
			@Param("userId") Integer userId,
			@Param("recipeId") Integer recipeId);
}

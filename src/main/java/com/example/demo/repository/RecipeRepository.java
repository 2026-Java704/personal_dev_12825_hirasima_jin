package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Recipe;

public interface RecipeRepository  extends JpaRepository<Recipe, Integer>
{
	@Query(value = "SELECT * FROM recipes WHERE name LIKE '%'||:keyword||'%' OR :keyword = ''"
			, nativeQuery = true)
	List<Recipe> findByKeyword(@Param("keyword") String keyword);
}

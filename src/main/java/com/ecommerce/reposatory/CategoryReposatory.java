package com.ecommerce.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Category;

@Repository
public interface CategoryReposatory extends JpaRepository<Category, Long> {
	Category findByName(String name);

	@Query("Select c from Category c Where c.name = :name And c.parentCategory = :parentCategory")

	Category findByNameAndParentCategory(@Param("name") String name, @Param("parentCategory") Category parentCategory);
}
package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private List<Category> categories = new ArrayList<>();
    private Long next = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
        category.setCategoryId(next++);
        categories.add(category);
        return category;
    }

    @Override
    public Category deleteCategory(Long id) {
        Iterator<Category> iterator = categories.iterator();
        while (iterator.hasNext()) {
            Category c = iterator.next();

            if (c.getCategoryId().equals(id)) {
                iterator.remove();
                return c;
            }
        }
        throw new RuntimeException("Category not found");
    }

    @Override
    public Category updateCategory(Long id,Category updatedCategory) {
        Iterator<Category> iterator = categories.iterator();
        while(iterator.hasNext()){
            Category c = iterator.next();

            if(c.getCategoryId().equals(id)){
                if(updatedCategory.getCategoryName()!=null){
                    c.setCategoryName(updatedCategory.getCategoryName());
                }
                return  c;
            }
        }
        throw new RuntimeException("Category not found");
    }
}

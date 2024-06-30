package osiride.vitt_be.mapper;

import org.springframework.stereotype.Component;

import osiride.vitt_be.domain.Category;
import osiride.vitt_be.dto.CategoryDTO;

@Component
public class CategoryMapper {
	
	public CategoryDTO toDto(Category object) {
		if(object == null) {
			return null;
		}
		
		CategoryDTO dto = new CategoryDTO();
	    dto.setId(object.getId());
	    dto.setCategoryName(object.getCategoryName());
	    return dto;
	}
	
	public Category toEntity(CategoryDTO object) {
		if(object == null) {
			return null;
		}
		
		Category entity = new Category();
		entity.setId(object.getId());
		entity.setCategoryName(object.getCategoryName());
	    return entity;
	}
}

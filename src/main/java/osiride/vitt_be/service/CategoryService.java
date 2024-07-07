package osiride.vitt_be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.Category;
import osiride.vitt_be.dto.CategoryDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.CategoryMapper;
import osiride.vitt_be.repository.CategoryRepository;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    /**
     * Get all Categories stored on DB 
     * @author Simone
     * @return List<CategoryDTO>
     */
    public List<CategoryDTO> getAll(){
    	log.info("SERVICE - Get All Category on DB - READ ALL");
    	return categoryRepository.findAll().stream().map(category -> categoryMapper.toDto(category)).toList();
    }
    
    /**
     * Find Category by id on DB. If there isn't a category with that id, 
     * throw NotFoundException. If id is null, throw BadRequestException
     * @author Simone
     * @param id
     * @return CategoryDTO
     * @throws BadRequestException
     * @throws NotFoundException
     */
    public CategoryDTO findById(Long id) throws BadRequestException, NotFoundException {
    	if(id == null) {
			log.error("SERVICE - Category id is null- FIND ONE");
    		throw new BadRequestException();
    	}
    	
    	Optional<Category> category = categoryRepository.findById(id);
    	if(category.isPresent()) {
        	log.info("SERVICE - Found a Category on DB - FIND ONE");
    		return categoryMapper.toDto(category.get());
    	}
    	else {
			log.error("SERVICE - Category not found - FIND ONE");
    		throw new NotFoundException();
    	}
    }
    
    /**
     * Create a Category by data given. If data is invalid, BadRequestException is thrown.
     * If Category Name is duplicated, DuplicatedValueException is thrown.
     * @author Simone
     * @param categoryDTO
     * @return CategoryDTO
     * @throws BadRequestException
     * @throws DuplicatedValueException
     */
    public CategoryDTO create(CategoryDTO categoryDTO) throws BadRequestException, DuplicatedValueException {
    	if(categoryDTO == null || !isDataValid(categoryDTO)) {
			log.error("SERVICE - Category data is invalid - CREATE");
    		throw new BadRequestException();
    	}
    	
    	Category category = categoryMapper.toEntity(categoryDTO);
    	category.setId(null);
		try {			
	    	return categoryMapper.toDto(categoryRepository.save(category));
		} catch (Exception e) {
			log.error("SERVICE - Dupicated Category Name - CREATE");
			throw new DuplicatedValueException();
		}
    }
    
    /**
     * Update a Category by data given. If data is invalid, BadRequestException is thrown.
     * If Category Name is duplicated, DuplicatedValueException is thrown.
     * @author Simone
     * @param categoryDTO
     * @return CategoryDTO
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws DuplicatedValueException
     */
    public CategoryDTO update(CategoryDTO categoryDTO) throws BadRequestException, NotFoundException,DuplicatedValueException {
    	if(categoryDTO == null || categoryDTO.getId() == null || !isDataValid(categoryDTO)) {
			log.error("SERVICE - Category data is invalid - UPDATE");
			throw new BadRequestException();
    	}
    	
    	Optional<Category> maybeCategory = categoryRepository.findById(categoryDTO.getId());
		if(maybeCategory.isEmpty()) {
			log.error("SERVICE - Category Not Found - UPDATE");
			throw new NotFoundException();
		}
		
		Category newCategory = categoryMapper.toEntity(categoryDTO);
		
		
		try {		
			newCategory = categoryRepository.save(newCategory);
		} catch (Exception e) {
			log.error("SERVICE - Dupicated Category Name - UPDATE");
			throw new DuplicatedValueException();
		}
		
		return categoryMapper.toDto(newCategory);
    }
    
    /**
     * Delete a Category by id. Finding controls given by method findById(Long id) on {@link CategoryService}
     * @author Simone
     * @param id
     * @return CategoryDTO
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws InternalServerException
     */
    public CategoryDTO deleteById(Long id) throws BadRequestException, NotFoundException, InternalServerException{    
    	CategoryDTO categoryDTO = findById(id);
    	categoryRepository.delete(categoryMapper.toEntity(categoryDTO));
    	if(categoryRepository.existsById(id)) {
			log.error("SERVICE - Category Not Deleted cause by Unknown Error - DELETE");
    		throw new InternalServerException();
    	}
    	
    	return categoryDTO;
    }
    
	private boolean isDataValid(CategoryDTO object) {
		return (object.getCategoryName() == null) 
				? false 
						: true;
	}
}

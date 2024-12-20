package osiride.vitt_be.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.dto.CategoryDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.service.CategoryService;

@Slf4j
@RestController
@RequestMapping("/api/category-management/")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Operation(summary = "Get all Category", description = "Get all Category ")
	@GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		List<CategoryDTO> result = categoryService.getAll();
		log.info("REST - Category's list size : {} - READ ALL", result.size());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@Operation(summary = "Find Category by id", description = "Get a Category by ID")
	@GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
		try {			
			CategoryDTO result = categoryService.findById(id);
			log.info("REST - Category found : {} - READ ONE", result);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (BadRequestException e) {
			log.error("REST - Bad information given - READ ONE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Category NOT found - READ ONE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Create category", description = "Creation category by given data")
	@PostMapping(value = "/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
		try {			
			CategoryDTO result = categoryService.create(categoryDTO);
			log.info("REST - Category created - CREATE");
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch(BadRequestException e) {
			log.error("REST - Bad information given - CREATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (DuplicatedValueException e) {
			log.error("REST - Duplicated Category Name Error - CREATE");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@Operation(summary = "Update category", description = "Update category by given data")
	@PutMapping(value = "/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO){
		try {
			CategoryDTO result = categoryService.update(categoryDTO);
			log.info("REST - Category Updated - UPDATE");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - UPDATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Category NOT found - UPDATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DuplicatedValueException e) {
			log.error("REST - Duplicated Category Name Error - UPDATE");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@Operation(summary = "Delete category by id", description = "Delete category by id")
	@DeleteMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> deleteCategoryById(@PathVariable Long id){
		try {
			CategoryDTO result = categoryService.deleteById(id);
			log.info("REST - Category Deleted, ID : {} - DELETE", id);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - DELETE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Category NOT found - DELETE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InternalServerException e) {
			log.error("REST - Error on deleting Category - DELETE");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

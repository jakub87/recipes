package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Answer;
import recipes.model.DTO.RecipeDTO;
import recipes.model.DTO.UserDTO;
import recipes.model.Recipe;
import recipes.service.RecipeService;
import recipes.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class RecipeController {

    private RecipeService recipeService;
    private UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public void registerUser(@Valid @RequestBody UserDTO userDTO) {
        if (userService.registerUser(userDTO)){
            throw new ResponseStatusException(HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/recipe/new")
    public Answer addRecipe(@Valid @RequestBody RecipeDTO recipeDTO, Principal principal) {
        return new Answer(recipeService.addRecipe(recipeDTO, principal.getName()));
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id){
        return recipeService.getRecipe(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable Long id,
                             Principal principal) {
        recipeService.deleteRecipe(id, principal.getName());
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> searchRecipe(@RequestParam Optional<String> category,
                                     @RequestParam Optional<String> name) {
        if (category.isPresent()) {
            return recipeService.searchByCategory(category.get());
        } else if (name.isPresent()) {
            return recipeService.searchByName(name.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@Valid @RequestBody RecipeDTO recipeDTO, @PathVariable Long id, Principal principal) {
        recipeService.updateRecipe(id, recipeDTO, principal.getName());
    }

}
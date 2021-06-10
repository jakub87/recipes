package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.DTO.RecipeDTO;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Long addRecipe(RecipeDTO recipeDTO, String loggedUser){
        User author = userRepository.findByEmail(loggedUser);
        Recipe recipe = new Recipe(recipeDTO.getName(), recipeDTO.getDescription(), recipeDTO.getCategory(),recipeDTO.getIngredients(), recipeDTO.getDirections(), author);
        recipeRepository.save(recipe);
        return recipe.getId();
    }

    public void updateRecipe(Long id, RecipeDTO recipeDTO, String loggedUser) {
        User user = userRepository.findByEmail(loggedUser);
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (recipe.get().getAuthor().equals(user)) {
            recipe.ifPresent(updateRecipe -> {
                updateRecipe.setDirections(recipeDTO.getDirections());
                updateRecipe.setDate(LocalDateTime.now());
                updateRecipe.setDescription(recipeDTO.getDescription());
                updateRecipe.setIngredients(recipeDTO.getIngredients());
                updateRecipe.setCategory(recipeDTO.getCategory());
                updateRecipe.setName(recipeDTO.getName());
                recipeRepository.save(updateRecipe);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            });
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> searchByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> searchByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public void deleteRecipe(Long id, String loggedUser) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        User user = userRepository.findByEmail(loggedUser);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (recipe.get().getAuthor().equals(user)) {
            recipeRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}

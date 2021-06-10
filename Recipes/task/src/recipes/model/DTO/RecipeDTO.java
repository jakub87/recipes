package recipes.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RecipeDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotNull
    @Size(min = 1)
    private String [] ingredients;
    @NotNull
    @Size(min = 1)
    private String [] directions;

    public RecipeDTO(@NotBlank String name, @NotBlank String description, @NotBlank String category, @NotNull @Size(min = 1) String[] ingredients, @NotNull @Size(min = 1) String[] directions) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}

package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String name;
    private String description;
    private String category;
    private LocalDateTime date;
    private String [] ingredients;
    private String [] directions;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_author")
    @JsonIgnore
    private User author;

    public Recipe(String name, String description, String category, String[] ingredients, String[] directions, User author) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.date = LocalDateTime.now();
        this.ingredients = ingredients;
        this.directions = directions;
        this.author = author;
    }
}

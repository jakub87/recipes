package recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private boolean status;

    @OneToMany(mappedBy = "author")
    private List<Recipe> recipeList;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.status = true;
        recipeList = new ArrayList<>();
    }
}
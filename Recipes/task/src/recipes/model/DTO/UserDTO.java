package recipes.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Data
public class UserDTO {
    @Pattern(regexp = ".{1,}[@].{1,}[.].{1,}")
    private String email;
    @Pattern(regexp = "\\S{8,}")
    private String password;

    public UserDTO(@Pattern(regexp = ".{1,}[@].{1,}[.].{1,}") String email, @Pattern(regexp = "\\S{8,}") String password) {
        this.email = email;
        this.password = password;
    }
}

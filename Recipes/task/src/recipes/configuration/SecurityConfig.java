package recipes.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .and().authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/recipe/**").authenticated()
                .and().headers().frameOptions().disable();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(
                        "SELECT email, password, status FROM user WHERE lower(email) = lower(?)")
                .authoritiesByUsernameQuery(
                        "select email, 'ROLE_USER' from user where email = ?") //fake role
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
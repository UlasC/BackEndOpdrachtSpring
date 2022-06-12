package ulas1.backend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ulas1.backend.component.JwtRequestFilter;
import ulas1.backend.domain.entity.Medewerker;
import ulas1.backend.service.JwtService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtService jwtService;

    @Autowired
    private DataSource dataSource;

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select gebruikersnaam, wachtwoord, enabled from medewerker where gebruikersnaam=?")
                .authoritiesByUsernameQuery("select gebruikersnaam, role from medewerker where gebruikersnaam=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/afspraken").hasAuthority(Medewerker.BALIEMEDEWERKER)
                .antMatchers("/afspraken/**").hasAuthority(Medewerker.BALIEMEDEWERKER)
                .antMatchers(HttpMethod.POST, "/autos").hasAuthority(Medewerker.BALIEMEDEWERKER)
                .antMatchers(HttpMethod.GET, "/autos/**").hasAnyAuthority(Medewerker.BALIEMEDEWERKER, Medewerker.MONTEUR)
                .antMatchers(HttpMethod.POST, "/handelingen").hasAuthority(Medewerker.BACKENDMEDEWERKER)
                .antMatchers(HttpMethod.GET, "/handelingen/**").hasAnyAuthority(Medewerker.MONTEUR, Medewerker.BACKENDMEDEWERKER)
                .antMatchers("/handelingen/**").hasAuthority(Medewerker.BACKENDMEDEWERKER)
                .antMatchers("/klanten").hasAuthority(Medewerker.BALIEMEDEWERKER)
                .antMatchers("/klanten/**").hasAuthority(Medewerker.BALIEMEDEWERKER)
                .antMatchers("/mankementen/{mankementId}/bon").hasAuthority(Medewerker.BALIEMEDEWERKER)
                .antMatchers("/mankementen").hasAuthority(Medewerker.MONTEUR)
                .antMatchers("/mankementen/**").hasAuthority(Medewerker.MONTEUR)
                .antMatchers("/medewerkers").hasAuthority(Medewerker.BACKENDMEDEWERKER)
                .antMatchers("/medewerkers/**").hasAuthority(Medewerker.BACKENDMEDEWERKER)
                .antMatchers(HttpMethod.GET, "/onderdelen/**").hasAnyAuthority(Medewerker.MONTEUR, Medewerker.BACKENDMEDEWERKER)
                .antMatchers(HttpMethod.PUT,"/onderdelen/{artikelnummer}/voorraad").hasAnyAuthority(Medewerker.MONTEUR, Medewerker.BACKENDMEDEWERKER)
                .antMatchers("/onderdelen").hasAuthority(Medewerker.BACKENDMEDEWERKER)
                .antMatchers("/onderdelen/**").hasAuthority(Medewerker.BACKENDMEDEWERKER)
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }
}

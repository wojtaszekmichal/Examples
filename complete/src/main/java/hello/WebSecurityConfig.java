package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home")
                .permitAll()
                .antMatchers("/webjars/**")
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/book/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/books/all")
                .hasAnyRole("USER","ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    /*
     * @Bean
     * @Override public UserDetailsService userDetailsService() { return new UserDetailsService(){
     * @Override public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
     * return new UserDetails() {
     * @Override public Collection<? extends GrantedAuthority> getAuthorities() { return
     * Arrays.asList(new SimpleGrantedAuthority("USER")); }
     * @Override public String getPassword() { return "root"; }
     * @Override public String getUsername() { return "root"; }
     * @Override public boolean isAccountNonExpired() { return true; }
     * @Override public boolean isAccountNonLocked() { return true; }
     * @Override public boolean isCredentialsNonExpired() { return true; }
     * @Override public boolean isEnabled() { return true; } }; } }; }
     * @Bean public NoOpPasswordEncoder passwordEncoder() { return (NoOpPasswordEncoder)
     * NoOpPasswordEncoder.getInstance(); }
     */

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN", "USER")
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(admin);

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        inMemoryUserDetailsManager.createUser(user);

        return inMemoryUserDetailsManager;
    }
}
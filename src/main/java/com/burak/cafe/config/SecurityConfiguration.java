package com.burak.cafe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

//Uygulamanın içeriği için Bean tanımlamalarının kaynağı gibi sınıfı etiketlemeyi sağlar.
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;// veritabanı bağlantılarıyla çalışmak için standart bir yöntem sunar.

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
        auth.
                inMemoryAuthentication()
                .withUser("admin@admin.com")
                .password("12345")
                .roles("ADMIN")
                .and()
                .withUser("asd@gmail.com")
                .password("12345")
                .roles("USER")
                .and()
                .withUser("visitor@gmail.com")
                .password("12345")
                .roles("VISITOR");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()

                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()//izin veriyoruz herşeye eriimesine
                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()//eğer yetkisi varsa hepsine ulaşssın

                .authenticated().and().csrf().disable().formLogin()//csrf güvenlik sorunu gibi mesela direk ulaşmayı engeller
                .loginPage("/login").failureUrl("/login?error=true")

                .defaultSuccessUrl("/default")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/accessdenied");//parametre controller RequestMappingi

        http.formLogin().defaultSuccessUrl("/default");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()//antmatchers ile başlayan urlleri  görmezden gel
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
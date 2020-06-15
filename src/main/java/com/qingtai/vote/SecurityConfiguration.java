package com.qingtai.vote;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter 
{



    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        // http.csrf().disable();
        http.authorizeRequests()
                // .antMatchers("/","/login").permitAll()
                .antMatchers("/all").access("hasRole('USER')")
                .antMatchers("/test").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login").permitAll()
                // .successHandler(new ForwardAuthenticationSuccessHandler("/loginStatus?status=true"))
                // 强制指定登陆成功后跳转的路劲
                .defaultSuccessUrl("/").permitAll()
                // .successForwardUrl("/login")
                // .successHandler(loginSuccessHandler())
                .failureUrl("/login?error").permitAll()
        // 设置缓存，默认2周有效
        .and()
        .rememberMe().tokenValiditySeconds(60*60*24*7).key("mykey");
        // 设置登出的路径和登出成功后访问的路径
        // .and().logout().logoutUrl("/loginOut").logoutSuccessUrl("/login").permitAll()
        // disable csrf
        // .and().csrf().disable();
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception 
    // {
    //     auth
    //         .inMemoryAuthentication()
    //         .passwordEncoder(new BCryptPasswordEncoder())
    //         .withUser("qingtai")
    //         .password(new BCryptPasswordEncoder().encode("admin"))
    //         .roles("USER");
    // }
    @Override
    // 重写了configure参数为AuthenticationManagerBuilder的方法
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        // auth.userDetailsService(new MyUserDetailsService());
        auth
        .userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        
    }
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { //登入处理
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                User userDetails = (User) authentication.getPrincipal();
                logger.info("USER : " + userDetails.getUsername() + " LOGIN SUCCESS !  ");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder(4);
    }

    @Override
    public void configure(WebSecurity web) throws Exception 
    {
        //ignore some url
        web.ignoring().antMatchers("/vercode");
    }



    @Bean
    public UserDetailsService userDetailsService() 
    {

        return new UserDetailsService() 
        {
            @Autowired
            private UserRepository userRepository;

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
            {

                User user = userRepository.findByUsername(username);
                
                if (user != null) 
                {
                    return new UserSecurity(user);
                }
                throw new UsernameNotFoundException("User '" + username + "' not found.");
            }
        };
    }




}
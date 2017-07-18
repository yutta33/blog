package jp.gr.java_conf.hirady.blog.app.common;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security設定クラス.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {

    // 認可
    http.authorizeRequests()
        .antMatchers("/", "/entry/**", "/mn", "/css/**").permitAll() // アクセス許可
        .anyRequest().authenticated() // 上記以外は要認証
        .and()
        .formLogin()
        .and()
        .rememberMe();

    // ログイン
    http.formLogin()
        .loginProcessingUrl("/login") // 認証処理
        .loginPage("/mn") // ログインフォーム
        .defaultSuccessUrl("/mn/top") // 認証成功時の遷移先
        .usernameParameter("login_id").passwordParameter("login_password") // ユーザー名、パスワードのパラメータ名
        .and();

    // ログアウト
    http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) // ログアウト処理のパス
        .logoutSuccessUrl("/index"); // ログア

  }

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("hoge")
        .password("hoge")
        .roles("ADMIN")
        .authorities(Collections.emptyList());
  }
}

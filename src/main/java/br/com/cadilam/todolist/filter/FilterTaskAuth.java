package br.com.cadilam.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Com servelet
// @Component
// public class FilterTaskAuth implements Filter {

//     @Override
//     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//             throws IOException, ServletException {
//         System.out.println("Passou pelo filtro");
//         chain.doFilter(request, response);
//     }
// }

// Com Spring Boot
@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authorization = request.getHeader("Authorization");

        var authEncode = authorization.substring("Basic".length()).trim();

        var authDecode = Base64.getDecoder().decode(authEncode);

        var authString = new String(authDecode);

        System.out.println("Authorization");
        System.out.println(authString);

        var user = authString.split(":")[0];
        var password = authString.split(":")[1];
    }
}
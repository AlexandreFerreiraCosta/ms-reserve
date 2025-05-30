package br.com.tecnologia.smuk.ms_reserve.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.tecnologia.smuk.ms_reserve.user.IUserRepository;
import br.com.tecnologia.smuk.ms_reserve.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if(servletPath.startsWith("/task")){
            var authorization = request.getHeader("Authorization");

            var userAuthorization = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(userAuthorization);

            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            User user = iUserRepository.findByUsername(username);

            if(user == null){
                response.sendError(401);
            }else{
                if(BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
                    request.setAttribute("userId",user.getId());
                    filterChain.doFilter(request,response);
                }else{
                    response.sendError(401);
                }
            }
        }else{
            filterChain.doFilter(request,response);
        }
    }
}

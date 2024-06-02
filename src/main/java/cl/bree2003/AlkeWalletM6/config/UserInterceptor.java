package cl.bree2003.AlkeWalletM6.config;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("userId") != null){
            Long userId = (Long) session.getAttribute("userId");
            Optional<UserEntity> optionalUser = userService.findUserById(userId);
            if(optionalUser.isPresent()){
                request.setAttribute("user", optionalUser.get());
            } else {
                return false;
            }
        }
        return true;
    }
}

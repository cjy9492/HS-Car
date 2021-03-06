package org.agile.resolver;

import org.agile.annotation.LoginUser;
import org.agile.interceptor.AuthorizationInterceptor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hundsun.hscar.entity.UserEntity;
import com.hundsun.hscar.service.api.IUserService;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 * HandlerMethodArgumentResolver主要负责执行handler前参数准备工作
 * 
 * @author zhangmm
 * @email phoenix122411@126.com
 * @date 2017-05-06
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 解析器是否支持参数
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * 解析参数,填充到参数值
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.LOGIN_USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if(object == null) {
            return null;
        }

        //获取用户信息
        UserEntity user = userService.queryObjectById((Long)object);

        return user;
    }
}

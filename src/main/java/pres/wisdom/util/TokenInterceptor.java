package pres.wisdom.util;
import java.lang.reflect.Method;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pres.wisdom.util.Token;
/**
 * 
 * 防止重复提交
 * 
 * @author wsc
 * 
 *         2016年12月5日
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	private static final String TOKEN_NAME = "token";
	@Override
	public boolean preHandle(HttpServletRequest request,
	HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				boolean need2SaveSession = annotation.save();
				if (need2SaveSession) {
					request.getSession(false).setAttribute("token",
							UUID.randomUUID().toString());
				}
				boolean need2RemoveSession = annotation.remove();
				if (need2RemoveSession) {
					if (isRepeatSubmit(request)) {
						return false;
					}
					request.getSession(false).removeAttribute("token");
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}
	/** 检查是否是重复提交（为空，不相等） */
	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute(
				TOKEN_NAME);// 服务端
		if (serverToken == null) {
			return true;
		}
		String clientToken = request.getParameter(TOKEN_NAME);// 客户端
		if (clientToken == null) {
			return true;
		}
		if (!serverToken.equals(clientToken)) {
			return true;
		}
		return false;
	}
}
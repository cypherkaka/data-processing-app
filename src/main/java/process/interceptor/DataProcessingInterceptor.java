package process.interceptor;

import process.service.DataProcessingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor for all common operation throughout end-points.
 */
@Component
public class DataProcessingInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataProcessingRepository dataProcessingRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //Implement if require
        return Boolean.TRUE;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //Implement if require
    }

    /**
     * Logging each request with HTTP status.
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        StringBuilder messageBuilder = new StringBuilder(request.getRequestURI());
        messageBuilder.append(" : ").append(response.getStatus()).append(" - ").append(httpStatus.getReasonPhrase());
        logger.info(messageBuilder.toString());
    }
}

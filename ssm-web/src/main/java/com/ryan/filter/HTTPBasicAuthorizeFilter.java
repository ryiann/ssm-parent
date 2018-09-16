package com.ryan.filter;

import com.ryan.constant.Constants;
import com.ryan.enums.ResponseCode;
import com.ryan.pojo.ResponseEntity;
import com.ryan.utils.JWTUtil;
import com.ryan.utils.JsonUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * API 调用权限控制
 * @author YoriChen
 * @date 2018/5/21
 */
public class HTTPBasicAuthorizeFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext context = filterConfig.getServletContext();  
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);  
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;  
        httpResponse.setCharacterEncoding("UTF-8");    
        httpResponse.setContentType("application/json; charset=utf-8"); 
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
        httpResponse.setHeader("Access-Control-Expose-Headers", "*");
        String auth = httpRequest.getHeader(Constants.AUTHORIZATION);
        //白名单，放过
        if (ApiWhiteData.getWhiteApis().contains(httpRequest.getRequestURI())) {
        	chain.doFilter(httpRequest, response);
        	return;
		}

        //验证TOKEN
		ResponseEntity jwt;
		if (StringUtils.hasText(auth)) {
            jwt = JWTUtil.checkToken(auth);
		} else{
            jwt = new ResponseEntity<>(ResponseCode.TOKEN_NO_AUTH.getCode(), ResponseCode.TOKEN_NO_AUTH.getMessage());
		}
		if (!Constants.RESPONSE_SUCCESS.equals(jwt.getCode())) {
			PrintWriter print = httpResponse.getWriter();
			print.write(JsonUtil.toJson(jwt));
			return;
		}
		
		chain.doFilter(httpRequest, response);
	}
	
	@Override
	public void destroy() {
		
	}

}

package filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.LogRecord;


@WebFilter(urlPatterns ={"/whumap.html","/whu_lwy.html","/whu_wwj.html","/whu_zyy.html"})
public class LoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    //
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 写Token解析
        // 成功-允许访问
        // 失败-重定向到登陆页面

        System.out.println("filter");

        // 将请求与响应向下转换
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        // 获得访问界面的url文件地址
        String servletPath = req.getServletPath();
        HttpSession session = req.getSession();
        // 获取登录状态
        String flag = (String) session.getAttribute("flag");
        /* 判断是否是登录页、首页、登录servlet */
        if (servletPath != null && (servletPath.equals("http://localhost:8081/webgis/login.html") || servletPath.equals("http://localhost:8081/webgis/index.html") || servletPath.equals("http://localhost:8081/webgis/loginservlet"))) {
            // 是则直接转发到下一个组件
            chain.doFilter(request, response);
        } else {
            // 否，则验证登录状态
            if (flag != null) {
                if (flag.equals("login_success")) {
                    // 登录成功，直接转发到下一个组件
                    chain.doFilter(request, response);
                    return;
                } else {
                    // 登录失败，跳转到登录页，并保证当前网页的url文件路径
                    req.setAttribute("msg", "登录失败");
                    req.getRequestDispatcher("/login.html").forward(req,response);
                    //req.setAttribute("return_uri", servletPath);
                    //RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                    //rd.forward(req, res);
                }
            } else {
                // 未登录，跳转到登录页，并保证当前网页的url文件路径
                req.setAttribute("msg", "您尚未登录，请登录");
                req.getRequestDispatcher("/login.html").forward(req,response);
//                req.setAttribute("return_uri", servletPath);
//                RequestDispatcher rd = req.getRequestDispatcher("/index.html");
//                rd.forward(req, res);
            }
        }




        //2. 获取用户是否包含在会话里面
//        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
//        session.getAttribute("user");
//        // 做判断
        // 成功-允许访问
        // 失败-重定向到登陆页面
    }

    @Override
    public void destroy() {

    }
}

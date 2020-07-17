package com.zwboy;

import com.zwboy.controller.FirstController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LiJiaChang
 * @Date: 2020/7/12 23:01
 */
@WebServlet("/")
public class MyServlet extends HttpServlet {

    //servlet容器只会在第一次使用的时候才会被创建
    @Override
    public void init() throws ServletException {
        System.out.println("启动Servlet容器");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath() == "/first" && req.getMethod() == "get") {
            new FirstController().getFirstService(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("执行了doGet");
        req.setAttribute("name", "这是servlet页面");
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("销毁Servlet容器");
    }
}

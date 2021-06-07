package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.entity.Role;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.Broken_Hammer.Constants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher rd;
    @Mock
    Connection connection;
    @Mock
    DBManager dbManager;
    @Mock
    DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    @Test
    public void vv() throws ServletException, IOException, SQLException, NamingException {
        final UserServlet servlet = new UserServlet();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("page")).thenReturn("1");
        when(session.getAttribute(ROLE_ID)).thenReturn(2);
        when(session.getAttribute(USER_ID)).thenReturn(2);
        when(dbManager.getConnection()).thenReturn(connection);
        when(dbManager.getDataSource()).thenReturn(dataSource);
        System.out.println(Role.getRoleById((Integer) session.getAttribute(ROLE_ID)));

        when(request.getRequestDispatcher("/orders.jsp")).thenReturn(rd);

        servlet.doGet(request, response);

//        verify(request, times(1)).getRequestDispatcher("/orders.jsp");
//        verify(request, never()).getSession();
//        verify(rd).forward(request, response);
    }

}
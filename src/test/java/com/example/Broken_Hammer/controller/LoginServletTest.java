package com.example.Broken_Hammer.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    LoginServlet loginServlet;

    @Before
    public void setUp() {
        loginServlet = new LoginServlet();
    }

    @Test
    public void testGet() throws IOException {
        loginServlet.doGet(request, response);
        ArgumentCaptor<String> dispatcherArgument = ArgumentCaptor.forClass(String.class);
        verify(request).getRequestDispatcher(dispatcherArgument.capture());
        assertEquals("/login.jsp", dispatcherArgument.getValue());
    }

}
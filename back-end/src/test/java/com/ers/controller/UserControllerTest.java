package com.ers.controller;

import com.ers.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    private static UserController userController;
    private static UserService userService;
    private static HttpServletRequest request;
    private static HttpServletResponse response;


    @BeforeAll
    public static void setup(){
        userService = mock(UserService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userController = new UserController(userService);
    }


    @Test
    @DisplayName("test delete")
    public void testControllerDelete() throws IOException {
        when(request.getRequestURI()).thenReturn("/test/test/testid");
        when(userService.deleteUser(any())).thenReturn("{\"success\": \"deleted\"}");
        when(response.getWriter()).thenReturn(new PrintWriter(System.out));
        userController.deleteUsers(request,response);
        Assertions.assertEquals(response.getStatus(),0);
    }

    @Test
    @DisplayName("test put")
    public void testControllerPut() throws IOException {
        when(request.getRequestURI()).thenReturn("/test/test/testid");
        when(userService.replaceUser(any())).thenReturn("{\"success\": \"deleted\"}");
        when(response.getWriter()).thenReturn(new PrintWriter(System.out));
        String test = "{\"success\": \"deleted\"}";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        when(request.getInputStream()).thenReturn(new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        });
        userController.putUsers(request,response);
        Assertions.assertEquals(response.getStatus(),0);
    }
    @Test()
    @DisplayName("test get")
    public void testGetUsers() throws IOException {
        when(request.getRequestURI()).thenReturn("/test/test/testid");
        when(userService.getUserByUserId(any())).thenReturn("{\"success\": \"deleted\"}");
        when(response.getWriter()).thenReturn(new PrintWriter(System.out));
        userController.getUsers(request,response);
        Assertions.assertEquals(response.getStatus(),0);
    }

    @Test
    @DisplayName("test post")
    public void testPostUsers() throws IOException {
        when(request.getRequestURI()).thenReturn("/test/test/testid");
        when(userService.createUser(any())).thenReturn("{\"success\": \"deleted\"}");
        when(response.getWriter()).thenReturn(new PrintWriter(System.out));
        userController.postUsers(request,response);
        Assertions.assertEquals(response.getStatus(),0);
    }

}

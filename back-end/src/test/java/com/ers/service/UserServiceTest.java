package com.ers.service;

import com.ers.dao.Dao;
import com.ers.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private static Dao dao;
    private static UserService userService;
    private static User responseUser;
    private static ObjectMapper objectMapper;
    private static List<User> userList;

    @BeforeAll
    public static void setup(){
        dao =  mock(Dao.class);
        objectMapper = new ObjectMapper();
        responseUser = new User();
        responseUser.setId(new ObjectId("609ae0d7f1c1765449c0c283"));
        responseUser.setFirstName("testFirstName");
        responseUser.setLastName("testLastName");
        responseUser.setUsername("testUsername");
        responseUser.setPassword("testPassword");
        responseUser.setEmail("test@gmail.com");
        responseUser.setType("Employee");
        userList = new ArrayList<>();
        userList.add(responseUser);
        userService = new UserService(dao);

    }

    @Test
    @DisplayName("Get user by id")
    public void testGetUserServiceGetUserById(){
        User user = new User();
        user.setId(new ObjectId("609ae0d7f1c1765449c0c283"));
        when(dao.getUser(any())).thenReturn(responseUser);
        String jsonString = convertUserToJson(user);
        String response  = userService.getUserByUserId(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertEquals("testFirstName",node.get("firstName").textValue());
    }

    @Test
    @DisplayName("Get user for non existing user")
    public void testGetUserForNonExistingUser(){
        User user = new User();
        user.setId(new ObjectId("609ae0d7f1c1765449c0c282"));
        when(dao.getUser(any())).thenReturn(null);
        String jsonString = convertUserToJson(user);
        String response = userService.getUserByUserId(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    @Test
    @DisplayName("Get all users")
    public void testGetAllUsers(){
        when(dao.getAllUsers()).thenReturn(userList);
        String response = userService.getAllUsers();
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertEquals(node.get("users").get(0).get("username").asText(),"testUsername");
    }

    @Test
    @DisplayName("Get all users when no users exist.")
    public void testGetAllUsersWhenThereAreNoUsers(){
        when(dao.getAllUsers()).thenReturn(new ArrayList<>());
        String response = userService.getAllUsers();
        JsonNode node= convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    @Test
    @DisplayName("Test create")
    public void testCreateUser(){
        User userNoId = new User();
        userNoId.setFirstName("Juan");
        userNoId.setLastName("Toledo");
        userNoId.setType("Employee");
        userNoId.setEmail("jtoledo@gmail.com");
        userNoId.setUsername("jtoledo");
        userNoId.setPassword("password");
        when(dao.createUser(any())).thenReturn(true);
        String jsonString = convertUserToJson(userNoId);
        String response = userService.createUser(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("success"));
    }

    @Test
    @DisplayName("Test create when user has invalid format")
    public void testCreateUserWithInvalidUser(){
        User userNoId = new User();
        userNoId.setFirstName("Juan");
        userNoId.setLastName("Toledo");
        when(dao.createUser(any())).thenReturn(false);
        String jsonString = convertUserToJson(userNoId);
        String response = userService.createUser(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    @Test
    @DisplayName("Test replace user")
    public void testReplaceUser(){
        when(dao.replaceUser(any())).thenReturn(true);
        String jsonString = convertUserToJson(responseUser);
        String response = userService.replaceUser(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("success"));
    }

    @Test
    @DisplayName("Test replace with user doesn't exists")
    public void testReplaceWithInvalidUser(){
        when(dao.replaceUser(any())).thenReturn(false);
        User user = new User();
        user.setId(new ObjectId("609ae0d7f1c1765449c0c286"));
        String jsonString = convertUserToJson(user);
        String response = userService.replaceUser(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));

    }

    @Test
    @DisplayName("Test delete user")
    public void testDeleteUser(){
        when(dao.deleteUser(any())).thenReturn(true);
        String jsonString = convertUserToJson(responseUser);
        String response = userService.deleteUser(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("success"));
    }

    @Test
    @DisplayName("Test delete user with invalid id")
    public void testDeleteUserWithInvalidId(){
        when(dao.deleteUser(any())).thenReturn(false);
        User user = new User();
        user.setId(new ObjectId("609ae0d7f1c1765449c0c286"));
        String jsonString = convertUserToJson(user);
        String response = userService.deleteUser(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    @Test
    @DisplayName("Test login user")
    public void testLoginUser(){
        when(dao.getUserByUsername(any())).thenReturn(responseUser);
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        String jsonString = convertUserToJson(user);
        String response = userService.loginUser(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertFalse(node.has("error"));
    }

    @Test
    @DisplayName("Test login user invalid credentials")
    public void testLoginUserInvalidCredentials(){
        when(dao.getUserByUsername(any())).thenReturn(responseUser);
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword234");
        String jsonString = convertUserToJson(user);
        String response = userService.loginUser(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }


    private String convertUserToJson(User user){
        String jsonString;
        try{
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            return jsonString;
        }catch(JsonProcessingException exception){
            throw new RuntimeException("Error converting user to json");
        }
    }

    private JsonNode convertJsonToJsonNode(String jsonString){
        JsonNode node;
        try{
            node = objectMapper.readTree(jsonString);
            return node;
        }catch(JsonProcessingException exception){
            throw new RuntimeException("Failed converting string to JsonNode.");
        }
    }
}

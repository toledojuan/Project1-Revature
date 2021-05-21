package com.ers.service;

import com.ers.dao.Dao;
import com.ers.model.Reimbursement;
import com.ers.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReimbursementServiceTest {
    private static Dao dao;
    private static ReimbursementService reimbursementService;
    private static Reimbursement reimbursement;
    private static ObjectMapper objectMapper;
    private static List<Reimbursement> reimbursementList;

    @BeforeAll
    public static void setup(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dao =  mock(Dao.class);
        objectMapper = new ObjectMapper();
        reimbursement = new Reimbursement();
        reimbursement.setId(new ObjectId("609aa7f23d980c5d6584ecf8"));
        reimbursement.setCost(200);
        reimbursement.setDate(new Date());
        reimbursement.setDescription("testDescription");
        reimbursement.setEmployeeId(new ObjectId("609ae0d7f1c1765449c0c283"));
        reimbursement.setStatus("Pending");
        reimbursementList = new ArrayList<>();
        reimbursementList.add(reimbursement);
        reimbursementService = new ReimbursementService(dao);

    }

    @Test
    @DisplayName("Get reimbursement by id")
    public void testGetUserServiceGetUserById(){
        Reimbursement tempReimbursement = new Reimbursement();
        tempReimbursement.setId(new ObjectId("609aa7f23d980c5d6584ecf8"));
        when(dao.getReimbursement(any())).thenReturn(reimbursement);
        String jsonString = convertUserToJson(tempReimbursement);
        String response  = reimbursementService.getReimbursementById(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertEquals("Pending",node.get("status").textValue());
    }

    @Test
    @DisplayName("Get user for non existing user")
    public void testGetUserForNonExistingUser(){
        Reimbursement tempReimbursement = new Reimbursement();
        tempReimbursement.setId(new ObjectId("609aa7f23d980c5d6584ecf7"));
        when(dao.getReimbursement(any())).thenReturn(null);
        String jsonString = convertUserToJson(tempReimbursement);
        String response = reimbursementService.getReimbursementById(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }


    @Test
    @DisplayName("Get all reimbursements")
    public void testGetAllReimbursements(){
        when(dao.getAllReimbursements()).thenReturn(reimbursementList);
        String response = reimbursementService.getAllReimbursements();
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertEquals(node.get("reimbursements").get(0).get("status").asText(),"Pending");
    }

    @Test
    @DisplayName("Get all reimbursements when no reimbursements exist.")
    public void testGetAllReimbursementsWhenNoReimbursementExists(){
        when(dao.getAllReimbursements()).thenReturn(new ArrayList<>());
        String response = reimbursementService.getAllReimbursements();
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    @Test
    @DisplayName("Get reimbursements by employeeId")
    public void testGetReimbursementsByEmployeeId(){
        User user  = new User();
        user.setId(new ObjectId("609ae0d7f1c1765449c0c283"));
        when(dao.getReimbursementsByEmployeeId(any())).thenReturn(reimbursementList);
        String jsonString = convertUserToJsonUser(user);
        String response = reimbursementService.getAllReimbursementsByEmployeeId(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertEquals(node.get("reimbursements").get(0).get("status").asText(),"Pending");
    }

    @Test
    @DisplayName("Get reimbursements by employeeId when user has none")
    public void testGetReimbursementsByEmployeeIdWhenUserHasNone(){
        User user  = new User();
        user.setId(new ObjectId("609ae0d7f1c1765449c0c283"));
        when(dao.getReimbursementsByEmployeeId(any())).thenReturn(new ArrayList<>());
        String jsonString = convertUserToJsonUser(user);
        String response = reimbursementService.getAllReimbursementsByEmployeeId(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    @Test
    @DisplayName("Create reimbursement")
    public void testCreateReimbursement(){
        when(dao.createReimbursement(any())).thenReturn(true);
        String jsonString = convertUserToJson(reimbursement);
        String response = reimbursementService.createReimbursement(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("success"));
    }

    @Test
    @DisplayName("Create reimbursements with invalid reimbursement format")
    public void testCreateReimbursementWithInvalidFormat(){
        Reimbursement testReimbursement = new Reimbursement();
        testReimbursement.setStatus("Pending");
        when(dao.createReimbursement(any())).thenReturn(false);
        String jsonString = convertUserToJson(testReimbursement);
        String response = reimbursementService.createReimbursement(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    @Test
    @DisplayName("Replace reimbursement")
    public void testReplaceReimbursement(){
        when(dao.replaceReimbursement(any())).thenReturn(true);
        String jsonString = convertUserToJson(reimbursement);
        String response = reimbursementService.replaceReimbursement(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("success"));
    }

    @Test
    @DisplayName("Replace reimbursements with invalid reimbursement format")
    public void testReplaceReimbursementWithInvalidFormat(){
        Reimbursement testReimbursement = new Reimbursement();
        testReimbursement.setStatus("Pending");
        when(dao.replaceReimbursement(any())).thenReturn(false);
        String jsonString = convertUserToJson(testReimbursement);
        String response = reimbursementService.replaceReimbursement(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    @Test
    @DisplayName("Delete reimbursement")
    public void testDeleteReimbursement(){
        when(dao.deleteReimbursement(any())).thenReturn(true);
        String jsonString = convertUserToJson(reimbursement);
        String response = reimbursementService.deleteReimbursement(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("success"));
    }

    @Test
    @DisplayName("Delete reimbursements with invalid reimbursement format")
    public void testDeleteReimbursementWithInvalidFormat(){
        Reimbursement testReimbursement = new Reimbursement();
        testReimbursement.setStatus("Pending");
        when(dao.deleteReimbursement(any())).thenReturn(false);
        String jsonString = convertUserToJson(testReimbursement);
        String response = reimbursementService.deleteReimbursement(jsonString);
        JsonNode node = convertJsonToJsonNode(response);
        Assertions.assertTrue(node.has("error"));
    }

    private String convertUserToJson(Reimbursement reimbursement){
        String jsonString;
        try{
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reimbursement);
            return jsonString;
        }catch(JsonProcessingException exception){
            throw new RuntimeException("Error converting user to json");
        }
    }

    private String convertUserToJsonUser(User user){
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

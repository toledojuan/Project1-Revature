package com.ers.service;

import com.ers.dao.Dao;
import com.ers.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Class in charge of communicating the controller with the dao
 */
public class UserService implements Service{


    private final Dao dao;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    /**
     * Constructor that initializes the dao
     * @param dao The dao that is in charge of database communications
     */
    public UserService(Dao dao){
        this.dao = dao;
        this.objectMapper = new ObjectMapper();
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Validates log in credentials
     * @param jsonString The string in json format with data
     * @return Json string response with validation results
     */
    public String loginUser(String jsonString){
        try{
            User user = this.objectMapper.readValue(jsonString,User.class);
            if(user.getUsername() != null && user.getPassword() != null){
                User responseUser = this.dao.getUserByUsername(user);
                if(responseUser != null){
                    if(responseUser.getPassword().equals(user.getPassword())){
                        logger.info("a user has been logged in.");
                        return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseUser);
                    }else{
                        logger.info("a user tried log in with invalid credential");
                        return  createErrorMessage("Invalid credentials");
                    }
                }else{
                    return createErrorMessage("Invalid credentials.");
                }
            }else{
                logger.info("a request had data with invalid format");
                return createErrorMessage("Invalid login format");
            }
        }catch(JsonProcessingException exception){
            logger.warn("error processing a request and transforming to JSON");
            return createErrorMessage("Invalid user format");
        }
    }

    /**
     * Finds users by userid
     * @param jsonString The user id request in JSON format
     * @return The response in JSON string with results
     */
    public String getUserByUserId(String jsonString) {

        try{
            User user = this.objectMapper.readValue(jsonString,User.class);
            user = this.dao.getUser(user);
            if(user != null){
                return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            }else{
                return createErrorMessage("User not found.");
            }

        }catch(JsonProcessingException exception){
            logger.warn("failed parsing a string to JSON");
            return createErrorMessage("Invalid user id");
        }
    }

    /**
     * Finds all users
     * @return list of users as JSON string
     */
    public String getAllUsers(){
        try{
            List<User> userList = this.dao.getAllUsers();
            if(userList.size() > 0){
                return this.objectMapper.writerWithDefaultPrettyPrinter().withRootName("users")
                        .writeValueAsString(userList);
            }else{
                logger.warn("no users where found in get all users");
                return createErrorMessage("noUsersFound");
            }
        }catch(JsonProcessingException exception){
            logger.warn("failed parsing a string to JSON");
            return createErrorMessage("Unable to retrieve users");
        }
    }

    /**
     * Create a new user
     * @param jsonString User data in json string
     * @return Response data in json string
     */
    public String createUser(String jsonString){
        try{
            User user = this.objectMapper.readValue(jsonString,User.class);
            if(this.dao.createUser(user)){
                return createSuccessMessage("User was created successfully");
            }else{
                return createErrorMessage("Invalid user format");
            }

        }catch(JsonProcessingException exception){
            logger.warn("failed parsing a string to JSON");
            return createErrorMessage("Invalid json user format");
        }
    }

    public String replaceUser(String jsonString) {
        try {
            User user = this.objectMapper.readValue(jsonString, User.class);
            System.out.println(user.getId());
            System.out.println(user.getFirstName());
            System.out.println(user.getLastName());
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getEmail());
            System.out.println(user.getType());
            if (this.dao.replaceUser(user)) {
                return createSuccessMessage("User was replaced");
            } else {
                logger.warn("dao returned validation error and couldn't create a user");
                return createErrorMessage("Invalid user format");
            }
        } catch (JsonProcessingException exception) {
            logger.warn("failed parsing a string to JSON");
            return createErrorMessage("Invalid json format");
        }
    }

    /**
     * Deletes a user
     * @param jsonString User info in JSON string
     * @return Response in JSON string format
     */
    public String deleteUser(String jsonString){
        try{
            User user = this.objectMapper.readValue(jsonString, User.class);
            if(this.dao.deleteUser(user)){
                return createSuccessMessage("User was deleted.");
            }else{
                logger.warn("dao failed deleting a request");
                return createErrorMessage("Invalid user format");
            }
        }catch(JsonProcessingException exception){
            logger.warn("failed parsing a string to JSON");
            return createErrorMessage("Invalid json format");
        }
    }

    /**
     * Formats the success response
     * @param message Information about the request
     * @return Success response formatted in JSON
     */
    private String createSuccessMessage(String message){
        return "{\"success\": \""+ message + "\"}";
    }

    /**
     * Formats the fail response
     * @param message Information about the request
     * @return Error response formatted in JSON
     */
    private String createErrorMessage(String message){
        return "{\"error\": \""+ message + "\"}";
    }
}

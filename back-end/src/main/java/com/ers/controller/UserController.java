package com.ers.controller;


import com.ers.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Class that is in charge of the communication between user servlet and user services
 */
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final Logger logger;


    /**
     * Constructor of the user controller
     * @param userService Service to use in the controller
     */
    public UserController(UserService userService){
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * deletes a user
     * @param request http request
     * @param response http response
     * @throws IOException error parsing json
     */
    public void deleteUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String[] urlParts = url.split("/");
        if(urlParts.length == 4){
            String userId = urlParts[3];
            String jsonString = "{\"id\":\"" + userId + "\"}";
            jsonString = this.userService.deleteUser(jsonString);
            int status = checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            this.writeToResponse(response,jsonString);
            logger.info("delete user controlled");
        }else{
            this.formatResponse(response,404);
            String jsonString = "{\"error\":\"Invalid url syntax.\"}";
            this.writeToResponse(response,jsonString);
            logger.warn("invalid url");
        }
    }

    /**
     * handles a PUT request
     * @param request http request
     * @param response http response
     * @throws IOException  error parsing JSON
     */
    public void putUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String[] urlParts = url.split("/");

        if(urlParts.length == 4){
            String userId = urlParts[3];
            String jsonString = this.createJsonFromParameters(request,userId);
            jsonString = this.userService.replaceUser(jsonString);
            int status =  checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            this.writeToResponse(response,jsonString);
            logger.info("put request controlled");
        }else{
            this.formatResponse(response,404);
            String jsonString = "{\"error\":\"Invalid url syntax.\"}";
            this.writeToResponse(response,jsonString);
            logger.info("invalid url");
        }
    }

    /**
     * handles POST requests
     * @param request http request
     * @param response https response
     * @throws IOException error parsing json
     */
    public void postUsers(HttpServletRequest request, HttpServletResponse response) throws IOException{

        String url = request.getRequestURI();
        String[] urlParts = url.split("/");
        if(urlParts.length == 4) {
            if (urlParts[3].equals("login")) {
                String jsonString = this.createJsonFromParameters(request,null);
                jsonString = this.userService.loginUser(jsonString);
                int status = checkSuccessOrError(jsonString);
                this.formatResponse(response,status);
                this.writeToResponse(response,jsonString);
                logger.info("post user controlled");
            }else{
                this.formatResponse(response,404);
                String jsonString = "{\"error\":\"Invalid url syntax.\"}";
                this.writeToResponse(response,jsonString);
                logger.warn("invalid url");
            }
        }else if(urlParts.length == 3){
            String jsonString = this.createJsonFromParameters(request,null);
            jsonString = this.userService.createUser(jsonString);
            int status =  checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            formatResponse(response,status);
            writeToResponse(response,jsonString);
            logger.info("post user controlled");
        }else{
            this.formatResponse(response,404);
            String jsonString = "{\"error\":\"Invalid url syntax.\"}";
            this.writeToResponse(response,jsonString);
            logger.warn("invalid url");
        }

    }

    /**
     * handles get requests
     * @param request http request
     * @param response http response
     * @throws IOException error parsing JSON
     */
    public void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String url = request.getRequestURI();
        String[] urlParts = url.split("/");
        if(urlParts.length == 3){
            formatResponse(response,200);
            String jsonString = this.userService.getAllUsers();
            int status =  checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            this.writeToResponse(response,jsonString);
            logger.info("get request controlled");
        }else if(urlParts.length == 4){
            String userId = urlParts[3];
            String jsonString = "{\"id\":\"" + userId + "\"}";
            jsonString = this.userService.getUserByUserId(jsonString);
            int status =  checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            this.writeToResponse(response,jsonString);
            logger.info("get request controlled");
        }else{
            this.formatResponse(response,404);
            String jsonString = "{\"error\":\"Invalid url syntax.\"}";
            this.writeToResponse(response,jsonString);
            logger.warn("invalid url");
        }
    }


    /**
     * creates a json from raw data of the request
     * @param request http request
     * @param id id to append from the url to the json
     * @return json string that represents object requested
     * @throws IOException error parsing json
     */
    private String createJsonFromParameters(HttpServletRequest request, String id) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder stringBuffer = new StringBuilder();
        String data ;
        while((data = bufferedReader.readLine())!= null){
            stringBuffer.append(data);
        }
        ObjectNode rootNode = (ObjectNode) this.objectMapper.readTree(stringBuffer.toString());
        if(id !=null){
            rootNode.put("id",id);
        }
        return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }

    /**
     * formats the response depending of whether the request was successful or not
     * @param jsonString response as json
     * @return status of the response
     * @throws JsonProcessingException error reading json
     */
    private int checkSuccessOrError(String jsonString) throws JsonProcessingException {
        JsonNode node = this.objectMapper.readTree(jsonString);
        if(node.has("error")){
            return 404;
        }else{
            return 200;
        }
    }

    /**
     * writes data to the response
     * @param response http response
     * @param jsonString data to be written
     * @throws IOException error writing data
     */
    private void writeToResponse(HttpServletResponse response, String jsonString) throws IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonString);
        printWriter.flush();
    }

    /**
     * formats the content of the response
     * @param response http response
     * @param status status of the response
     */
    private void formatResponse(HttpServletResponse response, int status){
        response.setHeader("Content-type","application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
    }


}

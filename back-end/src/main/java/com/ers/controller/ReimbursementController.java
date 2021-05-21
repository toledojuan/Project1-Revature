package com.ers.controller;

import com.ers.service.ReimbursementService;

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

public class ReimbursementController {
    private final ReimbursementService reimbursementService;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    /**
     * Constructor of the user controller
     * @param reimbursementService Service to use in the controller
     */
    public ReimbursementController(ReimbursementService reimbursementService){
        this.reimbursementService = reimbursementService;
        this.objectMapper = new ObjectMapper();
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * deletes a reimbursement
     * @param request http request
     * @param response http response
     * @throws IOException error parsing json
     */
    public void deleteReimbursements(HttpServletRequest request, HttpServletResponse response) throws IOException{

        String url = request.getRequestURI();
        String[] urlParts = url.split("/");
        if(urlParts.length == 4){
            String userId = urlParts[3];
            String jsonString = "{\"id\":\"" + userId + "\"}";
            jsonString = this.reimbursementService.deleteReimbursement(jsonString);
            int status = checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            this.writeToResponse(response,jsonString);
            logger.info("delete reimbursement controlled");
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
    public void putReimbursements(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String url = request.getRequestURI();
        String[] urlParts = url.split("/");
        if(urlParts.length == 4){
            String userId = urlParts[3];
            String jsonString = this.createJsonFromParameters(request,userId);
            jsonString = this.reimbursementService.replaceReimbursement(jsonString);
            int status =  checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            this.writeToResponse(response,jsonString);
            logger.info("put request controlled.");
        }else{
            this.formatResponse(response,404);
            String jsonString = "{\"error\":\"Invalid url syntax.\"}";
            this.writeToResponse(response,jsonString);
            logger.warn("invalid url");
        }
    }

    /**
     * handles POST requests
     * @param request http request
     * @param response https response
     * @throws IOException error parsing json
     */
    public void postReimbursements(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String url = request.getRequestURI();
        String[] urlParts = url.split("/");
        if(urlParts.length == 3){
            String jsonString = this.createJsonFromParameters(request,null);
            jsonString = this.reimbursementService.createReimbursement(jsonString);
            int status =  checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            formatResponse(response,status);
            writeToResponse(response,jsonString);
            logger.info("post request controlled");
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
    public void getReimbursements(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url  = request.getRequestURI();
        String[] urlParts = url.split("/");

        if(urlParts.length == 5){
            if(urlParts[3].equals("employeeId")){
                String userId = urlParts[4];
                String jsonString = "{\"id\":\"" + userId + "\"}";
                jsonString = this.reimbursementService.getAllReimbursementsByEmployeeId(jsonString);
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
        }else if(urlParts.length == 4){
            String userId = urlParts[3];
            String jsonString = "{\"id\":\"" + userId + "\"}";
            jsonString = this.reimbursementService.getReimbursementById(jsonString);
            int status =  checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            this.writeToResponse(response,jsonString);
            logger.info("get request controlled");
        }else if(urlParts.length == 3){
            String jsonString = this.reimbursementService.getAllReimbursements();
            int status = checkSuccessOrError(jsonString);
            this.formatResponse(response,status);
            this.writeToResponse(response,jsonString);
            logger.info("get request controlled");
        }else{
            this.formatResponse(response,404);
            String jsonString = "{\"error\":\"Invalid url syntax.\"}";
            this.writeToResponse(response,jsonString);
            logger.info("invalid url");
        }
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
        String data;
        while((data = bufferedReader.readLine())!= null){
            stringBuffer.append(data);
        }
        ObjectNode rootNode = (ObjectNode) this.objectMapper.readTree(stringBuffer.toString());
        if(id !=null){
            rootNode.put("id",id);
        }
        return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }
}

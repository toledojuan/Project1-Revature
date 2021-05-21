package com.ers.service;

import com.ers.dao.Dao;
import com.ers.model.Reimbursement;
import com.ers.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Class in charge of the communication between the controller and the dao
 */
public class ReimbursementService implements Service{

    private Dao dao;
    private ObjectMapper objectMapper;
    Logger logger;

    /**
     * Constructor of the reimbursement service
     * @param dao Dao object in charge of communicating with the database
     */
    public ReimbursementService(Dao dao){
        this.dao = dao;
        this.objectMapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.objectMapper.setDateFormat(dateFormat);
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Gets the requested reimbursement by using the id
     * @param jsonString Reimbursement information in json string format
     * @return Response in json string format
     */
    public String getReimbursementById(String jsonString) {

        try{
            Reimbursement reimbursement = this.objectMapper.readValue(jsonString,Reimbursement.class);
            reimbursement = this.dao.getReimbursement(reimbursement);
            if(reimbursement != null){
                return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reimbursement);
            }else{
                logger.warn("bad request received");
                return createErrorMessage("Reimbursement not found.");
            }

        }catch(JsonProcessingException exception){
            logger.warn("Invalid json format");
            return createErrorMessage("Invalid user id");
        }
    }

    /**
     * Get all reimbursements
     * @return Response in json format string
     */
    public String getAllReimbursements(){
        try{
            List<Reimbursement> reimbursementList = this.dao.getAllReimbursements();
            if(reimbursementList.size()>0){
                return this.objectMapper.writerWithDefaultPrettyPrinter().withRootName("reimbursements").writeValueAsString(reimbursementList);
            }else{
                return createErrorMessage("No reimbursements found.");
            }
        }catch(JsonProcessingException exception){
            return createErrorMessage("Unable to return reimbursements");
        }
    }

    /**
     * Gets all reimbursements that match a user id
     * @param jsonString user information in json string format
     * @return response in json string format
     */
    public String getAllReimbursementsByEmployeeId(String jsonString){
        try{
            User user = this.objectMapper.readValue(jsonString,User.class);
            List<Reimbursement> reimbursementList = this.dao.getReimbursementsByEmployeeId(user);
            if(reimbursementList.size()>0){
                return this.objectMapper.writerWithDefaultPrettyPrinter().withRootName("reimbursements").writeValueAsString(reimbursementList);
            }else{
                logger.warn("No reimbursements found by the requested id");
                return createErrorMessage("No reimbursements found for employee.");
            }
        }catch(JsonProcessingException exception){
            logger.warn("invalid json format from get all reimbursements by employee id");
            return createErrorMessage("Invalid user json format.");
        }
    }

    /**
     * Creates a new reimbursement
     * @param jsonString information about the new reimbursement
     * @return response in json string format
     */
    public String createReimbursement(String jsonString){
        try{
            Reimbursement reimbursement = this.objectMapper.readValue(jsonString,Reimbursement.class);
            if(reimbursement.getCost() < 1){
                return createErrorMessage("Invalid amount");
            }
            if(this.dao.createReimbursement(reimbursement)){
                return createSuccessMessage("Reimbursement was successfully created.");
            }else{
                logger.warn("Validation failed in create reimbursement");
                return createErrorMessage("Invalid reimbursement format");
            }
        }catch(JsonProcessingException exception){
            logger.warn("Invalid json fomrat in create reimbursement");
            return createErrorMessage("Invalid json format");
        }
    }

    /**
     * Replaces an existing reimbursement
     * @param jsonString information of the reimbursement that is to be replaced
     * @return response in json string format
     */
    public String replaceReimbursement(String jsonString){
        try{
            Reimbursement reimbursement = this.objectMapper.readValue(jsonString,Reimbursement.class);
            if(this.dao.replaceReimbursement(reimbursement)){
                return createSuccessMessage("Reimbursement was successfully replaced.");
            }else{
                logger.warn("failed validating the replace request");
                return createErrorMessage("Invalid reimbursement format");
            }
        }catch(JsonProcessingException exception){
            logger.warn("invalid json format in replace reimbursement");
            return createErrorMessage("Invalid json format");
        }
    }

    /**
     * Deletes a reimbursement
     * @param jsonString information about the reimbursement to be deleted
     * @return response in json string format
     */
    public String deleteReimbursement(String jsonString){
        try{
            Reimbursement reimbursement = this.objectMapper.readValue(jsonString,Reimbursement.class);
            if(this.dao.deleteReimbursement(reimbursement)){
                return createSuccessMessage("Reimbursement was successfully deleted.");
            }else{
                logger.warn("failed to validate reimbursement in delete reimbursement");
                return createErrorMessage("Invalid reimbursement format");
            }
        }catch(JsonProcessingException exception){
            logger.warn("invalid json format in delete reimbursement");
            return createErrorMessage("Invalid json format");
        }
    }

    /**
     * Function in charge of formatting the success response message
     * @param message information about the request that succeeded
     * @return response in json string format
     */
    private String createSuccessMessage(String message){
        return "{\"success\": \""+ message + "\"}";
    }
    /**
     * Function in charge of formatting the error response message
     * @param message information about the request that succeeded
     * @return response in json string format
     */
    private String createErrorMessage(String message){
        return "{\"error\": \""+ message + "\"}";
    }
}

package com.ers.service;

import com.ers.model.Reimbursement;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Class that is in charge of the deserialization of a reimbursement object
 */
public class ReimbursementDeserializerService extends JsonDeserializer<Reimbursement> {
    /**
     * Deserialize function for a reimbursement object
     * @param jsonParser JsonParser that contains information of the object to be deserialized
     * @param context Context from the JsonDeserializer class
     * @return A reimbursement object created form a json string
     * @throws IOException Error during the parsing and converting of json strings
     */
    @Override
    public Reimbursement deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        Reimbursement reimbursement = new Reimbursement();
        if(node.has("id") && !node.get("id").asText().equals("null")){
            try{
                reimbursement.setId(new ObjectId(node.get("id").asText()));
            }catch(IllegalArgumentException exception){
                //System.out.println("Invalid hex string");
            }
        }
        if(node.has("date") && !node.get("date").asText().equals("null")) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                reimbursement.setDate(dateFormat.parse(node.get("date").asText()));
            } catch (ParseException exception) {
                reimbursement.setDate(new Date());
            }
        }else{
            reimbursement.setDate(new Date());
        }
        if(node.has("cost") && !node.get("cost").asText().equals("null")) {
            reimbursement.setCost(node.get("cost").asDouble());
        }
        if(node.has("status") && !node.get("status").asText().equals("null")) {
            reimbursement.setStatus(node.get("status").asText());
        }
        if(node.has("description") && !node.get("description").asText().equals("null")) {
            reimbursement.setDescription(node.get("description").asText());
        }
        if(node.has("managerId") && !node.get("managerId").asText().equals("null")) {
            try{
                reimbursement.setManagerId(new ObjectId(node.get("managerId").asText()));
            }catch(IllegalArgumentException exception){
                //System.out.println("Invalid hex string");
            }
        }
        if(node.has("employeeId") && !node.get("employeeId").asText().equals("null")) {
            try{
                reimbursement.setEmployeeId(new ObjectId(node.get("employeeId").asText()));
            }catch(IllegalArgumentException exception){
                //System.out.println("Invalid hex string");
            }
        }
        if(node.has("resolution") && !node.get("resolution").asText().equals("null")) {
            reimbursement.setResolution(node.get("resolution").asText());
        }

        return reimbursement;
    }
}

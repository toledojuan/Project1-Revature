package com.ers.service;

import com.ers.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.bson.types.ObjectId;

import java.io.IOException;


/**
 * Class in charge of deserializing a User object
 */
public class UserDeserializerService extends JsonDeserializer<User> {

    /**
     * Deserializes each property given from a user Object
     * @param jsonParser The JsonParser that contains object information
     * @param context Context of the JsonDeserializer class
     * @return A user object created from json string
     * @throws IOException Error reading from json string
     */
    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException{
        JsonNode node = jsonParser.readValueAsTree();
        User responseUser = new User();
        if(node.has("id") && !node.get("id").asText().equals("null")){
            try{
                responseUser.setId(new ObjectId(node.get("id").asText()));
            }catch(IllegalArgumentException exception){
                //System.out.println("Invalid hex string");
            }
        }
        if(node.has("firstName") && !node.get("firstName").asText().equals("null")) {
            responseUser.setFirstName(node.get("firstName").asText());
        }
        if(node.has("lastName") && !node.get("lastName").asText().equals("null")) {
            responseUser.setLastName(node.get("lastName").asText());
        }
        if(node.has("username") && !node.get("username").asText().equals("null")) {
            responseUser.setUsername(node.get("username").asText());
        }
        if(node.has("password") && !node.get("password").asText().equals("null")) {
            responseUser.setPassword(node.get("password").asText());
        }
        if(node.has("email") && !node.get("email").asText().equals("null")) {
            responseUser.setEmail(node.get("email").asText());
        }
        if(node.has("type") && !node.get("type").asText().equals("null")) {
            responseUser.setType(node.get("type").asText());
        }
        return responseUser;
    }
}

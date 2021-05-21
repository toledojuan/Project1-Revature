package com.ers.model;

import com.ers.service.UserDeserializerService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;


/**
 * Class that represents a User object
 */
@JsonDeserialize(using = UserDeserializerService.class)
public class User {

    private ObjectId id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String type;

    /**
     * Constructor of the User class
     */
    public User() {
    }

    /**
     * Gets the id from the user
     * @return return the id as a ObjectId object
     */
    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId getId() {
        return id;
    }

    /**
     * set the object id
     * @param id of the user
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * gets the first name of the user
     * @return first name as string
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the first name of the user
     * @param firstName as a string
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the last name of the user
     * @return the last name as string
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the last name of the user
     * @param lastName in string format
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets the username of the user
     * @return username as string
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username of the user
     * @param username as string
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the password of the user
     * @return password as string
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password of the user
     * @param password as string
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get the email of the user
     * @return email as string
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email of the user
     * @param email as string
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets the type of user
     * @return the type as string
     */
    public String getType() {
        return type;
    }

    /**
     * sets the type of the user
     * @param type as string
     */
    public void setType(String type) {
        this.type = type;
    }

}

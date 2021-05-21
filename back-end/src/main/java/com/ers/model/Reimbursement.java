package com.ers.model;

import com.ers.service.ReimbursementDeserializerService;
import com.ers.service.UserDeserializerService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Class that represents a reimbursement object
 */
@JsonDeserialize(using = ReimbursementDeserializerService.class)
public class Reimbursement {
    private ObjectId id;
    private Date date;
    private double cost;
    private String status;
    private String description;
    private ObjectId managerId;
    private ObjectId employeeId;
    private String resolution;

    /**
     * Constructor of the reimbursement class
     */
    public Reimbursement() {
    }

    /**
     * gets the id of the reimbursement
     * @return id as ObjectId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId getId() {
        return id;
    }

    /**
     * sets the id of the reimbursement
     * @param id as ObjectId
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * gets the date of the reimbursement
     * @return date as Data object
     */
    public Date getDate() {
        return date;
    }

    /**
     * sets the date of the reimbursement
     * @param date as Date object
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * gets the cost of the reimbursement
     * @return the cost as double
     */
    public double getCost() {
        return cost;
    }

    /**
     * sets the cost of the reimbursement
     * @param cost as double
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * gets the status of the reimbursement
     * @return status as string
     */
    public String getStatus() {
        return status;
    }

    /**
     * sets the status of the reimbursement
     * @param status as string
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * gets the description of the reimbursement
     * @return description as string
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description of the reimbursement
     * @param description as string
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets the manager id of the reimbursement
     * @return manager id as OjbectId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId getManagerId() {
        return managerId;
    }

    /**
     * sets the manager id of the reimbursement
     * @param managerId as ObjectId
     */
    public void setManagerId(ObjectId managerId) {
        this.managerId = managerId;
    }
    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId getEmployeeId() {
        return employeeId;
    }

    /**
     * sets the employee id of the reimbursement
     * @param employeeId as ObjectId
     */
    public void setEmployeeId(ObjectId employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * gets the resolution of the reimbursement
     * @return resolution as string
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * sets the resolution of the reimbursement
     * @param resolution as string
     */
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}

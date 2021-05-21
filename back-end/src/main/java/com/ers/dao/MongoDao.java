package com.ers.dao;

import com.ers.model.Reimbursement;
import com.ers.model.User;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.log4j.Logger;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that is in charge of communicating with a mongo database
 */
public class MongoDao implements Dao{


    private final MongoCollection<User> userMongoCollection;
    private final MongoCollection<Reimbursement> reimbursementMongoCollection;
    private final Logger logger;

    /**
     * Constructor of the mongo dao class
     * Initializes the client, database, collections and logger
     */
    public MongoDao() {
        MongoClient mongoClient = MongoClients.create();
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongoDatabase = mongoClient.getDatabase("ers").withCodecRegistry(codecRegistry);
        this.userMongoCollection = mongoDatabase.getCollection("user",User.class);
        this.reimbursementMongoCollection = mongoDatabase.getCollection("reimbursement", Reimbursement.class);
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * gets all user from the mongo database
     * @return list of User objects
     */
    @Override
    public List<User> getAllUsers() {
        try{
            List<User> userList = new ArrayList<>();
            userMongoCollection.find().forEach(userList::add);
            return userList;
        }catch(Exception exception){
            logger.error("Failed getting users from the database");
            throw new RuntimeException("Failed getting all users from database.");
        }
    }

    /**
     * gets all reimbursements from the mongo database
     * @return list of Reimbursement objects
     */
    @Override
    public List<Reimbursement> getAllReimbursements() {
        try{
            List<Reimbursement> reimbursementList = new ArrayList<>();
            reimbursementMongoCollection.find().sort(Sorts.descending("date")).forEach(reimbursementList::add);
            return reimbursementList;
        }catch(Exception exception){
            logger.error("failed getting all reimbursements");
            throw new RuntimeException("Failed getting all reimbursements.");
        }
    }

    /**
     * creates a user in the mongo database
     * @param user User object to be created
     * @return boolean response of success or fail
     */
    @Override
    public boolean createUser(User user) {
        try{
            userMongoCollection.insertOne(user);
            return true;
        }catch(Exception exception){
            logger.warn("failed creating a user");
            return false;
        }
    }

    /**
     * replaces a user from the mongo database
     * @param user User object to be replaced
     * @return boolean response of success or fail
     */
    @Override
    public boolean replaceUser(User user) {
        try{
            Bson filter = Filters.eq("_id",user.getId());
            UpdateResult updateResult = userMongoCollection.replaceOne(filter,user);
            return updateResult.getModifiedCount() > 0;
        }catch(Exception exception){
            logger.warn("failed replacing user");
            return false;
        }
    }

    /**
     * deletes a user from the mongo database
     * @param user User object to be deleted
     * @return boolean response of success or fail
     */
    @Override
    public boolean deleteUser(User user) {
        try{
            Bson filter = Filters.eq("employeeId",user.getId());
            reimbursementMongoCollection.deleteMany(filter);
            Bson userFilter = Filters.eq("_id", user.getId());
            DeleteResult deleteResult= userMongoCollection.deleteOne(userFilter);
            return deleteResult.getDeletedCount() > 0;

        }catch(Exception exception){
            logger.warn("failed deleting user");
            return false;
        }
    }

    /**
     * gets user from mongo database
     * @param user User object to find
     * @return found User
     */
    @Override
    public User getUser(User user) {
        try{
            Bson filter = Filters.eq("_id", user.getId());
            return userMongoCollection.find(filter).first();

        }catch(Exception exception){
            logger.warn("failed getting user");
            return null;
        }
    }

    /**
     * gets a reimbursement form the mongo database
     * @param reimbursement Reimbursement object to find
     * @return Reimbursement object found
     */
    @Override
    public Reimbursement getReimbursement(Reimbursement reimbursement) {
        try{
            Bson filter = Filters.eq("_id",reimbursement.getId());
            return reimbursementMongoCollection.find(filter).first();
        }catch(Exception exception){
            logger.warn("failed getting reimbursement");
            return null;
        }
    }

    /**
     * gets reimbursement of a specific user
     * @param user User object that has the reimbursements
     * @return list of Reimbursements
     */
    @Override
    public List<Reimbursement> getReimbursementsByEmployeeId(User user) {
        try{
            Bson filter = Filters.eq("employeeId",user.getId());

            List<Reimbursement> reimbursementList = new ArrayList<>();
            reimbursementMongoCollection.find(filter).sort(Sorts.descending("date")).forEach(reimbursementList::add);
            return reimbursementList;
        }catch(Exception exception){
            logger.warn("failed getting reimbursements by employee id");
            return null;
        }
    }

    /**
     * creates a new reimbursement in the mongo database
     * @param reimbursement Reimbursement object to be created
     * @return boolean response of success or fail
     */
    @Override
    public boolean createReimbursement(Reimbursement reimbursement) {
        try{
            reimbursementMongoCollection.insertOne(reimbursement);
            return true;
        }catch(Exception exception){
            logger.warn("failed creating a new reimbursement");
            return false;
        }
    }

    /**
     * replaces a reimbursement in the mongo database
     * @param reimbursement Reimbursement object to be replaced
     * @return boolean response of success or fail
     */
    @Override
    public boolean replaceReimbursement(Reimbursement reimbursement) {
        try{
            Bson filter = Filters.eq("_id", reimbursement.getId());
            UpdateResult updateResult = reimbursementMongoCollection.replaceOne(filter, reimbursement);
            return updateResult.getModifiedCount() > 0;
        }catch(Exception exception){
            logger.warn("failed replacing a reimbursement");
            return false;
        }
    }

    /**
     * deletes a reimbursement from the mongo database
     * @param reimbursement Reimbursement object to be deleted
     * @return boolean response of success or fail
     */
    @Override
    public boolean deleteReimbursement(Reimbursement reimbursement) {
        try{
            Bson filter = Filters.eq("_id", reimbursement.getId());
            DeleteResult deleteResult = reimbursementMongoCollection.deleteOne(filter);
            return deleteResult.getDeletedCount() > 0;
        }catch(Exception exception){
            logger.warn("failed deleting a reimbursement");
            return false;
        }
    }

    /**
     * gets user by username
     * @param user User object to find
     * @return User object found
     */
    @Override
    public User getUserByUsername(User user) {
        try{
            Bson filter = Filters.eq("username", user.getUsername());
            return userMongoCollection.find(filter).first();
        }catch(Exception exception){
            logger.warn("failed getting user by username");
            return null;
        }
    }
}

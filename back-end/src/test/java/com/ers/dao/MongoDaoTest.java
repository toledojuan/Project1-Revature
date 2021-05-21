package com.ers.dao;

import com.ers.model.Reimbursement;
import com.ers.model.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class MongoDaoTest {


    private static Dao dao;

    @BeforeAll
    public static void setup(){
        dao = new MongoDao();
    }

    @Test
    @DisplayName("Test user CRUD operations")
    public void testUserCRUD(){
        User customUser = new User();
        customUser.setFirstName("testFirstName2");
        customUser.setLastName("testLastName2");
        customUser.setUsername("testUsername2");
        customUser.setPassword("testPassword2");
        customUser.setEmail("test2@gmail.com");
        customUser.setType("Employee");
        dao.createUser(customUser);
        customUser = dao.getUserByUsername(customUser);
        Assertions.assertNotEquals(customUser,null);

        customUser.setFirstName("testFirstName3");
        dao.replaceUser(customUser);
        customUser = dao.getUser(customUser);
        Assertions.assertEquals(customUser.getFirstName(),"testFirstName3");

        List<User> userList = dao.getAllUsers();
        Assertions.assertNotEquals(0,userList.size());

        dao.deleteUser(customUser);
        customUser = dao.getUserByUsername(customUser);
        Assertions.assertEquals(customUser,null);
    }

    @Test
    @DisplayName("Test reimbursement CRUD operations")
    public void testReimbursementCRUD(){
        User customUser = new User();
        customUser.setId(new ObjectId("609ae0d7f1c1765449c0c283"));

        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setEmployeeId(new ObjectId("609ae0d7f1c1765449c0c283"));
        reimbursement.setDescription("Test description.");
        reimbursement.setStatus("Pending");
        reimbursement.setCost(200);
        reimbursement.setDate(new Date());
        dao.createReimbursement(reimbursement);
        List<Reimbursement> reimbursementList = dao.getReimbursementsByEmployeeId(customUser);
        Assertions.assertNotEquals(0,reimbursementList.size());

        reimbursement = reimbursementList.get(0);
        reimbursement.setStatus("Resolved");
        reimbursement.setManagerId(new ObjectId("609ae0d7f1c1765449c0c283"));
        reimbursement.setResolution("Denied");
        dao.replaceReimbursement(reimbursement);
        reimbursement = dao.getReimbursement(reimbursement);
        Assertions.assertEquals(reimbursement.getStatus(), "Resolved");

        reimbursementList = dao.getAllReimbursements();
        Assertions.assertNotEquals(0,reimbursementList.size());

        dao.deleteReimbursement(reimbursement);
        reimbursementList = dao.getReimbursementsByEmployeeId(customUser);
        Assertions.assertEquals(0,reimbursementList.size());
    }





}

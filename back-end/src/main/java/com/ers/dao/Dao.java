package com.ers.dao;

import com.ers.model.Reimbursement;
import com.ers.model.User;

import java.util.List;

public interface Dao {

    public List<User> getAllUsers();

    public boolean createUser(User user);

    public boolean replaceUser(User user);

    public boolean deleteUser(User user);

    public User getUser(User user);

    public User getUserByUsername(User user);

    public Reimbursement getReimbursement(Reimbursement reimbursement);

    public List<Reimbursement> getAllReimbursements();

    public List<Reimbursement> getReimbursementsByEmployeeId(User user);

    public boolean createReimbursement(Reimbursement reimbursement);

    public boolean replaceReimbursement(Reimbursement reimbursement);

    public boolean deleteReimbursement(Reimbursement reimbursement);

}

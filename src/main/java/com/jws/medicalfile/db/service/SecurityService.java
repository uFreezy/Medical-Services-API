package com.jws.medicalfile.db.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
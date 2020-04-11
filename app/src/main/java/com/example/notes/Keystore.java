package com.example.notes;
import androidx.annotation.Nullable;

interface Keystore {
    boolean userExists(String userName);
    @Nullable
    String isAdmin(String userName, String pin);
    void newUser(String userName, String pin);
}
package com.example.recipegenie;
// IM/2021/009 - Y.A.D.S.C.BASNAYAKE

public class UserModel {

        private String userName;
        private String userEmail;

        // Default constructor (required for Firebase)
        public UserModel() {}

        // Constructor
        public UserModel(String userName, String userEmail) {
            this.userName = userName;
            this.userEmail = userEmail;
        }

        // getter and setter methods
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }
    }



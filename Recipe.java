package com.example.getting_started;

// IM/2021/009 - recipe class to get and set recipe data
public class Recipe {
    private String name;
    private String type;
    private String servingInfo;
    private String time;
    private double rating;
    private String imageURL;
    private String recipeID;

    // No-argument constructor required for Firebase
    public Recipe() {
        // Initialize fields if necessary
    }

    // Constructor
    public Recipe(String name, String type, String servingInfo,String time, double rating, String imageURL) {
        this.name = name;
        this.type = type;
        this.servingInfo = servingInfo;
        this.time = time;
        this.rating = rating;
        this.imageURL = imageURL;
        this.recipeID = recipeID;
    }


        // Getters
        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getServingInfo() {
            return servingInfo;
        }

        public String getTime() {
            return time;
        }

        public double getRating() {
            return rating;
        }

        public String getImageUrl() {
            return imageURL;
        }

        public String getRecipeID() {
        return recipeID;
        }



    // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setServingInfo(String servingInfo) {
            this.servingInfo = servingInfo;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public void setImageUrl(String imageURL) {
            this.imageURL = imageURL;
        }
    }



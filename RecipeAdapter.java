package com.example.getting_started;


import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

/* public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    @SuppressLint("RestrictedApi")
    private Context context;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context context,List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeType.setText(recipe.getType());
        holder.recipeServingInfo.setText(recipe.getServingInfo());
        holder.recipeRating.setText(recipe.getRating());
        holder.recipeImage.setImageResource(recipe.getImageResource());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeType, recipeServingInfo, recipeRating;
        ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeType = itemView.findViewById(R.id.recipe_type);
            recipeServingInfo = itemView.findViewById(R.id.recipe_serving_info);
            recipeRating = itemView.findViewById(R.id.recipe_rating);
            recipeImage = itemView.findViewById(R.id.recipe_image);
        }
    }
}  */



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    /*private List<Recipe> recipeList;
    private boolean isMyRecipes;

    public RecipeAdapter(Context context, List<Recipe> recipeList, boolean isMyRecipes) {
        this.recipeList = recipeList;
        this.isMyRecipes = isMyRecipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.nameCard.setText(recipe.getName());
        holder.typeCard.setText(recipe.getType());
        holder.servingInfoCard.setText(recipe.getServingInfo());
        holder.timeCard.setText(recipe.getTime());
        holder.ratingCard.setText(String.valueOf(recipe.getRating())); // Converts the double to String


        // Load image using Picasso
        Picasso.get().load(recipe.getImageUrl()).into(holder.imageViewCard);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView nameCard, typeCard, servingInfoCard, timeCard, ratingCard;
        ImageView imageViewCard;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCard = itemView.findViewById(R.id.recipe_name);
            typeCard = itemView.findViewById(R.id.recipe_type);
            servingInfoCard = itemView.findViewById(R.id.recipe_serving_info);
            timeCard = itemView.findViewById(R.id.cook_time);
            ratingCard = itemView.findViewById(R.id.recipe_rating);
            imageViewCard = itemView.findViewById(R.id.recipe_image);
        }
    }*/


        // Declare constants here
        public static final int VIEW_TYPE_SAVED_RECIPE = 0;
        public static final int VIEW_TYPE_MY_RECIPE = 1;

        private Context context;
        private List<Recipe> recipeList;
        private int viewType;
        Button no_button_delete,yes_button_delete;
        Dialog dialog_recipe_delete;
        String recipeID;


        public RecipeAdapter(Context context, List<Recipe> recipeList, int viewType) {
            this.recipeList = recipeList;
            this.viewType = viewType;
            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            return viewType;  // Return the view type based on what was passed in the constructor
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if (viewType == VIEW_TYPE_SAVED_RECIPE) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
                return new SavedRecipeViewHolder(view);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_your_recipes, parent, false);
                return new MyRecipeViewHolder(view);
            }
        }

    /*@Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {

    }*/

    //@Override
        @SuppressLint("UseCompatLoadingForDrawables")
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Recipe recipe = recipeList.get(position);

            if (holder.getItemViewType() == VIEW_TYPE_SAVED_RECIPE) {
                SavedRecipeViewHolder savedHolder = (SavedRecipeViewHolder) holder;
                savedHolder.nameCard.setText(recipe.getName());
                savedHolder.typeCard.setText(recipe.getType());
                savedHolder.servingInfoCard.setText(recipe.getServingInfo());
                savedHolder.timeCard.setText(recipe.getTime());
                savedHolder.ratingCard.setText(String.valueOf(recipe.getRating()));

                // Load image using Picasso
                Picasso.get().load(recipe.getImageUrl()).into(savedHolder.imageViewCard);

            } else {
                MyRecipeViewHolder myHolder = (MyRecipeViewHolder) holder;
                myHolder.nameCard.setText(recipe.getName());
                myHolder.typeCard.setText(recipe.getType());
                myHolder.servingInfoCard.setText(recipe.getServingInfo());
                myHolder.timeCard.setText(recipe.getTime());

                // Load image using Picasso
                Picasso.get().load(recipe.getImageUrl()).into(myHolder.imageViewCard);

                // Implement your logic for Edit and Delete buttons
                myHolder.imgEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(context, Profile.class);
                    context.startActivity(intent);
                    // Handle edit action
                });

                myHolder.imgDelete.setOnClickListener(v -> {
                    // Handle delete action
                    //Log.d("RecipeAdapter", "Delete image clicked"); // Debug line
                    //Toast.makeText(context, "Delete image clicked", Toast.LENGTH_SHORT).show();
                    dialog_recipe_delete = new Dialog(context);
                    dialog_recipe_delete.setContentView(R.layout.delete_recipe); // Use the new layout file
                    dialog_recipe_delete.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog_recipe_delete.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialogbox_bg)); // Use the appropriate background
                    dialog_recipe_delete.setCancelable(false);

                    //initialize the yes, No buttons in delete account dialog box
                    yes_button_delete = dialog_recipe_delete.findViewById(R.id.yes_button_delete);
                    no_button_delete = dialog_recipe_delete.findViewById(R.id.no_button_delete);

                    no_button_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_recipe_delete.dismiss(); //close delete acc dialog
                        }
                    });

                    yes_button_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recipeID = recipe.getRecipeID();

                            DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("recipes").child(recipeID);

                            // Delete the recipe from Firebase
                            recipeRef.removeValue().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Recipe deleted successfully!", Toast.LENGTH_SHORT).show();

                                    // Remove from local list and notify adapter
                                    recipeList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, recipeList.size());

                                    dialog_recipe_delete.dismiss(); // Close the dialog
                                } else {
                                    Toast.makeText(context, "Recipe deletion failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }




                    });


                });


            }

        }

        @Override
        public int getItemCount() {
            return recipeList.size();
        }

        // ViewHolder for Saved Recipes
        public static class SavedRecipeViewHolder extends RecyclerView.ViewHolder {
            TextView nameCard, typeCard, servingInfoCard, timeCard, ratingCard;
            ImageView imageViewCard;

            public SavedRecipeViewHolder(@NonNull View itemView) {
                super(itemView);
                nameCard = itemView.findViewById(R.id.recipe_name);
                typeCard = itemView.findViewById(R.id.recipe_type);
                servingInfoCard = itemView.findViewById(R.id.recipe_serving_info);
                timeCard = itemView.findViewById(R.id.cook_time);
                ratingCard = itemView.findViewById(R.id.recipe_rating);
                imageViewCard = itemView.findViewById(R.id.recipe_image);
            }
        }

        // ViewHolder for My Recipes
        public static class MyRecipeViewHolder extends RecyclerView.ViewHolder {
            TextView nameCard, typeCard, servingInfoCard, timeCard;
            ImageView imageViewCard, imgEdit, imgDelete;


            public MyRecipeViewHolder(@NonNull View itemView) {
                super(itemView);
                nameCard = itemView.findViewById(R.id.recipe_name);
                typeCard = itemView.findViewById(R.id.recipe_type);
                servingInfoCard = itemView.findViewById(R.id.recipe_serving_info);
                timeCard = itemView.findViewById(R.id.cook_time);
                imageViewCard = itemView.findViewById(R.id.recipe_image);
                imgEdit = itemView.findViewById(R.id.img_edit);
                imgDelete = itemView.findViewById(R.id.img_delete);
            }
        }
    }







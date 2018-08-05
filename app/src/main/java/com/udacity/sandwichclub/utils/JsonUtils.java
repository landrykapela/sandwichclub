package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";
    private static final String NAME_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String AKA_KEY = "alsoKnownAs";
    private static final String ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich mySandwich = new Sandwich();
        try {
            JSONObject joSandwichDetails = new JSONObject(json);

            //get sandwich name details
            JSONObject sandwitchName = joSandwichDetails.getJSONObject(NAME_KEY);
            String sandwichManinName = sandwitchName.getString(MAIN_NAME_KEY);

            //get sandwich aliases
            JSONArray sandwichAliases= sandwitchName.getJSONArray(AKA_KEY);

            List<String> alias = new ArrayList<>();
            if(sandwichAliases != null){
                for(int i=0; i < sandwichAliases.length(); i++){
                    alias.add(sandwichAliases.get(i).toString());
                }

            }


            //get sandwich origin
           // String sandwichOrigin = joSandwichDetails.getString(ORIGIN_KEY);

            String sandwichOrigin = (joSandwichDetails.getString(ORIGIN_KEY) == null || TextUtils.isEmpty(joSandwichDetails.getString(ORIGIN_KEY))) ? "Not available" : joSandwichDetails.getString(ORIGIN_KEY);

            //get sandwich Description
            String sandwichDescription = joSandwichDetails.getString(DESCRIPTION_KEY);

            //get sandwich image
            String sandwichImage = joSandwichDetails.getString(IMAGE_KEY);

            //get sandwich ingredients
            List<String> ingredients = new ArrayList<>();
            JSONArray sandwichIngredients = joSandwichDetails.getJSONArray(INGREDIENTS_KEY);
            if(sandwichIngredients != null){
                for(int i=0; i < sandwichIngredients.length(); i++){
                    ingredients.add(sandwichIngredients.get(i).toString());
                }
            }


            //set sandwich data
            mySandwich.setMainName(sandwichManinName);
            mySandwich.setAlsoKnownAs(alias);
            mySandwich.setPlaceOfOrigin(sandwichOrigin);
            mySandwich.setDescription(sandwichDescription);
            mySandwich.setImage(sandwichImage);
            mySandwich.setIngredients(ingredients);

        }
        catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG, "Error in JSON Object "+e.getMessage());
        }

        return mySandwich;
    }
}

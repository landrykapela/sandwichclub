package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private TextView mAlsoKnownAsTv;
    private TextView mDescriptionTv;
    private TextView mIngredientsTv;
    private TextView mPlaceOfOriginTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);

        mAlsoKnownAsTv   = findViewById(R.id.also_known_tv);
        mDescriptionTv   = findViewById(R.id.description_tv);
        mIngredientsTv   = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTv = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        else {
            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTv.setText(sandwich.getDescription());
        StringBuilder aka = new StringBuilder();
        if(sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                if (i < sandwich.getAlsoKnownAs().size() - 1) {
                    aka.append(sandwich.getAlsoKnownAs().get(i)).append(", ");
                } else aka.append(sandwich.getAlsoKnownAs().get(i));
            }
        }
        else aka = new StringBuilder("Not available");
        mAlsoKnownAsTv.setText(aka.toString());
        StringBuilder ingredients = new StringBuilder();
        if(sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                if (i < sandwich.getIngredients().size() - 1) {
                    ingredients.append((i +1))
                    .append(". ")
                    .append(sandwich.getIngredients().get(i))
                    .append("\n");
                }
                else {
                    ingredients.append((i +1))
                        .append(". ")
                        .append(sandwich.getIngredients().get(i));
                }
            }
        }
        else ingredients = new StringBuilder("Not Available");
        mIngredientsTv.setText(ingredients.toString());
    }
}

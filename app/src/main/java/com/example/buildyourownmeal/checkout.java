package com.example.buildyourownmeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class checkout extends AppCompatActivity {

    private RadioButton priority, standard, scheduledDate;
    private TextView sideActName, itemCount, mealNameSummary, mealNameSubtotal, addOn, mealPriceSum, mealPriceSubtotal, mealPriceTotal, payment, addItemBtn;
    private Button orderBtn;
    private ImageView backBtn;
    private LinearLayout paymentMethodBtn, orderCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        //SET ID
        sideActName = findViewById(R.id.sideFragName);
        backBtn = findViewById(R.id.backBtn);
        priority = findViewById(R.id.priorityFee);
        standard = findViewById(R.id.standardFee);
        scheduledDate = findViewById(R.id.scheduledDate);
        itemCount = findViewById(R.id.quantityValue);
        mealNameSummary = findViewById(R.id.mealName);
        addOn = findViewById(R.id.addOn);
        mealPriceSum = findViewById(R.id.price);
        mealPriceSubtotal = findViewById(R.id.subtotalPrice);
        mealPriceTotal = findViewById(R.id.totalPrice);
        payment = findViewById(R.id.paymentMethod);
        orderBtn = findViewById(R.id.orderBtnCart);
        orderCon = findViewById(R.id.orderCon);
        addItemBtn = findViewById(R.id.addItemBtn);

        //SET TOOLBAR NAME
        sideActName.setText(getString(R.string.smallCheckOut));

        //BACK BUTTON
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //RADIO BUTTON
        standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standard.setChecked(true);
                priority.setChecked(false);
                scheduledDate.setChecked(false);
            }
        });

        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standard.setChecked(false);
                priority.setChecked(true);
                scheduledDate.setChecked(false);
            }
        });

        scheduledDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standard.setChecked(false);
                priority.setChecked(false);
                scheduledDate.setChecked(true);
            }
        });

        //USER SESSION
        SharedPreferences userSession = getSharedPreferences("userSession", MODE_PRIVATE);
        boolean isUserLoggedIn = userSession.getBoolean("isUserLoggedIn", false);

        //SHARED PREFERENCE FOR MENU ITEM
        SharedPreferences menuItem = getSharedPreferences("craftedMeal", MODE_PRIVATE);
        boolean menuSession = menuItem.getBoolean("menuSession", false);
        String selectedItems = menuItem.getString("selectedItems", "No selected items");
        int dbItemCount = menuItem.getInt("itemCount", 0);
        String mealName = menuItem.getString("mealName", "No meal name");
        float totalPrice = menuItem.getFloat("totalPrice", 0);

        //SHARED PREFERENCE FOR CART
        SharedPreferences orderProcess = getSharedPreferences("orderProcess", MODE_PRIVATE);
        SharedPreferences.Editor editor = orderProcess.edit();

        if (isUserLoggedIn) {
            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (priority.isChecked() || standard.isChecked()) {

                        if (priority.isChecked()) {
                            editor.putString("pickUpOption", "priority");
                        } else if (standard.isChecked()) {
                            editor.putString("pickUpOption", "standard");
                        }
                        String getPayment = payment.getText().toString();
                        editor.putString("paymentMethod", getPayment);
                        editor.putBoolean("ifUserHadOrdered", true);
                        editor.apply();

                        Intent intent = new Intent(checkout.this, Navbar.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(checkout.this, getString(R.string.choosePickUpOption), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.logInSignUpFirst), Toast.LENGTH_SHORT).show();
        }


        /*if (menuSession) {
            mealNameSummary.setText(mealName);
            mealNameSubtotal.setText(mealName);
            addOn.setText(selectedItems);

            String itemCountX = dbItemCount + "x";
            itemCount.setText(itemCountX);

            String totalPricePesos = totalPrice + "₱";
            mealPriceTotal.setText(totalPricePesos);
            mealPriceSubtotal.setText(totalPricePesos);
            mealPriceSum.setText(totalPricePesos);

        } else {
            orderCon.setVisibility(View.GONE);
        }*/

        //ADD ITEM BUTTON
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checkout.this, menu.class);
                startActivity(intent);
            }
        });

        //SET ID
        paymentMethodBtn = findViewById(R.id.paymentCon);

        //PAYMENT METHOD
        paymentMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checkout.this, paymentMethod.class);
                startActivity(intent);
            }
        });

    }
}
package com.example.hp.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //To Shoe error message
            Toast.makeText(this, "You cannot have more than 100 Coffees", Toast.LENGTH_SHORT).show();
            //To exit Method
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1) {
            //To Shoe error message
            Toast.makeText(this, "You cannot have less than 1 Coffees", Toast.LENGTH_SHORT).show();
            //To exit Method
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);


    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // To get the name of user
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // To find user wants the Whipped Cream Topping
        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWippedCream = WhippedCreamCheckBox.isChecked();

        //To find user wants the Chocolate Topping
        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.Chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        int price = calculatePrice(hasWippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWippedCream, hasChocolate);
        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method is used for  create summary of order
     *
     * @param name            for user
     * @param price           of the order
     * @param addWhippedCream is whether or not user wants Whipped Cream Topping
     * @param addChocolate    is whether or not user wants Cocolate Topping
     * @return order summary
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd Whipped Cream: " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate: " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank You!";
        return priceMessage;

    }

    /**
     * Calculates the price of the order.
     *
     * @param addChocolate    is whether user wants a Chocolate Topping
     * @param addWhippedCream is whether a user wants a Whipped Cream Topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //price of 1 cup of coffee
        int basePrice = 5;
        //Add $1 if user wants Whipprd Cream Topping
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        // Add $2 if user wants Chocolate Topping
        if (addChocolate) {
            basePrice = basePrice + 2;

        }
        // To Calculate a Total price by multiplying by quantity
        int price = quantity * basePrice;
        return price;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     *
     private void displayMessage(String message) {
     TextView OrderSummaryTextView = (TextView) findViewById(R.id.Order_Summary_text_view);
     OrderSummaryTextView.setText(message);
     }

     */
}

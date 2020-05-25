package com.example.coffeeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity=2;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedcreamcheckbox=(CheckBox) findViewById(R.id.checkbox_whipped_cream);
        boolean haswhippedcream=whippedcreamcheckbox.isChecked();

        CheckBox chocolatecheckbox=(CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean haschocolate=chocolatecheckbox.isChecked();

        EditText nameView=(EditText) findViewById(R.id.name);
        String name= nameView.getText().toString();
        Log.i("MainActivity.java",name);
        if(name.length()==0){
            showToastMessage("Please Enter Name!");
            return;
        }
        int price=calculatePrice(haswhippedcream,haschocolate);


        String message=createOrderSummary(name,price,haswhippedcream,haschocolate);
//        displayMessage(message);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "CoffeeOrder for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void Increment(View view) {
        quantity=quantity+1;
        if(quantity>100){
            showToastMessage("You Cannot order more than 100 cups of Coffee!");
            quantity--;
            return;
        }
        display(quantity);

//        displayPrice(5*quantity);
    }
    public void Decrement(View view) {
        quantity=quantity-1;
        if(quantity<=0){
            showToastMessage("Order Atleast ! cup of Coffee!");
            quantity=quantity+1;
        }
        else {
            display(quantity);
//        displayPrice(5*quantity);
        }

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            orderSummaryTextView.setText(message);
//        }
//    }

    private int calculatePrice(boolean whippedcream,boolean chocolate){
        int base_price=5;
        if(whippedcream){
            base_price++;
        }
        if(chocolate){
            base_price=base_price+2;
        }
        return base_price*quantity;
    }

    private String createOrderSummary(String name,int number,boolean wc,boolean chocolate){
        String ans="Name: "+name+"\nAdd Whipped Cream? "+wc+"\nAdd Chocolate? "+chocolate+"\nQuantity: "+quantity+"\nTotal:"+" "+"$"+number+"\nThank You!";
        return ans;
    }
    private void showToastMessage(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}

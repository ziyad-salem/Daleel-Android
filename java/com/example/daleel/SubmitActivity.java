package com.example.daleel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SubmitActivity extends AppCompatActivity {

    Button back, submit;
    EditText etName, etStreet, etPostalCode, etCity, etPhone;
    Spinner sCategory, sCountry;
    MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        etName = findViewById(R.id.name);
        etStreet = findViewById(R.id.street);
        etPostalCode = findViewById(R.id.postalCode);
        etCity = findViewById(R.id.city);
        etPhone = findViewById(R.id.phone);

        sCategory = (Spinner) findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                        R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(adapter1);

        sCountry = (Spinner) findViewById(R.id.country);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCountry.setAdapter(adapter2);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("SUCCESS", false);
                setResult(1, intent);
                finish();
            }
        });

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, category, street, postal_code, city, country, phone;
                name = etName.getText().toString();
                category = sCategory.getSelectedItem().toString();
                street = etStreet.getText().toString();
                postal_code = etPostalCode.getText().toString();
                city = etCity.getText().toString();
                country = sCountry.getSelectedItem().toString();
                phone = etPhone.getText().toString();

                if (isEmpty(name) || isEmpty(street) || isEmpty(postal_code) || isEmpty(city) || isEmpty(country) || category == "Category" || country == "Country")
                {
                    Toast.makeText(SubmitActivity.this, "Please complete all fields", Toast.LENGTH_LONG).show();
                }
                else {
                    Place place = new Place();
                    place.setName(name);
                    place.setCategory(category);
                    place.setStreet(street);
                    place.setPostalCode(postal_code);
                    place.setCity(city);
                    place.setCountry(country);
                    place.setPhone(phone);

                    db.addPlace(place);
                    Intent intent = new Intent();
                    intent.putExtra("SUCCESS", true);
                    setResult(1, intent);
                    finish();
                }
            }
        });
    }

    private boolean isEmpty(String string) {
        return string.trim().length() == 0;
    }
}
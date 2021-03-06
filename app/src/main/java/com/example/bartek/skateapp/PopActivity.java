package com.example.bartek.skateapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

public class PopActivity extends Activity{

    private EditText tytul;
    private EditText opis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_activity);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        tytul = findViewById(R.id.tytul);
        opis = findViewById(R.id.opis);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width*8/10 , height*6/10);
    }

    /**
     * Metoda wywołująca się podczas kliknięcia klawisza "wstecz" na telefonie
     * Wysyła informację o tytule i opisie miejsca do aktywności, która ją wywołała
     */
        @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("tytul" , tytul.getText().toString());
        intent.putExtra("opis" , opis.getText().toString());
        setResult(RESULT_OK , intent);
        finish();

    }

    /**
     * po Kliknięciu przycisku "Confirm" efekt jest taki sam jak po wciśnięciu przycisku "Wstecz".
     * @param view Jest to widok przycisku, który został kliknięty
     */
    public void zapisz(View view){
        this.onBackPressed();
    }
}

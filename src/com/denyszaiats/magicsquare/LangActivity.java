package com.denyszaiats.magicsquare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import com.denyszaiats.magicsquare.R;

public class LangActivity extends Activity {

    private ImageView flagEn;
    private ImageView flagUa;
    private ImageView flagPl;
    private ImageView flagRu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_lang);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();

        flagEn = (ImageView) findViewById(R.id.imageLangEn);
        flagUa = (ImageView) findViewById(R.id.imageLangUa);
        flagRu = (ImageView) findViewById(R.id.imageLangRu);

        String prefLang = prefs.getString(Constants.PREFIX_LANG, "");
        if(!prefLang.equals("")) {
            Intent i = new Intent(LangActivity.this,
                    MagicSquare.class);
            startActivity(i);
        }

        flagEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(Constants.PREFIX_LANG, "_en");
                editor.commit();
                Intent i = new Intent(LangActivity.this,
                        MagicSquare.class);
                startActivity(i);
            }
        });

        flagUa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(Constants.PREFIX_LANG, "_ua");
                editor.commit();
                Intent i = new Intent(LangActivity.this,
                        MagicSquare.class);
                startActivity(i);
            }
        });

        flagRu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(Constants.PREFIX_LANG, "_ru");
                editor.commit();
                Intent i = new Intent(LangActivity.this,
                        MagicSquare.class);
                startActivity(i);
            }
        });
    }
}
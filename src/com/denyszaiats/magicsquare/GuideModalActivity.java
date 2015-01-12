package com.denyszaiats.magicsquare;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

public class GuideModalActivity extends Activity{

    private RelativeLayout mainLayout;
    private CheckBox dontShowAgain;
    private WebView textGuide;
    private SharedPreferences prefs;
    private Button closeGuide;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_guide);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayoutGuide);
        dontShowAgain = (CheckBox) findViewById(R.id.dontShowAgain);
        textGuide = (WebView) findViewById(R.id.textGuide);
        closeGuide = (Button) findViewById(R.id.buttonGuideOk);
        String guideText = "";

        closeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dontShowAgain.isChecked()) {
                    editor.putBoolean(Constants.DONT_SHOW_AGAIN, true);
                    editor.commit();
                }
               onBackPressed();
            }
        });
        String customHtml = "<html>" +
                                "<head>" +
                                    "<style>" +
                                        "body{" +
                                        "color: #000000;" +
                                        "text-align:justify;" +
                                        "font-family: Arial, Helvetica, sans-serif;" +
                                        "font-size:14dp;}" +
                                        "h3{" +
                                        "text-align:center;" +
                                        "font-size:16dp;}" +
                                    "</style>" +
                                "</head>" +
                                "<body>" + guideText + "</body>" +
                            "</html>";
        textGuide.loadData(customHtml, "text/html", "UTF-8");
    }
}

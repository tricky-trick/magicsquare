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
import android.widget.TextView;

public class GuideModalActivity extends Activity{

    private RelativeLayout mainLayout;
    private TextView guideTitle;
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
        guideTitle = (TextView) findViewById(R.id.textGuideTitle);
        String prefix = prefs.getString(Constants.PREFIX_LANG, "_en");
        guideTitle.setText(getResources().getString(getResources().getIdentifier("game_guide_title" + prefix, "string", getPackageName())));
        dontShowAgain.setText(getResources().getString(getResources().getIdentifier("dont_show_checkbox" + prefix, "string", getPackageName())));
        String guideTextStep1 = getResources().getString(getResources().getIdentifier("guide_modal_step1" + prefix, "string", getPackageName()));
        String guideTextStep2 = getResources().getString(getResources().getIdentifier("guide_modal_step2" + prefix, "string", getPackageName()));
        String guideTextStep3 = getResources().getString(getResources().getIdentifier("guide_modal_step3" + prefix, "string", getPackageName()));
        String guideTextStep4 = getResources().getString(getResources().getIdentifier("guide_modal_step4" + prefix, "string", getPackageName()));
        String guideTextStep5 = getResources().getString(getResources().getIdentifier("guide_modal_step5" + prefix, "string", getPackageName()));

        boolean showCheckbox = prefs.getBoolean(Constants.SHOW_CHECKBOX, true);
        if(!showCheckbox){
            mainLayout.removeView(dontShowAgain);
        }

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
                                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
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
                                "<body>" +  guideTextStep1 + "<br>" +
                                            guideTextStep2 + "<br>" +
                                            guideTextStep3 + "<br>" +
                                            guideTextStep4 + "<br>" +
                                            guideTextStep5 + "<br>" +
                                "</body>" +
                            "</html>";
        textGuide.loadData(customHtml, "text/html; charset=UTF-8", "UTF-8");
    }
}

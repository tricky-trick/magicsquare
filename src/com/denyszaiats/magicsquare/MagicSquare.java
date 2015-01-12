package com.denyszaiats.magicsquare;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.denyszaiats.magicsquare.drawer.DrawLine;
import com.denyszaiats.magicsquare.drawer.DrawRect;
import com.denyszaiats.magicsquare.drawer.DrawView;

import java.util.*;

public class MagicSquare extends Activity {

    private Helper helper;
    private Button tryAgainButton;
    private RelativeLayout areaView;
    private LinkedList<Integer> usedCoordinates;
    private LinkedList<DrawRect> listCreatedViews;
    private LinkedList<DrawLine> listCreatedLines;
    private int rectSize;
    private SharedPreferences prefs;
    private float rectX;
    private float rectY;
    private HashMap<Integer, Integer> colorMap;
    private ArrayList<Integer> listColor;
    private TreeMap<Integer, String> mapCoord;
    private SharedPreferences.Editor editor;
    private int maxX;
    private int maxY;
    private int countShapes = 2;
    private int size = 105;
    private int genIndexColorRect;
    private int width;
    private int magicColor;
    private int rectNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        helper = new Helper();
        areaView = (RelativeLayout) findViewById(R.id.mainLayout);
        colorMap = new HashMap<Integer, Integer>();
        size = helper.getShapeStartSize(this);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        areaView.setLayoutParams(params);
        areaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runGame();
            }
        });
    }

    private void runGame() {
        removeView();
        removeGrid();
        colorMap.put(1, R.color.clue_theme);
        colorMap.put(2, R.color.dark_blue);
        colorMap.put(3, R.color.hot_red);
        colorMap.put(4, R.color.lime);
        colorMap.put(5, R.color.yellow);
        colorMap.put(6, R.color.pink);
        colorMap.put(7, R.color.cyan);
        colorMap.put(8, R.color.gray);
        colorMap.put(9, R.color.orange);
        colorMap.put(10, R.color.indigo);
        colorMap.put(11, R.color.dark_green);
        colorMap.put(12, R.color.brown);
        //tryAgainButton.setVisibility(View.INVISIBLE);

        initShapes();
    }

    private void drawGrid(){
        listCreatedLines = new LinkedList<DrawLine>();
        for(Map.Entry<Integer, String> map: mapCoord.entrySet()){
            if(map.getValue().toString().startsWith("0,")){
                DrawLine lineView = new DrawLine(this);
                lineView.setBackgroundColor(getResources().getColor(R.color.silver));
                int point = Integer.parseInt(map.getValue().split(",")[1]);
                lineView.setStartPoint(new Point(0, point));
                lineView.setEndPoint(new Point(areaView.getWidth(), point));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        areaView.getWidth(), 3
                );
                lineView.setLayoutParams(params);
                lineView.setX(0);
                lineView.setY(point);
                listCreatedLines.add(lineView);
            }
            if(map.getValue().toString().endsWith(",0")){
                DrawLine lineView = new DrawLine(this);
                lineView.setBackgroundColor(getResources().getColor(R.color.silver));
                int point = Integer.parseInt(map.getValue().split(",")[0]);
                lineView.setStartPoint(new Point(point, 0));
                lineView.setEndPoint(new Point(point, areaView.getHeight()));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        3, areaView.getHeight()
                );
                lineView.setLayoutParams(params);
                lineView.setY(0);
                lineView.setX(point);
                listCreatedLines.add(lineView);
            }
        }

        DrawLine lineViewX = new DrawLine(this);
        lineViewX.setBackgroundColor(getResources().getColor(R.color.silver));
        lineViewX.setStartPoint(new Point(maxX, 0));
        lineViewX.setEndPoint(new Point(maxX, areaView.getHeight()));
        RelativeLayout.LayoutParams paramsX = new RelativeLayout.LayoutParams(
                3, areaView.getHeight()
        );
        lineViewX.setLayoutParams(paramsX);
        lineViewX.setY(0);
        lineViewX.setX(maxX - 2);
        listCreatedLines.add(lineViewX);

        DrawLine lineViewY = new DrawLine(this);
        lineViewY.setBackgroundColor(getResources().getColor(R.color.silver));
        lineViewY.setStartPoint(new Point(maxY, 0));
        lineViewY.setEndPoint(new Point(maxY, areaView.getWidth()));
        RelativeLayout.LayoutParams paramsY = new RelativeLayout.LayoutParams(
                areaView.getWidth(), 3
        );
        lineViewY.setLayoutParams(paramsY);
        lineViewY.setY(maxY - 2);
        lineViewY.setX(0);
        listCreatedLines.add(lineViewY);


        for(DrawView line: listCreatedLines){
            areaView.addView(line);
        }
    }

    private void removeGrid(){
        if (listCreatedLines != null) {
            for (DrawView line : listCreatedLines) {
                if(line != null)
                    areaView.removeView(line);
            }
        }
        areaView.setPadding(0,0,0,0);
    }

    private void drawShapes() {
        int randMagicColor = generateRandomInteger(1, colorMap.size(), new Random());
        magicColor = getResources().getColor(colorMap.get(randMagicColor));
        listCreatedViews = new LinkedList<DrawRect>();
        listColor = new ArrayList<Integer>();
        usedCoordinates = new LinkedList<Integer>();
        generateMapCoordinates();

        countShapes = mapCoord.size();
        for (int i = 0; i < countShapes; i++) {
            generateShapesParameters();
            DrawRect drawRect = new DrawRect(this);
            //drawRect.setLayoutParams(new ViewGroup.LayoutParams(rectSize, rectSize), );
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    rectSize - pxFromDp(1.5f), rectSize - pxFromDp(1.5f)
            );
            params.setMargins(pxFromDp(1.25f), pxFromDp(1.25f), pxFromDp(1.5f), pxFromDp(1.5f));
            drawRect.setLayoutParams(params);
            drawRect.setSideSize(rectSize);
            Random randColorRect = new Random();
            genIndexColorRect = generateRandomInteger(1, colorMap.size(), randColorRect);
            if(rectNumber%9 == 0) {
                drawRect.setBackgroundColor(magicColor);
            }
            else {
                drawRect.setBackgroundColor(getResources().getColor(colorMap.get(genIndexColorRect)));
            }
            drawRect.setX(rectX);
            drawRect.setY(rectY);
            drawRect.setText(String.valueOf(rectNumber));
            listCreatedViews.add(drawRect);
            listColor.add(genIndexColorRect);
        }
        removeGrid();
        //setPaddings();
        drawGrid();

        for (DrawRect drawRect : listCreatedViews) {
            do {
                areaView.addView(drawRect);
            }
            while (areaView.indexOfChild(drawRect) == -1);
        }

//        for (int i = 0; i < countShapes; i++) {
//            if(Integer.valueOf(listCreatedViews.get(i).getText())== i) {
//                areaView.addView(listCreatedViews.get(i));
//            }
//        }

        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        areaView.startAnimation(animation);

    }

    public void setPaddings(){
        int paddingX = (areaView.getWidth() - maxX)/2;
        int paddingY = (areaView.getHeight()- maxY)/2;

        areaView.setPadding(paddingX, paddingY, paddingX, paddingY);
    }

    private int pxFromDp(float dp) {
        return Math.round(dp * this.getResources().getDisplayMetrics().density);
    }

    private void initShapes() {
        removeView();
        removeGrid();
        drawShapes();
    }

    private void generateMapCoordinates() {
        int generatedSize = width/10;
        rectSize = generatedSize;
        maxX = 0;
        maxY = 0;
        int k = 1;
        mapCoord = new TreeMap<Integer, String>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if ((i % generatedSize == 0) && (j % generatedSize == 0)) {
                    if (((i) < width) && ((j) < width)) {
                        if((i) > maxX) {
                            maxX = (i);
                        }
                        if((j) > maxY) {
                            maxY = (j);
                        }
                        mapCoord.put(k, i + "," + j);
                        k++;
                    }
                }
            }
        }

    }

    private void generateShapesParameters() {
        Random randomNumberRect = new Random();
        int randomGeneratedNumberRect;
        do {
            randomGeneratedNumberRect = generateRandomInteger(1, mapCoord.size(), randomNumberRect);
        }
        while (usedCoordinates.contains(randomGeneratedNumberRect));

        rectX = Float.parseFloat(mapCoord.get(randomGeneratedNumberRect).split(",")[0]);
        rectY = Float.parseFloat(mapCoord.get(randomGeneratedNumberRect).split(",")[1]);
        rectNumber = randomGeneratedNumberRect;

        usedCoordinates.add(randomGeneratedNumberRect);
    }


    private int generateRandomInteger(int aStart, int aEnd, Random aRandom) {
        if (aStart > aEnd) {
            int tempEnd = aEnd;
            int tempStart = aStart;

            aEnd = tempStart;
            aStart = tempEnd;
        }
        long range = (long) aEnd - (long) aStart + 1;
        long fraction = (long) (range * aRandom.nextDouble());
        return (int) (fraction + aStart);
    }

    private void removeView() {
        if (listCreatedViews != null) {
            for (DrawView view : listCreatedViews) {
                if (view != null) {
                    areaView.removeView(view);
                }
            }
        }
    }
}

package mapp.com.sg.salud.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import mapp.com.sg.salud.R;

/**
 * Created by Jasmin on 10/5/2018.
 */

public class SplashScreen extends AppCompatActivity {
    ImageView left, right, logo;
    Animation flyLeft, flyRight, flyUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the layout -> splash.xml
        setContentView(R.layout.splash);

        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        logo = findViewById(R.id.logo);

        flyLeft = AnimationUtils.loadAnimation(this, R.anim.fromleft);
        flyRight = AnimationUtils.loadAnimation(this, R.anim.fromright);
        flyUp = AnimationUtils.loadAnimation(this, R.anim.frombot);

        Thread splashThread = new Thread() {

            public void run() {
                try {
                    left.setAnimation(flyLeft);
                    right.setAnimation(flyRight);
                    logo.setAnimation(flyUp);
                    // sleep time in milliseconds (3000 = 3sec)
                    sleep(3000);
                }  catch(InterruptedException e) {
                    // Trace the error
                    e.printStackTrace();
                } finally
                {
                    // Launch the MainActivity class
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        };
        // To Start the thread
        splashThread.start();
    }

}

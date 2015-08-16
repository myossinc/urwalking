package mamamairis.offlinenavigation;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String WATCH_BUTTON = "Schau dir lieber den fancy Button an!";
    private static final String USER_NODE = "Der Nutzer befindet sich auf dem Node: ";
    private static final String ROTATION =  "rotation";

    private Button button;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.node_name);
        button = (Button) findViewById(R.id.fanciest_button_ever);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startNavigation();
            }
        });
    }

    private void startNavigation() {
        System.out.println("startNavigation");
        ObjectAnimator anim = ObjectAnimator.ofFloat(button,ROTATION, 0f, 1080f);
        anim.setDuration(500);
        anim.start();
        blinkButton();
        showNode(WATCH_BUTTON);
    }

    private void blinkButton() {
        button.setBackgroundColor(Color.GREEN);
        final Animation animation = new AlphaAnimation(1f, 0.2f);
        animation.setDuration(10);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        button.startAnimation(animation);
        stopBlinkingAfterOneSecond();
    }

    private void stopBlinkingAfterOneSecond() {
        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.clearAnimation();
                Uri uri = Uri.parse("http://urwalking.ur.de/navi/index.php?startarea=PT&startnode=43");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }, 500);
    }

    private void showNode(String node) {
        System.out.println("showNode");
        text.setText(USER_NODE + node);
        ObjectAnimator anim = ObjectAnimator.ofFloat(button, ROTATION, 0f, 1080f);
        anim.setDuration(1000);
        anim.start();
        blinkButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.kylefebv.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_SHOWN = "com.example.kylefebv.geoquiz.answer_shown";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.kylefebv.geoquiz.answer_is_true";
    private boolean isAnswerTrue;
    private boolean mAnswerShown = false;
    private TextView mAnswerText, mVersion;
    private Button mCheatButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        isAnswerTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerText = (TextView) findViewById(R.id.answer_text);
        mCheatButton = (Button) findViewById(R.id.show_answer);
        mVersion = (TextView)findViewById(R.id.version);

        int i = Build.VERSION.SDK_INT;
        String s = i+"";

        mVersion.setText(s);

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnswerTrue) {
                    mAnswerText.setText(R.string.true_button);
                } else {
                    mAnswerText.setText(R.string.false_button);
                }
                setAnswerShownResult(true);

                if (Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.LOLLIPOP) {

                    int cx = mCheatButton.getWidth() / 2;
                    int cy = mCheatButton.getHeight() / 2;
                    float radius = mCheatButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mCheatButton, cx, cy,
                            radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mCheatButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }else{
                    mCheatButton.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("cheat", mAnswerShown);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        mAnswerShown = true;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}


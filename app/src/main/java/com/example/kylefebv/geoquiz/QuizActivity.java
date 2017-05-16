package com.example.kylefebv.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button true_btn, false_btn, cheat;
    private ImageButton next,prev;
    private TextView mQuestion;
    private static final String KEY_INDEX = "index";
    private static final String TAG = "QuizActivity";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;
    private int mCheatNum = 0;


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_asia,true)
    };

    private int mCurrentIndex;
    private int mCheatIndex;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX,mCurrentIndex);
        outState.putBoolean("cheat_index",mIsCheater);
        outState.putInt("cheat_question",mCheatIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        true_btn = (Button)findViewById(R.id.true_button);
        false_btn = (Button)findViewById(R.id.false_button);
        mQuestion = (TextView)findViewById(R.id.question);
        next = (ImageButton) findViewById(R.id.next_btn);
        prev = (ImageButton)findViewById(R.id.prev_btn);
        cheat = (Button)findViewById(R.id.cheat);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater = savedInstanceState.getBoolean("cheat_index");
            mCheatIndex = savedInstanceState.getInt("cheat_question");
        }

        updateQuestion();

        cheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheatNum > 3) {
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                    Intent i = new CheatActivity().newIntent(QuizActivity.this, answerIsTrue);
                    startActivityForResult(i, REQUEST_CODE_CHEAT);
                }
            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater = false;
                if(mCurrentIndex != 0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater = false;
                if(mCurrentIndex != mQuestionBank.length-1) {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                }

            }
        });


        true_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkAnswer(true);

            }
        });

        false_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkAnswer(false);
            }
        });
    }

    private void updateQuestion(){
        int i = mQuestionBank[mCurrentIndex].getTextresID();

        mQuestion.setText(i);
    }

    private void checkAnswer (boolean pressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int id = 0;
        if(mIsCheater){
            id = R.string.judgment_toast;
            mCheatIndex = mQuestionBank[mCurrentIndex].getTextresID();
        }else {
            if (pressedTrue == answerIsTrue) {
                id = R.string.correct_toast;
            } else {
                id = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this,id,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mCheatNum++;
        }
    }
}

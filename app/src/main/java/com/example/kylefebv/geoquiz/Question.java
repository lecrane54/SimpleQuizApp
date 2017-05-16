package com.example.kylefebv.geoquiz;

/**
 * Created by kyle on 5/16/2017.
 */

public class Question {
    private int mTextresID;
    private boolean mAnswerTrue;

    public Question (int textresID, boolean answerTrue){
        mTextresID = textresID;
        mAnswerTrue = answerTrue;
    }

    public int getTextresID() {
        return mTextresID;
    }

    public void setTextresID(int textresID) {
        mTextresID = textresID;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }


}

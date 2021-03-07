package com.aveke.quizy;

public class Question {
    int imageId;
    String questionText;
    String answer0;
    String answer1;
    String answer2;
    String answer3;
    int correctAnswer;
    int playerAnswer;

    public int getImageId() {
        return imageId;
    }

    public String getAnswer0() {
        return answer0;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public int getPlayerAnswer() {
        return playerAnswer;
    }

    public Question(int imageIdentifier,
                    String questionString,
                    String answerZero,
                    String answerOne,
                    String answerTwo,
                    String answerThree,
                    int correctAnswerIndex) {
        imageId = imageIdentifier;
        questionText = questionString;
        answer0 = answerZero;
        answer1 = answerOne;
        answer2 = answerTwo;
        answer3 = answerThree;
        correctAnswer = correctAnswerIndex;
        playerAnswer = -1;


    }

    public String getQuestionString() {
        return questionText;
    }

    public void setPlayerAnswer(int answer) {
        this.playerAnswer = answer;
    }

    boolean isCorrect() {
        return correctAnswer == playerAnswer;
    }

    public static void main(String[] args) {

    }
}


package com.aveke.quizy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Variables
    int currentQuestionIndex;
    int totalCorrect;
    int questionsRemaining;
    int totalQuestions;
    ArrayList<Question> questions = new ArrayList<Question>();
    ImageView questionImageView;
    TextView questionTextView;
    TextView questionsRemainingTextView;
    Button answer0Button;
    Button answer1Button;
    Button answer2Button;
    Button answer3Button;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show app icon in ActionBar
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_unquote_icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setElevation(0);

        setContentView(R.layout.activity_main);

        // Assign View member variables
        questionImageView = findViewById(R.id.iv_main_question_image);
        questionTextView = findViewById(R.id.tv_main_question_title);
        questionsRemainingTextView = findViewById(R.id.tv_main_questions_remaining_count);
        answer0Button = findViewById(R.id.btn_main_answer_0);
        answer1Button = findViewById(R.id.btn_main_answer_1);
        answer2Button = findViewById(R.id.btn_main_answer_2);
        answer3Button = findViewById(R.id.btn_main_answer_3);
        submitButton = findViewById(R.id.btn_main_submit_answer);

        answer0Button.setOnClickListener(v -> {
            onAnswerSelected(0);
        });

        answer1Button.setOnClickListener(v -> {
            onAnswerSelected(1);
        });

        answer2Button.setOnClickListener(v -> {
            onAnswerSelected(2);
        });

        answer3Button.setOnClickListener(v -> {
            onAnswerSelected(3);
        });

        submitButton.setOnClickListener(v -> {
            Question toCheck = getCurrentQuestion();
            if (toCheck.getPlayerAnswer() == -1)  {
                // Validation if user submitted their answer.
                AlertDialog.Builder validationEmptyAnswer = new AlertDialog.Builder(MainActivity.this);
                AlertDialog alert = validationEmptyAnswer.create();
                // Title of the pop-up.
                validationEmptyAnswer.setTitle("Wait a minute...");
                // Message of the pop-up.
                validationEmptyAnswer.setMessage("Please select an answer, no cheating!");
                // Button on the pop-up.
                validationEmptyAnswer.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.cancel();
                    }
                });
                // End of a pop-up, always needed.
                validationEmptyAnswer.show();
            } else {
                onAnswerSubmission();
            }
        });

        startNewGame();
    }

    public void displayQuestionsRemaining(int questionsRemaining) {
        questionsRemainingTextView.setText(String.format("%d", questionsRemaining));
    }

    public void displayQuestion(Question question) {
        questionTextView.setText(question.getQuestionString());
        answer0Button.setText(question.getAnswer0());
        answer1Button.setText(question.getAnswer1());
        answer2Button.setText(question.getAnswer2());
        answer3Button.setText(question.getAnswer3());
        questionImageView.setImageResource(question.getImageId());
    }

    public void onAnswerSelected(int answerSelected) {
        Question currentQuestion = getCurrentQuestion();
        currentQuestion.setPlayerAnswer(answerSelected);

        // Reset answers to remove checkboxes.
        answer0Button.setText(getCurrentQuestion().getAnswer0());
        answer1Button.setText(getCurrentQuestion().getAnswer1());
        answer2Button.setText(getCurrentQuestion().getAnswer2());
        answer3Button.setText(getCurrentQuestion().getAnswer3());

        // Add checkbox to selection.
        switch(answerSelected) {
            case 0:
                answer0Button.setText("✔ " + answer0Button.getText());
                break;
            case 1:
                answer1Button.setText("✔ " + answer1Button.getText());
                break;
            case 2:
                answer2Button.setText("✔ " + answer2Button.getText());
                break;
            case 3:
                answer3Button.setText("✔ " + answer3Button.getText());
                break;
            default:
                break;
        }
    }

     public void onAnswerSubmission() {
        Question toCheck = getCurrentQuestion();
        boolean isCorrect = toCheck.isCorrect();
        if (isCorrect) {
            totalCorrect++;
        }
        questions.remove(toCheck);
        displayQuestionsRemaining(questions.size());

        if (questions.size() == 0) {
            System.out.println(getGameOverMessage(totalCorrect, questionsRemaining));
            AlertDialog.Builder gameOverDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            gameOverDialogBuilder.setCancelable(false);
            // Title of the pop-up.
            gameOverDialogBuilder.setTitle("Game Over!");
            // Message of the pop-up.
            gameOverDialogBuilder.setMessage(getGameOverMessage(totalCorrect, questionsRemaining));
            // Button on the pop-up.
            gameOverDialogBuilder.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startNewGame();
                }
            });
            // End of a pop-up, always needed.
            gameOverDialogBuilder.show();

        } else {
            questionsRemaining = questions.size();
            chooseNewQuestion();
            displayQuestion(getCurrentQuestion());
        }
    }

    public void startNewGame() {
        // Add questions.
        Question q1 = new Question(R.drawable.img_quote_0, "Pretty good advice, and perhaps a scientist did say it... Who actually did?", "Albert Einstein", "Isaac Newton", "Rita Mae Brown", "Rosalind Franklin", 2);
        Question q2 = new Question(R.drawable.img_quote_1, "Was honest Abe honestly quoted? Who authored this pithy bit of wisdom?", "Edward Stieglitz","Maya Angelou",  "Abraham Lincoln", "Ralph Waldo Emerson", 0);
        Question q3 = new Question(R.drawable.img_quote_2, "Easy advice to read, difficult advice to follow - who actually said it?", "Martin Luther King Jr.", "Mother Teresa", "Fred Rogers", "Oprah Winfrey", 1);
        Question q4 = new Question(R.drawable.img_quote_3, "Insanely inspiring, insanely incorrect (maybe). Who is the true source of this inspiration?", "Nelson Mandela", "Harriet Tubman", "Mahatma Ghandi", "Nicolas Klein", 3);
        Question q5 = new Question(R.drawable.img_quote_4, "A peace worth striving for - who actually reminded us of this?", "Malala Yousafzai", "Martin Luther King Jr.", "Liu Xiaobo", "Dalai Lama", 1);
        Question q6 = new Question(R.drawable.img_quote_5, "Unfortunately, true - but did Marilyn Monroe say it or did someone else?", "Laurel Thatcher Ulrich", "Eleanor Roosevelt", "Marilyn Monroe", "Queen Victoria", 0);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);

        // Reset the totals.
        totalCorrect = 0;
        questionsRemaining = questions.size();
        totalQuestions = questions.size();

        Question firstQuestion = chooseNewQuestion();
        displayQuestion(firstQuestion);
        displayQuestionsRemaining(questionsRemaining);
    }

    Question chooseNewQuestion() {
        int randomNumber = generateRandomNumber(questions.size());
        currentQuestionIndex = randomNumber;
        return questions.get(currentQuestionIndex);
    }

    int generateRandomNumber(int max) {
        double randomNumber = Math.random();
        double result = max * randomNumber;
        return (int) result;
    }

    Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    String getGameOverMessage(int totalCorrect, int totalQuestions) {
        if (totalCorrect == 6) {
            return "You got all " + totalCorrect + " right! You won!";
        } else {
            return "You got " + totalCorrect + " right out of " + this.totalQuestions + ". Better luck next time!";
        }
    }
}
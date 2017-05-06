package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EditChallenge extends Activity implements View.OnClickListener{

    public Button buttonSaveChallenge;
    public Button buttonDeleteChallenge;
    public Button buttonEditQuestion;
    public Button buttonAddQuestion;
    public Button buttonDeleteQuestion;
    public EditText editTextChallengeName;
    public ListView questionList;

    public Challenge challenge;
    public int selectedQuestionPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_challenge);

        buttonSaveChallenge = (Button) findViewById(R.id.buttonEditChallengeSaveChallenge);
        buttonDeleteChallenge = (Button) findViewById(R.id.buttonEditChallengeDeleteChallenge);
        buttonEditQuestion = (Button) findViewById(R.id.buttonEditChallengeEditQuestion);
        buttonAddQuestion = (Button) findViewById(R.id.buttonEditChallengeAddQuestion);
        buttonDeleteQuestion = (Button) findViewById(R.id.buttonEditChallengeDeleteQuestion);
        editTextChallengeName = (EditText) findViewById(R.id.editTextEditChallengeChallengeName);
        questionList = (ListView) findViewById(R.id.listViewEditChallengeQuestions);

        buttonSaveChallenge.setOnClickListener(this);
        buttonDeleteChallenge.setOnClickListener(this);
        buttonEditQuestion.setOnClickListener(this);
        buttonAddQuestion.setOnClickListener(this);
        buttonDeleteQuestion.setOnClickListener(this);

        editTextChallengeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                challenge.setName(s.toString());
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            if(extras.containsKey("challenge")){
                challenge = (Challenge) extras.getSerializable("challenge");
                extras.remove("challenge");

                editTextChallengeName.setText(challenge.getName());

                List<String> questionNames = new ArrayList<>();
                for (Question question : challenge.getQuestionList()) {
                    questionNames.add(question.getName());
                }

                ArrayAdapter<String> challengeQuestionsAdapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        questionNames);

                questionList.setAdapter(challengeQuestionsAdapter);

                questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedQuestionPos = position;
                        Log.e("app", "Item position: "+ position);
                    }
                });

            }else{
                Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
                Intent start = new Intent(getApplicationContext(), Start.class);
                startActivity(start);
            }
        }else{
            Toast.makeText(this, getApplicationContext().getString(R.string.toast_error_missing_data), Toast.LENGTH_SHORT).show();
            Intent start = new Intent(getApplicationContext(), Start.class);
            startActivity(start);
        }
    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        switch(clickedButton.getId())
        {
            case R.id.buttonEditChallengeSaveChallenge:
                //TODO: still needs to be implemented...
                System.out.println("Save Challenge Button clicked");
                break;
            case R.id.buttonEditChallengeDeleteChallenge:
                //TODO: still needs to be implemented...
                System.out.println("Delete Challenge Button clicked");
                break;
            case R.id.buttonEditChallengeEditQuestion:
                if(selectedQuestionPos >= 0){
                    Intent editQuestion = new Intent(getApplicationContext(), EditQuestion.class);
                    editQuestion.putExtra("challenge", challenge);
                    editQuestion.putExtra("questionPosition", selectedQuestionPos);
                    editQuestion.putExtra("fromActivity", "editChallenge");
                    startActivity(editQuestion);
                }else{
                    Toast.makeText(this, R.string.toast_select_a_question, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonEditChallengeAddQuestion:
                Intent newQuestion = new Intent(getApplicationContext(), NewQuestion.class);
                newQuestion.putExtra("challenge", challenge);
                newQuestion.putExtra("fromActivity", "editChallenge");
                startActivity(newQuestion);
                break;
            case R.id.buttonEditChallengeDeleteQuestion:
                //TODO: still needs to be implemented...
                System.out.println("Delete Question Button clicked");
                break;
            default:
                throw new IllegalArgumentException("Action can not be handled.");
        }

    }
}
package at.vocabdevelopment.studymanager;

import java.io.Serializable;

public class Question implements Serializable {

    private String answer;
    private String question;
    private String name;

    public Question(String name, String question, String answer){
        this.name = name;
        this.question = question;
        this.answer = answer;
    }

    public String getName(){
        return name;
    }

    public String getQuestion(){
        return question;
    }

    public String getAnswer(){
        return answer;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }
}
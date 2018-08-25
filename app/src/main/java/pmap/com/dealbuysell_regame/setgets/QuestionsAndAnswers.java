package pmap.com.dealbuysell_regame.setgets;

/**
 * Created by Shashi on 04-Feb-18.
 */

public class QuestionsAndAnswers {
    public int que_id;
    public String que_questions;
    public String que_question_image;
    public String que_option1;
    public String que_option2;
    public String que_option3;
    public String que_option4;
    public int que_answer;

    public QuestionsAndAnswers(int que_id, String que_questions, String que_question_image, String que_option1, String que_option2, String que_option3, String que_option4, int que_answer) {
        this.que_id = que_id;
        this.que_questions = que_questions;
        this.que_question_image = que_question_image;
        this.que_option1 = que_option1;
        this.que_option2 = que_option2;
        this.que_option3 = que_option3;
        this.que_option4 = que_option4;
        this.que_answer = que_answer;
    }

    public String getQue_question_image() {
        return que_question_image;
    }

    public void setQue_question_image(String que_question_image) {
        this.que_question_image = que_question_image;
    }

    public int getQue_id() {
        return que_id;
    }

    public void setQue_id(int que_id) {
        this.que_id = que_id;
    }

    public String getQue_questions() {
        return que_questions;
    }

    public void setQue_questions(String que_questions) {
        this.que_questions = que_questions;
    }

    public String getQue_option1() {
        return que_option1;
    }

    public void setQue_option1(String que_option1) {
        this.que_option1 = que_option1;
    }

    public String getQue_option2() {
        return que_option2;
    }

    public void setQue_option2(String que_option2) {
        this.que_option2 = que_option2;
    }

    public String getQue_option3() {
        return que_option3;
    }

    public void setQue_option3(String que_option3) {
        this.que_option3 = que_option3;
    }

    public String getQue_option4() {
        return que_option4;
    }

    public void setQue_option4(String que_option4) {
        this.que_option4 = que_option4;
    }

    public int getQue_answer() {
        return que_answer;
    }

    public void setQue_answer(int que_answer) {
        this.que_answer = que_answer;
    }
}

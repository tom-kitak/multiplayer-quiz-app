package client.scenes;

public class Question {

    private String question;
    private int number;
    private String pathPhoto;
    private int correctAnswer;

    public Question(String question, int number, String pathPhoto, int correctAnswer) {
        this.question = question;
        this.number = number;
        this.pathPhoto = pathPhoto;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

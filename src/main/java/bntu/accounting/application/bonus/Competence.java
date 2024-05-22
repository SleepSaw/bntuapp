package bntu.accounting.application.bonus;

import bntu.accounting.application.models.fordb.Expert;

public class Competence
{
    private Double[][] degreesMatrix = new Double[3][4];

    public Competence() {
        initMatrix();
    }

    private void initMatrix(){
        degreesMatrix[0][0] = 1d;degreesMatrix[0][1] = 1.5d;degreesMatrix[0][2] = 2d;degreesMatrix[0][3] = 3d;
        degreesMatrix[1][0] = 2.5d;degreesMatrix[1][1] = 3d;degreesMatrix[1][2] = 5d;degreesMatrix[1][3] = 6d;
        degreesMatrix[2][0] = 4.5d;degreesMatrix[2][1] = 5.5d;degreesMatrix[2][2] = 7d;degreesMatrix[2][3] = 10d;

    }
    public double getCompetenceDegree(Expert expert){
        double degree = 0;
        int postIndex = getPostIndex(expert.getPost());
        int experienceIndex = getExperienceIndex(expert.getExperience());
        degree = degreesMatrix[postIndex][experienceIndex];
        return degree;
    }
    // Индекс должности
    private int getPostIndex(String post){
        int postIndex = 0;
        switch (post){
            case "Методист":
                postIndex = 0;
                break;
            case "Зам. директора":
                postIndex = 1;
                break;
            case "Директор":
                postIndex = 2;
                break;
        }
        return postIndex;
    }
    // Индекс опыта
    private int getExperienceIndex(String experience){
        int experienceIndex = 0;
        switch (experience){
            case "До 5 лет":
                experienceIndex = 0;
                break;
            case "От 5 до 10 лет":
                experienceIndex = 1;
                break;
            case "От 10 до 15 лет":
                experienceIndex = 2;
                break;
            case "Более 15 лет":
                experienceIndex = 3;
                break;
        }
        return experienceIndex;
    }
}

package bntu.accounting.application.models.fordb;

import jakarta.persistence.*;

@Entity
@Table(name = "Rating")
public class Rating {
    @EmbeddedId
    private ConstructKey key;
    @Column(name = "score")
    private Integer score;

    public Rating() {
    }
    public Rating(Employee employee,Expert expert, Integer score) {
        ConstructKey key = new ConstructKey(employee,expert);
        this.key = key;
        this.score = score;
    }
    public Rating(ConstructKey key, Integer score) {
        this.key = key;
        this.score = score;
    }

    public ConstructKey getKey() {
        return key;
    }

    public void setKey(ConstructKey key) {
        this.key = key;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}

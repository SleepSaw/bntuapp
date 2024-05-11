package bntu.accounting.application.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Rating")
public class Rating {
    @EmbeddedId
    private ConstructKey key;
    @Column(name = "score")
    private Integer score;

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

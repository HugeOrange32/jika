package cn.com.self.domain;

import javax.persistence.*;

public class Card {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String cardId;

    private Float probability;

    private String img;

    @Column(name = "card_type")
    private Integer cardType;

    @Column(name = "activity_id")
    private String activityId;

    /**
     * @return card_id
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * @return probability
     */
    public Float getProbability() {
        return probability;
    }

    /**
     * @param probability
     */
    public void setProbability(Float probability) {
        this.probability = probability;
    }

    /**
     * @return img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return card_type
     */
    public Integer getCardType() {
        return cardType;
    }

    /**
     * @param cardType
     */
    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    /**
     * @return activity_id
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * @param activityId
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
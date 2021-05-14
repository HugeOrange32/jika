package cn.com.self.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "draw_water")
public class DrawWater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String id;

    @Column(name = "act_id")
    private String actId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "draw_time")
    private Date drawTime;

    @Column(name = "card_id")
    private String cardId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return act_id
     */
    public String getActId() {
        return actId;
    }

    /**
     * @param actId
     */
    public void setActId(String actId) {
        this.actId = actId;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return draw_time
     */
    public Date getDrawTime() {
        return drawTime;
    }

    /**
     * @param drawTime
     */
    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }

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
}
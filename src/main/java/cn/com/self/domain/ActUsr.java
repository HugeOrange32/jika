package cn.com.self.domain;

import javax.persistence.*;

@Table(name = "act_usr")
public class ActUsr {
    @Id
    @Column(name = "act_id")
    private String actId;

    @Id
    @Column(name = "user_id")
    private String userId;

    private Integer cardnum1;

    private Integer cardnum2;

    private Integer cardnum3;

    private Integer cardnum4;

    private Integer cardnum5;

    @Column(name = "card_draw_nums")
    private Integer cardDrawNums;

    @Column(name = "first_share")
    private String firstShare;

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
     * @return cardnum1
     */
    public Integer getCardnum1() {
        return cardnum1;
    }

    /**
     * @param cardnum1
     */
    public void setCardnum1(Integer cardnum1) {
        this.cardnum1 = cardnum1;
    }

    /**
     * @return cardnum2
     */
    public Integer getCardnum2() {
        return cardnum2;
    }

    /**
     * @param cardnum2
     */
    public void setCardnum2(Integer cardnum2) {
        this.cardnum2 = cardnum2;
    }

    /**
     * @return cardnum3
     */
    public Integer getCardnum3() {
        return cardnum3;
    }

    /**
     * @param cardnum3
     */
    public void setCardnum3(Integer cardnum3) {
        this.cardnum3 = cardnum3;
    }

    /**
     * @return cardnum4
     */
    public Integer getCardnum4() {
        return cardnum4;
    }

    /**
     * @param cardnum4
     */
    public void setCardnum4(Integer cardnum4) {
        this.cardnum4 = cardnum4;
    }

    /**
     * @return cardnum5
     */
    public Integer getCardnum5() {
        return cardnum5;
    }

    /**
     * @param cardnum5
     */
    public void setCardnum5(Integer cardnum5) {
        this.cardnum5 = cardnum5;
    }

    /**
     * @return card_draw_nums
     */
    public Integer getCardDrawNums() {
        return cardDrawNums;
    }

    /**
     * @param cardDrawNums
     */
    public void setCardDrawNums(Integer cardDrawNums) {
        this.cardDrawNums = cardDrawNums;
    }

    /**
     * @return first_share
     */
    public String getFirstShare() {
        return firstShare;
    }

    /**
     * @param firstShare
     */
    public void setFirstShare(String firstShare) {
        this.firstShare = firstShare;
    }
}
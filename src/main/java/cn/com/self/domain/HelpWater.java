package cn.com.self.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "help_water")
public class HelpWater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String id;

    @Column(name = "act_id")
    private String actId;

    @Column(name = "help_user_id")
    private String helpUserId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "help_time")
    private Date helpTime;

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
     * @return help_user_id
     */
    public String getHelpUserId() {
        return helpUserId;
    }

    /**
     * @param helpUserId
     */
    public void setHelpUserId(String helpUserId) {
        this.helpUserId = helpUserId;
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
     * @return help_time
     */
    public Date getHelpTime() {
        return helpTime;
    }

    /**
     * @param helpTime
     */
    public void setHelpTime(Date helpTime) {
        this.helpTime = helpTime;
    }
}
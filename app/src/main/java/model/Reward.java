package model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class Reward implements Serializable{
    private static final long serialVersionUID = 1L;

    private  String reward_id;

    private String reward_name;

    private String reward_score;

    public String getReward_id() {
        return reward_id;
    }

    public void setReward_id(String reward_id) {
        this.reward_id = reward_id;
    }

    public String getReward_name() {
        return reward_name;
    }

    public void setReward_name(String reward_name) {
        this.reward_name = reward_name;
    }

    public String getReward_score() {
        return reward_score;
    }

    public void setReward_score(String reward_score) {
        this.reward_score = reward_score;
    }
}

package com.example.wishstore;

public class PostsHelper {
    String title;
    String desc;
    String budget;
    String bids;
    String userId;
    String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PostsHelper() {
    }

    public String getBids() {
        return bids;
    }

    public void setBids(String bids) {
        this.bids = bids;
    }

    public PostsHelper(String title, String desc, String budget, String bids, String userId, String postId) {
        this.title = title;
        this.desc = desc;
        this.budget = budget;
        this.bids = bids;
        this.userId = userId;
        this.postId = postId;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

package com.example.forum.filters.dtos;


public class PostFilterDto {
    private String title;
    private String keyword;
    private String createdBy;
    private String sortBy;
    private String sortOrder;

    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public PostFilterDto(){
    }

    public String getTitle() {
        return title;
    }


    public String getKeyword() {
        return keyword;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}

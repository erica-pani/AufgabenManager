package com.exercises.exeercises.model.Id;

import java.io.Serializable;
import java.util.Objects;

public class CategoryId implements Serializable{
    
    private Long boardId;
    private String categoryName;

    public CategoryId() {}

    public CategoryId(Long boardId, String categoryName) {
        this.boardId = boardId;
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryId)) return false;
        CategoryId that = (CategoryId) o;
        return Objects.equals(boardId, that.boardId)
                && Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, categoryName);
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    
}

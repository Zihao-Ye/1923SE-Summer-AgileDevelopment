package com.example.SESummer.Entity;

public class ScoreData {
    private Integer index;//下标
    private Integer count;//数量

    ScoreData(){

    }

    public ScoreData(Integer index, Integer count){
        this.index=index;
        this.count=count;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

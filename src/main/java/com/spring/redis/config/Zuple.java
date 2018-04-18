package com.spring.redis.config;

public class Zuple<T> {
    private Double score;
    private T element;

    public Zuple(T score, Object element) {
        this.score = (Double) score;
        this.element = (T) element;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }
}

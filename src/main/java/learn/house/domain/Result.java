package learn.house.domain;

public class Result<T> extends Response{
    private T item;
    public T getItem() { return item; }
    public void setItem(T item) { this.item = item; }
}

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private static int cruID = 1;
    private String name;
    private String author;
    private int price;
    private int amount;

    public Book(int id, String name, String author, int price, int amount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.amount = amount;

    }

    public Book(){
this.id = cruID++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

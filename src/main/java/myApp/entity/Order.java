package myApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import myApp.entity.Client;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "total_sum")
    private double sum;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @JsonBackReference
    private Client client;

    public Order() {   }

    public Order(int id, int userId, LocalDateTime creationDate, double sum) {
        this.id = id;
        this.clientId = userId;
        this.creationDate = creationDate;
        this.sum = sum;
    }

    // Getters and Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getClientId() {return clientId;}

    public void setClientId(int clientId) {this.clientId = clientId;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public double getSum() {return sum;}

    public void setSum(double sum) {this.sum = sum;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    @Override
    public String toString() {
        return "Order ID = " + id + ", client_id = " + clientId + ", creationDate = "
                + creationDate + ", total_sum = " + sum;
    }
}

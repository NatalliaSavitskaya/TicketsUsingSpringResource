package myApp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Order> orders;

    public Client() {    }

    public Client(int id, String name, LocalDateTime creationDate, String status) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.status = status;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public List<Order> getOrders() {return orders;}

    public void setOrders(List<Order> orders) {this.orders = orders;}

    @Override
    public String toString() {
        return "Client ID = " + id + ", name = " + name + ", creationDate = " + creationDate + ", status = " + status;
    }
}
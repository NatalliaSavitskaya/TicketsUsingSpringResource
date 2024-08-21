package myApp.service;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    public User() {
        this.id = id + 1;
    }

    public User(int id, String name, LocalDateTime creationDate, String status) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public List<Ticket> getTickets() {return tickets;}

    public void setTickets(List<Ticket> tickets) {this.tickets = tickets;}

    @Override
    public String toString() {
        return "User ID = " + id + ", name = " + name + ", creationDate = " + creationDate + ", status = " + status;
    }
}
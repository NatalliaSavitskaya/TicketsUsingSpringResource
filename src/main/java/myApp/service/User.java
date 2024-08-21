package myApp.service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    public User() {}

    // Getters and Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public List<Ticket> getTickets() {return tickets;}

    public void setTickets(List<Ticket> tickets) {this.tickets = tickets;}
}
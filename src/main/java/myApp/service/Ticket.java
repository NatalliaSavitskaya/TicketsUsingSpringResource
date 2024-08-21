package myApp.service;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "ticket_type")
    private String ticketType;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Ticket() {   }

    public Ticket(int id, int userId, LocalDateTime creationDate, String ticketType) {
        this.id = id;
        this.userId = userId;
        this.creationDate = creationDate;
        this.ticketType = ticketType;
    }

    // Getters and Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getUserId() {return userId;}

    public void setUserId(int userId) {this.userId = userId;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public String getTicketType() {return ticketType;}

    public void setTicketType(String ticketType) {this.ticketType = ticketType;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    @Override
    public String toString() {
        return "Ticket ID = " + id + ", user_id = " + userId + ", creationDate = "
                + creationDate + ", ticketType = " + ticketType;
    }
}
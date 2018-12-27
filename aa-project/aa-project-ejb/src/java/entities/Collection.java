/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Bram
 */
public class Collection {
    private Tickets ticket;
    private Plays play;
    private Seats seat;

    public void setTicket(Tickets ticket) {
        this.ticket = ticket;
    }

    public void setPlay(Plays play) {
        this.play = play;
    }

    public void setSeat(Seats seat) {
        this.seat = seat;
    }
    
    public Tickets getTicket() {
        return ticket;
    }

    public Plays getPlay() {
        return play;
    }

    public Seats getSeat() {
        return seat;
    }
    
    public Collection(Plays play, Seats seat, Tickets ticket) {
        this.ticket = ticket;
        this.play = play;
        this.seat = seat;
    }
}

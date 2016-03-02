package model;

import javax.persistence.*;

/**
 * Created by NTI-Sistema on 29/06/2015.
 */
@Entity
public class Contact {
    @Id
    @GeneratedValue
    private Integer Id;
    private String Valor;
    @ManyToOne
    private User user;
}

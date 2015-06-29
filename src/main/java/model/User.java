package model;

import javax.persistence.*;

/**
 * Created by NTI-Sistema on 29/06/2015.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer Id;
    private String Nome;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Nome;
    }

    public void setName(String name) {
        Nome = name;
    }
}

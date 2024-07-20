package ssu.cttkz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String userpic;
    private String email;
    private String gender;
    private String locale;
}

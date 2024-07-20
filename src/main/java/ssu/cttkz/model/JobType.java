package ssu.cttkz.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "job_types")
@Data
@NoArgsConstructor
public class JobType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "jobType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;
}

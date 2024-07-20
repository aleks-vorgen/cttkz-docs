package ssu.cttkz.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inv_number")
    private String invNumber;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "title")
    private String title;

    @Column(name = "fullname_mvo")
    private String fullNameMVO;

    @Column(name = "department")
    private String department; //TODO заменить на модель Department

    @Column(name = "application_number_original")
    private String applicationNumberOriginal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_type_id")
    private JobType jobType;

    @Column(name = "reg_number")
    private Timestamp regNumber;

    @Column(name = "executor")
    private String executor; //TODO заменить на модель User

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "create_user")
    private String createUser; //TODO заменить на модель User

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "update_reason")
    private String updateReason;

    @Column(name = "update_user")
    private String updateUser; //TODO заменить на модель User

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "delete_reason")
    private String deleteReason;

    @Column(name = "delete_user")
    private String deleteUser; //TODO заменить на модель User
}

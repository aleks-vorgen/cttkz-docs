package ssu.cttkz.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inv_number", nullable = false)
    private String invNumber;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "fullname_mvo", nullable = false)
    private String fullNameMVO;

    @Column(name = "department", nullable = false)
    private String department; //TODO заменить на модель Department

    @Column(name = "application_number_original", nullable = false)
    private String applicationNumberOriginal;

    @ManyToOne()
    @JoinColumn(name = "job_type_id", nullable = false)
    @JsonManagedReference
    private JobType jobType;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "executor", nullable = false)
    private String executor; //TODO заменить на модель User

    @Column(name = "comment")
    private String comment;

    @ManyToOne()
    @JoinColumn(name = "status_id")
    @JsonManagedReference
    private Status status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TaskHistory> history;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "create_user", nullable = false)
    private String createUser; //TODO заменить на модель User

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "update_reason")
    private String updateReason;

    @Column(name = "update_user")
    private String updateUser; //TODO заменить на модель User

    @PrePersist
    protected void onCreate() {
        this.regNumber = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        if (this.status == null) {
            this.status = new Status();
            this.status.setId(1L);
        }
        this.createdAt = LocalDateTime.now();
    }
}

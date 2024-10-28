package ssu.cttkz.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@RequiredArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; //TODO заменить на модель Department

    @Column(name = "application_number_original", nullable = false)
    private String applicationNumberOriginal;

    @ManyToOne()
    @JoinColumn(name = "job_type_id", nullable = false)
    @JsonManagedReference
    private JobType jobType;

    @Column(name = "reg_number")
    private String regNumber;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor; //TODO заменить на модель User

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

    @ManyToOne
    @JoinColumn(name = "created_user_id")
    private User createdUser; //TODO заменить на модель User

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "update_reason")
    private String updateReason;

    @ManyToOne
    @JoinColumn(name = "updated_user_id")
    private User updatedUser; //TODO заменить на модель User

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

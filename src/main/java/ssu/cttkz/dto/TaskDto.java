package ssu.cttkz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Integer id;
    private String invNumber;
    private String serialNumber;
    private String title;
    private String fullNameMVO;
    private String department; //TODO заменить на модель Department
    private String applicationNumberOriginal;
    private String jobType;
    private Timestamp regNumber;
    private String executor; //TODO заменить на модель User
    private String comment;
    private String status;
    private Timestamp createdAt;
    private String createUser; //TODO заменить на модель User
    private Timestamp updatedAt;
    private String updateReason;
    private String updateUser; //TODO заменить на модель User
}

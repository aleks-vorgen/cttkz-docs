package ssu.cttkz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String invNumber;
    private String serialNumber;
    private String title;
    private String fullNameMVO;
    private String department; //TODO заменить на модель Department
    private String applicationNumberOriginal;
    private Long jobType;
    private Long status;
    private String executor; //TODO заменить на модель User
    private String comment;
    private String createUser; //TODO заменить на модель User
    private String updateReason;
    private String updateUser; //TODO заменить на модель User

    private String regNumber;
    private String createdAt;
    private String updatedAt;
    private String deleteReason;
    private String deleteUser;
}

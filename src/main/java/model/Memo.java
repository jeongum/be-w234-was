package model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Memo {

    @Id
    @GeneratedValue
    private Long id;

    private String author;

    private String contents;

    @CreationTimestamp
    private LocalDateTime createDate;
}

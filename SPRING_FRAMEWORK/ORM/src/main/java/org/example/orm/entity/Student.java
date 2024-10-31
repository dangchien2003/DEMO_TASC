package org.example.orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    private int id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;
}

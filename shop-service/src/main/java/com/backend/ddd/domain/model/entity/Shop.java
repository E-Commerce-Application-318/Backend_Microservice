package com.backend.ddd.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "shops")
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    private String name;
    private String description;
    private String address;

    @Column(name = "created_at")
    private Date createAt;

    @Column(name = "updated_at")
    private Date updateAt;
}

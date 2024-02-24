package org._3HCompany.microservices.uaa.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Filter;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branch")
@Filter(name="is_deleted",condition="is_deleted = false")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100,nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer code;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer degree;

    @Column(length = 500)
    private String description;

    @ColumnDefault("false")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Branch parent;

    @ToString.Exclude
    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<Branch> children;
}
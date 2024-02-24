package org._3HCompany.microservices.uaa.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Filter;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persons")
@Filter(
        name="is_deleted",
        condition="is_deleted = false"
)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 30, nullable = false)
    private String lastName;

    @Column(length = 30)
    private String email;

    @Column(length = 11, nullable = false,unique = true)
    private String mobileNumber;

    @Column(length = 10)
    private String postalCode;

    @ColumnDefault("false")
    private Boolean mobileConfirmed;

    @ColumnDefault("false")
    private Boolean emailConfirmed;

    @ColumnDefault("false")
    private Boolean isDeleted;

    @ToString.Exclude
    @OneToOne(mappedBy = "person")
    private User user;

    /*@ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Invoice> buyInvoices;

    @ToString.Exclude
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Invoice> saleInvoices;*/

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Person person = (Person) o;
        return getId() != null && Objects.equals(getId(), person.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}

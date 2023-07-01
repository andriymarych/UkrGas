package com.gas.app.entity.user;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter @Setter
@ToString
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_User_AuthId"))
    @JsonManagedReference
    private Auth auth;

}

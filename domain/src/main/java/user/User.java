package user;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shop.Shop;
import warranty.WarrantyServices;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "users")
public class User  extends BaseEntity {

    private String name;
    private String surname;
    private String userName;
    private String email;
    private String password;

    @ElementCollection(targetClass = UserRole.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_roles")
    @Column(name="role")
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "userManager")
    private List<Shop> shopsForManager;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manager_id")
    private User manager;

    @OneToMany(mappedBy = "manager")
    private List<User> subordinates;



}

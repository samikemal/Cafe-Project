package com.burak.cafe.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name="user")
public class UserEntity implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name="name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    @Transient
    private String password;


    @Column(name = "active")
    private int active;



    //mesela biz user ı oluştuyoruz role u olusturuyoruz kendiside user_role u oluşturuyır ve eşleştiriyor
    //join table la kendimiz ekliyoruz
    @NotEmpty
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)//Çoktan bire bir ilişkiyi eşlemek için, //@JoinColumn ana tablo ile sütun bağlama / birleştirme belirtmek için kullanılır.
    @JoinTable(name = "user_role", joinColumns ={ @JoinColumn(name = "user_id") }, inverseJoinColumns = {@JoinColumn(name = "role_id") } )
    private Set<RoleEntity> roles;


    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_orders",joinColumns ={@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private Set<OrderEntity> order;

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, @Email(message = "*Please provide a valid Email") @NotEmpty(message = "*Please provide an email") String email, @Length(min = 5, message = "*Your password must have at least 5 characters") @NotEmpty(message = "*Please provide your password") String password, int active, @NotEmpty Set<RoleEntity> roles, @NotEmpty Set<OrderEntity> order) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.order = order;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }


    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<OrderEntity> getOrder() {
        return order;
    }

    //buraya bak hata çıkarsa
    public void setOrderEntity(Set<OrderEntity> order) {
        this.order = order;
    }

    public void setOrder(Set<OrderEntity> order) {
        this.order = order;
    }


}

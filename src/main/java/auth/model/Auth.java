package auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity(name = "AUTH")
public class Auth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "AUTH_CD")
    private Long authCd;

    @Column(name = "AUTH_TYPE")
    private String authType;

    @JoinColumn(name = "userCd")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User userEntity;
    public void setUser(User userEntity) {
        this.userEntity = userEntity;
    }
}
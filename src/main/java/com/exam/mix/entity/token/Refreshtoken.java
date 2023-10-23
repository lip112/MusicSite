package com.exam.mix.entity.token;


import com.exam.mix.entity.user.Role;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Refreshtoken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    private String refreshToken;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}

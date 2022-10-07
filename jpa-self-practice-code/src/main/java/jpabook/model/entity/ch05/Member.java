package jpabook.model.entity.ch05;

import javax.persistence.*;

/**
 * 22/10/08
 * HiiWee
 * ex05_04: 매핑한 회원 엔티티
 */
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    // 연관관계 매핑 Member(N) : Team(1)이므로 Member에 @ManyToOne
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public Member() {

    }

    // 연관관계 설정
    public void setTeam(Team team) {
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

}

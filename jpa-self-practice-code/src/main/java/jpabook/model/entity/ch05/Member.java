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
        // 연관관계 편의 메서드
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        // 무한루프 방지를 위해 !team.getMember().contains(this) 추가
        if (team != null && !team.getMembers().contains(this)) {
            team.addMember(this);
        }
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

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }
}

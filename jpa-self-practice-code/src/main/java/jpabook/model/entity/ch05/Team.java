package jpabook.model.entity.ch05;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 22/10/08
 * HiiWee
 * ex05_05: 매핑한 팀 엔티티
 */
@Entity
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    public Team() {

    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // 양방향 연관관계를 위해 일대다 추가
    // mappedBy: 양방향 매핑일때 반대쪽 매핑의 필드 이름을 값으로 준다
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        this.members.add(member);

        // 무한루프 방지를 위한 if문
        if (member.getTeam() != this) {
            member.setTeam(this);
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}

package jpabook.model;


import jpabook.model.entity.ch05.Member;
import jpabook.model.entity.ch05.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("jpabook");
        em = emf.createEntityManager();

        EntityTransaction tr = em.getTransaction();

        try {
            tr.begin();

            // 연관관계 저장
            testSave();

            // 연관관계 모두 조회
            queryLogicJoin();

            // 연관관계 수정 및 조회
            updateRelation();

            // 연관관계 제거 및 조회
            deleteRelation();

            // 양방향 연관관계 설정
            biDirection();

            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    public static void print() {
        String jpql = "select m from Member m";
        List<Member> list = em.createQuery(jpql, Member.class).getResultList();

        list.forEach(o -> {
            System.out.println(o.toString());
        });
    }

    // 연관관계 저장
    public static void testSave() {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");

        // 양방향 연관관계 설정
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);
    }

    // 조회
    private static void queryLogicJoin() {
        // 회원이 팀과 관계를 가지고 있는 필드 m.team을 통해 Member, Team을 조인함
        // where절에선 조인한 t.name을 검색조건으로 사용해 팀1에 속한 회원만 검색함
        // :teamName: :로 시작하는 것은 파라미터를 바인딩 받는 문법
        String jpql = "select m from Member m join m.team t where t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        System.out.println(resultList);
    }

    // 연관관계 수정
    private static void updateRelation() {
        // 새로운 팀 생성
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        // 회원1에 새로운 팀2 설정
        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    // 연관관계 제거
    private static void deleteRelation() {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
    }

    // 일대다 컬렉션 조회
    public static void biDirection() {
        // 강제로 flush 하지 않으면 아무것도 조회되지 않음
        // em.flush();
        // em.clear();
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();
        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }
    }
}


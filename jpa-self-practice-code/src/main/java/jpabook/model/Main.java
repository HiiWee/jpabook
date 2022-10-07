package jpabook.model;


import jpabook.model.entity.ch05.Member;
import jpabook.model.entity.ch05.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("jpabook");
        em = emf.createEntityManager();

        EntityTransaction tr = em.getTransaction();

        try {
            tr.begin();
            testSave();
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    public static void testSave() {
        // 팀1 저장
        Team team1 = new Team("Team", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);
    }


}
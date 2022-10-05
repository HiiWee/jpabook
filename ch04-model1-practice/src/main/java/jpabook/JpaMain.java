package jpabook;

import jpabook.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tr = em.getTransaction();

        try {
            tr.begin();

            logic(em);

            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
        Member member = new Member();
        member.setName("호석");
        member.setCity("천안");
        member.setStreet("103");
        member.setZipcode("11234");

        em.persist(member);
        System.out.println(member.getId());
        System.out.println(member.getName() + " " + member.getCity() + " " + member.getStreet() + " " + member.getZipcode());

        Member findMember = em.find(Member.class, member.getId());
        System.out.println(member == findMember);
        em.remove(member);
    }
}
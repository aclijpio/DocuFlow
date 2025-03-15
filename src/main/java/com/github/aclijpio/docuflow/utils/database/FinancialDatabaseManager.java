package com.github.aclijpio.docuflow.utils.database;

import com.github.aclijpio.docuflow.entities.Document;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class  FinancialDatabaseManager extends AbstractDatabaseManager {
    public FinancialDatabaseManager(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
    public List<? extends Document> findAllDocuments(){
        try(EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            TypedQuery<? extends Document> query = entityManager.createQuery(
                    "SELECT d FROM Document d WHERE TYPE(d) IN (Invoice, Payment, PaymentRequest)",
                    Document.class);
            return query.getResultList();
        }
    }
    public boolean checkUniqueNumber(String number){
        try(EntityManager entityManager = this.entityManagerFactory.createEntityManager()){
            TypedQuery<? extends Document> query = entityManager.createQuery(
                    "SELECT d FROM Document d WHERE TYPE(d) IN (Invoice, Payment, PaymentRequest) AND d.number = :currentNumber",
                    Document.class);
            query.setParameter("currentNumber", number);
            return query.getSingleResult() == null;

        } catch (NoResultException e){
          return true;
        }
    }
    public void deleteDocumentById(Long id ){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Document document = entityManager.find(Document.class, id);
        if (document != null) {
            entityManager.remove(document);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

/*    public Optional<Currency> findCurrencyByCurrencyCode(CurrencyCode code){
        try(EntityManager entityManager = this.entityManagerFactory.createEntityManager()) {
            Query query = entityManager.createQuery(
                    "SELECT c FROM Currency c WHERE c.currencyCode = :currencyCodeValue"
            );
            query.setParameter("currencyCodeValue", code);
            Currency currency = (Currency) query.getSingleResult();
            query.getSingleResult();
            return Optional.of(currency);
        }catch (NoResultException e){
            return Optional.empty();
        }

    }*/
}

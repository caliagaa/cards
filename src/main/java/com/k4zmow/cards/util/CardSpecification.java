package com.k4zmow.cards.util;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.k4zmow.cards.model.Card;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CardSpecification implements Specification<Card> {

    private List<SearchCriteria> criteriaList;

    public CardSpecification(List<SearchCriteria> criteria) {
        this.criteriaList = criteria;
    }


    @Override
    public Predicate toPredicate(Root<Card> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate result =  builder.conjunction();
        for (SearchCriteria criteria: this.criteriaList) {
            if (criteria != null) {
                if (criteria.getKey().equalsIgnoreCase("creationDate")){
                    Predicate equal = builder.equal(root.get(criteria.getKey()), criteria.getValue());
                    result = builder.and(result, equal);
                    continue;
                }

                if (criteria.getOperation().equalsIgnoreCase("EQ")) {
                    Predicate equal = builder.equal(root.get(criteria.getKey()), criteria.getValue().toString());
                    result = builder.and(result, equal);
                }
            }
        }
        return result;
    }
}

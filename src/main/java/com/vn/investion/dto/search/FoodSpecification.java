//package dev.sabri.securityjwt.dto.search;
//
//import com.vn.investion.dto.search.SearchCriteria;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.time.OffsetDateTime;
//import java.util.List;
//
//public class FoodSpecification<T> implements Specification<T> {
//
//    private final List<SearchCriteria> criteriaList;
//
//    public FoodSpecification(List<SearchCriteria> criteriaList) {
//        this.criteriaList = criteriaList;
//    }
//
//    @Override
//    public Predicate toPredicate
//            (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//        if (criteriaList.isEmpty()) {
//            return builder.conjunction();
//        }
//        var predicate = builder.conjunction();
//        for (SearchCriteria criteria : criteriaList) {
//
//            if (criteria.getOperation().equalsIgnoreCase(">")) {
//                Predicate predicateTmp = null;
//                if(root.get(criteria.getKey()).getJavaType() == OffsetDateTime.class){
//                    predicateTmp = builder.greaterThanOrEqualTo(
//                            root.get(criteria.getKey()), criteria.getValue().toString());
//                }else{
//
//                }
//                predicate = builder.and(predicate, );
//            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
//                predicate = builder.and(predicate, builder.lessThanOrEqualTo(
//                        root.get(criteria.getKey()), criteria.getValue().toString()));
//            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
//
//                Predicate predicateTmp = null;
//                if (root.get(criteria.getKey()).getJavaType() == String.class) {
//                    predicateTmp = builder.like(
//                            root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
//                } else {
//                    predicateTmp = builder.equal(root.get(criteria.getKey()), criteria.getValue());
//                }
//                predicate = builder.and(predicate, predicateTmp);
//            }
//        }
//
//        return predicate;
//    }
//}

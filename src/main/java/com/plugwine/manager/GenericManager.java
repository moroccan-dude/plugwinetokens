package com.plugwine.manager;

import java.io.Serializable;
import java.util.List;

/**
 * Interface définissant un gestionnaire générique pour une entité.
 * 
 * @author fchopard
 * 
 * @param <T> le type d'entité traité par ce service
 * @param <ID> le type de la clé primaire des objets de type <T>
 */
public interface GenericManager<T, ID extends Serializable> extends AbstractManager {

    /**
     * Récupère une entité selon son identifiant.
     * 
     * @param id l'identifiant de l'objet
     * @return l'entité ou <code>null</code> si non trouvée
     */
    T get(ID id);

    /**
     * Récupère une entité selon son identifiant avec ses dépendances sans tenir compte du lazy
     * loading de celles-ci.
     * 
     * @param id l'identifiant de l'objet
     * @return l'entité ou <code>null</code> si non trouvée
     */
    T getWithDependencies(ID id);

    /**
     * Recherche toutes les entités connues du type géré par ce gestionnaire.
     * 
     * @return la liste des entités
     */
    List<T> getAll();

    /**
     * Recherche toutes les entités connues du type géré par ce gestionnaire avec leurs dépendances
     * sans tenir compte du lazy loading de celles-ci.
     * 
     * @return la liste des entités
     */
    List<T> getAllWithDependencies();

    /**
     * Recherche toutes les entités connues du type géré par ce gestionnaire dont l'id est dans la
     * liste ids donnée en paramêtre. Si un id des id passé en paramêtre n'existe pas, il n'en sera
     * pas tenu compte
     * 
     * @return la liste des entités dont l'id est dans la liste ids
     */
    List<T> getAllByIds(List<ID> ids);

    /**
     * Sauve une entité persistente. Si elle ne l'était pas encore, elle est créée, sinon elle est
     * mise à jour.
     * 
     * @param entity l'entité
     * @return l'entité sauvée
     */
    T persist(T entity);

    /**
     * Supprime une entité.
     * 
     * @param entity l'entité
     */
    void delete(T entity);

    /**
     * Contrôle si une entité existe déjà en fonction de son identifiant.
     * 
     * @param id l'identifiant
     * @return <code>true</code> si l'entité existe, sinon <code>false</code>
     */
    boolean exists(ID id);

    /**
     * Une serie de methodes generiques
     */
    /**
     * 
     * @param property
     * @param value
     * @return
     */
    T getByUniqueProperty(String property, Object value);

    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @return
     */
    T getUnique(String queryNameOrString, String[] properties, Object[] values);

    /**
     * 
     * @param queryNameOrString
     * @param values
     * @return
     */
    T getUnique(String queryNameOrString, Object[] values);

    /**
     * 
     * @param queryNameOrString
     * @param properties
     * @param values
     * @param pageNumber Numéro de la page (-1 pour obtenir la liste complète des entités)
     * @return
     */
    List<?> listForQueryName(String queryNameOrString, String[] properties, Object[] values,
        int pageNumber);

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    List<T> listByProperty(String property, Object value);

    /**
     * 
     * @param property
     * @param value
     * @return
     */
    List<T> listByProperty(String[] property, Object[] value);

}

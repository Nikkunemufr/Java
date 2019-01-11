package representations;

import java.util.Set;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Variable {

    public String nom;
    public Set<String> domaine;

    /**
     *
     * Creer une variable avec un nom et un domaine.
     *
     * @param nom
     * @param domaine
     */
    public Variable(String nom, Set<String> domaine) {
        this.nom = nom;
        this.domaine = domaine;
    }

    /**
     *
     * Permet d'obtenir le nom d'une Variable.
     *
     * @return le nom d'une variable
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * Permet d'obtenir les valeurs d'un domaine.
     * 
     * @return les valeurs d'un domaine
     */
    public Set<String> getDomaine() {
        return domaine;
    }
}

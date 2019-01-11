package datamining;

import representations.Variable;

import java.util.List;
import java.util.Map;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class BooleanDatabase {

    private List<Variable> listVariable;
    private List<Map<Variable, String>> listTransactions;

    /**
     * Constructeur instanciant une boolean database
     *
     * @param listVariable     liste des variables appartenant a la database
     * @param listTransactions liste des transactions appartenant a la database
     */
    public BooleanDatabase(List<Variable> listVariable, List<Map<Variable, String>> listTransactions) {
        this.listVariable = listVariable;
        this.listTransactions = listTransactions;

    }

    /**
     * Methode permettant d'acceder à la liste des variables passé au constructeur
     *
     * @return liste des variables
     */
    public List<Variable> getListVariable() {
        return listVariable;
    }

    /**
     * Methode permettant d'acceder à la liste des transactions passé au constructeur
     *
     * @return liste des transactions
     */
    public List<Map<Variable, String>> getListTransactions() {
        return listTransactions;
    }

}

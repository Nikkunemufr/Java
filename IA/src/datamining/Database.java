package datamining;

import representations.Variable;

import java.util.*;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Database {
    private List<Variable> listVariable;
    private List<Map<Variable, String>> listTransactions;

    /**
     * Constructeur instanciant une database
     *
     * @param listVariable     liste des variables appartenant a la database
     * @param listTransactions liste des transactions appartenant a la database
     */
    public Database(List<Variable> listVariable, List<Map<Variable, String>> listTransactions) {
        this.listVariable = listVariable;
        this.listTransactions = listTransactions;
    }

    /**
     * Transforme une database non booléenne en une database booléenne
     *
     * @return database transformé en database booléenne
     */
    public BooleanDatabase toBooleanDatabase() {
        List<Variable> listVariableBoolean = new ArrayList<>();
        List<Map<Variable, String>> listTransactionsBoolean = new ArrayList<>();

        HashSet<String> bool = new HashSet<>();
        bool.add("0");
        bool.add("1");
        // Transformation des variables non boolean en variable boolean
        for (Variable var : listVariable) {
            for (String value : var.getDomaine()) {
                listVariableBoolean.add(new Variable(var.getNom() + "_" + value, bool));
            }
        }

        // Transformation des transactions non boolean en transactions boolean
        for (Map<Variable, String> transaction : listTransactions) {
            Map<Variable, String> mapTransactionBoolean = new HashMap<>();

            // Initialisation de toute les variables boolean
            for (Variable var : listVariableBoolean) {
                mapTransactionBoolean.put(var, "0");
            }

            // Pour chaque variable étant dans une transaction on passe sa valeur à vrai
            for (Map.Entry mapEntry : transaction.entrySet()) {
                Variable key = (Variable) mapEntry.getKey();
                for (Variable var : listVariableBoolean) {
                    if (var.getNom().equals(key.getNom() + "_" + transaction.get(key))) {
                        mapTransactionBoolean.put(var, "1");
                    }
                }
            }
            // Ajout de la transaction boolean a la liste de toute les transactions boolean
            listTransactionsBoolean.add(mapTransactionBoolean);
        }
        return new BooleanDatabase(listVariableBoolean, listTransactionsBoolean);
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
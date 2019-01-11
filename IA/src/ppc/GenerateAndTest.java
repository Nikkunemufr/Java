package ppc;

import representations.Variable;

import java.util.*;
import representations.Constraint;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class GenerateAndTest {

    public Map<Variable,String> generate(Variable[] tabVariable, Constraint[] tabRule){

        Map<Variable,String> solution = new HashMap<>();
        Iterator<String>[] tabIt = new Iterator[tabVariable.length];
        Random rand = new Random();
        String valueDomaine = "";
        Boolean verif = true;

        do{
            for (int i = 0;i<=tabVariable.length-1;i++) {
                tabIt[i] = tabVariable[i].domaine.iterator();
                int nombreAleatoire = rand.nextInt(tabVariable[i].domaine.size() - 1 + 1) + 1;
                for (int y = 0; y < nombreAleatoire; y++){
                    if (tabIt[i].hasNext()){
                        valueDomaine = tabIt[i].next();
                    }
                }
                solution.put(tabVariable[i],valueDomaine);
            }

            verif = true;
            for (Constraint r: tabRule) {
                if(!(verif && r.isSatisfiedBy(solution)))
                    verif = false;
            }
        } while(!verif);

        return solution;
    }
}

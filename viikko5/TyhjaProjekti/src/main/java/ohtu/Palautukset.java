
package ohtu;

import java.util.ArrayList;
import java.util.List;

public class Palautukset {
    List<Palautus> palautukset = new ArrayList<Palautus>();

    public void setPalautukset(List<Palautus> palautukset) {
        this.palautukset = palautukset;
    }

    public List<Palautus> getPalautukset() {
        return palautukset;
    }

    @Override
    public String toString() {
        if (palautukset.size() == 0){
            return "";
        }
        int tunnit = 0;
        int tehtavat = 0;
        StringBuilder p = new StringBuilder();
        p.append("\nopiskelijanumero ");
        p.append(palautukset.get(0).getOpiskelijanumero());
        p.append("\n\n");
        for (Palautus palautus : palautukset) {
            p.append(palautus.siistittyToString());
            p.append("\n");
            tunnit += palautus.getTunteja();
            tehtavat += palautus.getTehtavia();
        }
        p.append("\nyhteensä: ");
        p.append(tehtavat);
        p.append(" tehtävää ");
        p.append(tunnit);
        p.append(" tuntia");

        return p.toString();
    }
    
    
}

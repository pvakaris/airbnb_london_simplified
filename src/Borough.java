/**
 * An enum containing all London boroughs.
 *
 * @author Talha Abduluddus
 * @version 2021.03.16
 */
public enum Borough {
    ENFI("Enfield"),
    BARN("Barnet"),
    HRGY("Haringey"),
    WALT("Waltham Forest"),
    HRRW("Harrow"),
    BREN("Brent"),
    CAMD("Camden"),
    ISLI("Islington"),
    HACK("Hackney"),
    REDB("Redbridge"),
    HAVE("Havering"),
    HILL("Hillingdon"),
    EALI("Ealing"),
    KENS("Kensington and Chelsea"),
    WSTM("Westminster"),
    TOWH("Tower Hamlets"),
    NEWH("Newham"),
    BARK("Barking and Dagenham"),
    HOUN("Hounslow"),
    HAMM("Hammersmith and Fulham"),
    WAND("Wandsworth"),
    CITY("City of London"),
    GWCH("Greenwich"),
    BEXL("Bexley"),
    RICH("Richmond upon Thames"),
    MERT("Merton"),
    LAMB("Lambeth"),
    STHW("Southwark"),
    LEWS("Lewisham"),
    KING("Kingston upon Thames"),
    SUTT("Sutton"),
    CROY("Croydon"),
    BROM("Bromley");

    private String fullName;

    Borough(String fullName) {
        this.fullName = fullName;
    }

    public String toString() {
        return fullName;
    }
}

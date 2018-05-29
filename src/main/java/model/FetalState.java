package model;

public enum FetalState {
    Normal(0), Suspect(0.5), Patologic(1);

    public final double Value;

    private FetalState(double value) {
        Value = value;
    }

    public static FetalState valueOf(double i) {
        if (i == 0) return Normal;
        else if (i == 0.5) return Suspect;
        else return Patologic;
    }
}
